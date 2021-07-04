package de.acolic.demos.model;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.apache.beam.sdk.coders.AvroCoder;
import org.apache.beam.sdk.coders.DefaultCoder;

@Data
@Jacksonized
@Builder
@DefaultCoder(AvroCoder.class)
public class Address {
    String street;
    String houseNumber;
    String city;
    String zipCode;
}
