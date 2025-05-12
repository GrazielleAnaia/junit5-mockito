package com.grazielleanaia.junit5_mockito.api.request;

public class CustomerRequestDTOFixture {
    public static CustomerRequestDTO build(String name,
                                           String email,
                                           String document,
                                           AddressRequestDTO addressDTO) {

        return new CustomerRequestDTO(name, email, document, addressDTO);
    }
}
