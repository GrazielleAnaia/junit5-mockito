package com.grazielleanaia.junit5_mockito.api.response;

import java.time.LocalDateTime;

public class CustomerResponseDTOFixture {

    public static CustomerResponseDTO build(Long id,
                                            String name,
                                            String email,
                                            String document,
                                            LocalDateTime registrationDate,
                                            AddressResponseDTO addressDTO) {

        return new CustomerResponseDTO(id, name, email, document, registrationDate, addressDTO);
    }
}
