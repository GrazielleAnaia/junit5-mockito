package com.grazielleanaia.junit5_mockito.api.request;

public class AddressRequestDTOFixture {

    public static AddressRequestDTO build(
            String street,
            String city,
            String state,
            Long zipcode) {

        return new AddressRequestDTO(street, city, state, zipcode);
    }
}
