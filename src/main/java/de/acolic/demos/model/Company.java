package de.acolic.demos.model;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.apache.beam.sdk.coders.AvroCoder;
import org.apache.beam.sdk.coders.DefaultCoder;

import java.util.List;

@Data
@Jacksonized
@Builder
@DefaultCoder(AvroCoder.class)
public class Company {
    String name;
    List<String> phoneNumbers;
    List<Address> addresses;
}