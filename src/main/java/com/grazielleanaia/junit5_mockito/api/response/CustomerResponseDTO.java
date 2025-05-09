package com.grazielleanaia.junit5_mockito.api.response;

import java.time.LocalDateTime;

public record CustomerResponseDTO(Long id,

                                  String name,

                                  String email,

                                  String document,

                                  LocalDateTime registrationDate,
                                  AddressResponseDTO addressDTO) {
}
