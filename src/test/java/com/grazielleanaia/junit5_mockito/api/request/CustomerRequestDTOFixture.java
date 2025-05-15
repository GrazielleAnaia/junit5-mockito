package com.grazielleanaia.junit5_mockito.api.request;

import java.time.LocalDateTime;

public class CustomerRequestDTOFixture {
    public static CustomerRequestDTO build(String name,
                                           String email,
                                           String document,
                                           LocalDateTime registrationDate,
                                           AddressRequestDTO addressDTO) {

        return new CustomerRequestDTO(name, email, document, registrationDate, addressDTO);
    }
}
