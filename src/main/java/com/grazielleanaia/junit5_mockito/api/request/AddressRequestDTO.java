package com.grazielleanaia.junit5_mockito.api.request;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class AddressRequestDTO {

    private String street;

    private String city;

    private String state;

    private Long zipcode;
}
