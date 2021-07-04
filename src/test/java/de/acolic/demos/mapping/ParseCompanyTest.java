package de.acolic.demos.mapping;

import de.acolic.demos.model.Address;
import de.acolic.demos.model.Company;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ParseCompanyTest {
    ParseCompany parseCompany = new ParseCompany();

    static Stream<Arguments> parseCompany() {
        Company company = Company.builder()
                .name("Test GmbH")
                .phoneNumbers(List.of("0171-1234567", "0172-1234567", "0173-1234567"))
                .addresses(List.of(
                        Address.builder()
                                .street("Erste str.")
                                .houseNumber("1")
                                .city("Berlin")
                                .zipCode("12345")
                                .build(),
                        Address.builder()
                                .street("Zweite str.")
                                .houseNumber("1")
                                .city("Berlin")
                                .zipCode("12345")
                                .build(),
                        Address.builder()
                                .street("Dritte str.")
                                .houseNumber("1")
                                .city("Berlin")
                                .zipCode("12345")
                                .build()
                ))
                .build();
        return Stream.of(
                arguments("<company>\n" +
                        "    <name>Test GmbH</name>\n" +
                        "    <phonenumbers>\n" +
                        "        <number>0171-1234567</number>\n" +
                        "        <number>0172-1234567</number>\n" +
                        "        <number>0173-1234567</number>\n" +
                        "    </phonenumbers>\n" +
                        "    <addresses>\n" +
                        "        <address>\n" +
                        "            <street>Erste str.</street>\n" +
                        "            <housenumber>1</housenumber>\n" +
                        "            <city>Berlin</city>\n" +
                        "            <zipcode>12345</zipcode>\n" +
                        "        </address>\n" +
                        "        <address>\n" +
                        "            <street>Zweite str.</street>\n" +
                        "            <housenumber>1</housenumber>\n" +
                        "            <city>Berlin</city>\n" +
                        "            <zipcode>12345</zipcode>\n" +
                        "        </address>\n" +
                        "        <address>\n" +
                        "            <street>Dritte str.</street>\n" +
                        "            <housenumber>1</housenumber>\n" +
                        "            <city>Berlin</city>\n" +
                        "            <zipcode>12345</zipcode>\n" +
                        "        </address>\n" +
                        "    </addresses>\n" +
                        "</company>", company),
                arguments("", null)
        );
    }

    @ParameterizedTest
    @MethodSource
    void parseCompany(String input, Company output) {
        assertThat(parseCompany.parseCompany(input)).isEqualTo(output);
    }

}