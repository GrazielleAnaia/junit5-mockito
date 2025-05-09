package com.grazielleanaia.junit5_mockito.api.response;

public record AddressResponseDTO(Long id,

                                 String street,

                                 String city,

                                 String state,

                                 Long zipcode) {
}
