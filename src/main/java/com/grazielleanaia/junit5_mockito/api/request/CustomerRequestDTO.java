package com.grazielleanaia.junit5_mockito.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode

public class CustomerRequestDTO {

    private String name;

    @JsonProperty(required = true)
    private String email;

    private String document;

    private LocalDateTime registrationDate;

    private AddressRequestDTO addressDTO;
}
