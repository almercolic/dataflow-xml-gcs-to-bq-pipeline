package de.acolic.demos.mapping;

import com.google.api.services.bigquery.model.TableFieldSchema;
import com.google.api.services.bigquery.model.TableRow;
import com.google.api.services.bigquery.model.TableSchema;
import de.acolic.demos.model.Company;
import lombok.extern.slf4j.Slf4j;
import org.apache.beam.sdk.transforms.SerializableFunction;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

@Slf4j
public class MapCompany implements SerializableFunction<Company, TableRow> {
    @Override
    public TableRow apply(final Company input) {
        if (input == null) {
            log.info("Company is not defined");
            return null;
        }
        log.info("Mapping Company {}", input.getName());
        return new TableRow()
                .set("name", input.getName())
                .set("phone_numbers",
                        input.getPhoneNumbers().stream()
                                .map(number -> new TableRow().set("phone_number", number))
                                .collect(Collectors.toList()))
                .set("address",
                        input.getAddresses().stream()
                                .map(address -> new TableRow()
                                        .set("street", address.getStreet())
                                        .set("house_number", address.getHouseNumber())
                                        .set("city", address.getCity())
                                        .set("zip_code", address.getZipCode()))
                                .collect(Collectors.toList()));
    }

    public static TableSchema schema =
            new TableSchema()
                    .setFields(
                            Arrays.asList(
                                    new TableFieldSchema()
                                            .setName("name")
                                            .setType("STRING"),
                                    new TableFieldSchema()
                                            .setName("phone_numbers")
                                            .setType("STRUCT")
                                            .setMode("REPEATED")
                                            .setFields(
                                                    Collections.singletonList(
                                                            new TableFieldSchema().setName("phone_number").setType("STRING"))),
                                    new TableFieldSchema()
                                            .setName("address")
                                            .setType("STRUCT")
                                            .setMode("REPEATED")
                                            .setFields(
                                                    Arrays.asList(
                                                            new TableFieldSchema().setName("street").setType("STRING"),
                                                            new TableFieldSchema().setName("house_number").setType("STRING"),
                                                            new TableFieldSchema().setName("city").setType("STRING"),
                                                            new TableFieldSchema().setName("zip_code").setType("STRING")))));
}
