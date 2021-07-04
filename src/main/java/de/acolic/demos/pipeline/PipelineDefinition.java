package de.acolic.demos.pipeline;

import de.acolic.demos.mapping.MapCompany;
import de.acolic.demos.mapping.ParseCompany;
import de.acolic.demos.model.Company;
import lombok.extern.slf4j.Slf4j;
import org.apache.beam.sdk.io.FileIO;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.options.ValueProvider.StaticValueProvider;
import org.apache.beam.sdk.transforms.ParDo;

@Slf4j
public class PipelineDefinition {


    public org.apache.beam.sdk.Pipeline applyTransforms(org.apache.beam.sdk.Pipeline pipeline) {
        Options options = pipeline.getOptions().as(Options.class);
        pipeline
                .apply("Match files", FileIO.match().filepattern(options.getSourcePath()))
                .apply("Read files", FileIO.readMatches())
                .apply("Parse Companies", ParDo.of(new ParseCompany()))
                .apply("Insert into BQ",
                        BigQueryIO.<Company>write()
                                .to(options.getOutputTable())
                                .withSchema(MapCompany.schema)
                                .withFormatFunction(new MapCompany())
                                .withCustomGcsTempLocation(StaticValueProvider.of(options.getGcpTempLocation()))
                                .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_TRUNCATE)
                                .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_IF_NEEDED)
                                .withExtendedErrorInfo());
        return pipeline;
    }

}
