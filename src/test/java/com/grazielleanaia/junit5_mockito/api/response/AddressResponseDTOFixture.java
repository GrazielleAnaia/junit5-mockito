package com.grazielleanaia.junit5_mockito.api.response;

public class AddressResponseDTOFixture {

    public static AddressResponseDTO build(Long id,
                                           String street,
                                           String city,
                                           String state,
                                           Long zipcode) {

        return new AddressResponseDTO(id, street, city, state, zipcode);
    }
}
