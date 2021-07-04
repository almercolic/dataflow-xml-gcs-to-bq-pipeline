package de.acolic.demos.mapping;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import de.acolic.demos.model.Company;
import lombok.extern.slf4j.Slf4j;
import org.apache.beam.sdk.io.FileIO;
import org.apache.beam.sdk.transforms.DoFn;

import java.io.IOException;

@Slf4j
public class ParseCompany extends DoFn<FileIO.ReadableFile, Company> {

    @ProcessElement
    public void processElement(@Element FileIO.ReadableFile input, OutputReceiver<Company> out) {
        String inputXMLString = null;
        try {
            inputXMLString = input.readFullyAsUTF8String();
        } catch (IOException e) {
            log.error("Could not read file {}", input.getMetadata().resourceId().getFilename(), e);
        }


        if (inputXMLString != null) {
            Company company = parseCompany(inputXMLString);
            if (company != null) {
                out.output(company);
            }
        }
    }

    Company parseCompany(String payload) {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CASE);
        Company value = null;
        try {
            value = xmlMapper.readValue(payload, Company.class);
        } catch (Exception e) {
            log.error("Could not parse file", e);
        }
        return value;
    }
}
