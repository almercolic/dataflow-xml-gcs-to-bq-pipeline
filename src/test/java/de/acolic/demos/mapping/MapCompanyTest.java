package de.acolic.demos.mapping;


import com.google.api.services.bigquery.model.TableRow;
import de.acolic.demos.model.Address;
import de.acolic.demos.model.Company;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class MapCompanyTest {
    MapCompany mapCompany = new MapCompany();

    static Stream<Arguments> mapCompany() {
        Company company = Company.builder()
                .name("Test GmbH")
                .phoneNumbers(List.of("0171-1234567", "0172-1234567"))
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
                                .build()))
                .build();
        return Stream.of(
                arguments(company,
                        new TableRow()
                                .set("name", company.getName())
                                .set("phone_numbers",
                                        company.getPhoneNumbers().stream()
                                                .map(number -> new TableRow().set("phone_number", number))
                                                .collect(Collectors.toList()))
                                .set("address",
                                        company.getAddresses().stream()
                                                .map(address -> new TableRow()
                                                        .set("street", address.getStreet())
                                                        .set("house_number", address.getHouseNumber())
                                                        .set("city", address.getCity())
                                                        .set("zip_code", address.getZipCode()))
                                                .collect(Collectors.toList())))
        );
    }

    @ParameterizedTest
    @MethodSource
    void mapCompany(Company company, TableRow row) {
        assertThat(mapCompany.apply(company)).isEqualTo(row);
    }
}