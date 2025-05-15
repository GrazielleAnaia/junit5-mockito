package com.grazielleanaia.junit5_mockito.api.converter;

import com.grazielleanaia.junit5_mockito.api.request.AddressRequestDTO;
import com.grazielleanaia.junit5_mockito.api.request.CustomerRequestDTO;
import com.grazielleanaia.junit5_mockito.infrastructure.entity.AddressEntity;
import com.grazielleanaia.junit5_mockito.infrastructure.entity.CustomerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;



@Component
@RequiredArgsConstructor

public class CustomerConverter {
    private final Clock clock;

    public CustomerEntity toCustomerEntity(CustomerRequestDTO customerRequestDTO) {
        return CustomerEntity.builder()
                .name(customerRequestDTO.getName())
                .email(customerRequestDTO.getEmail())
                .document(customerRequestDTO.getDocument())
                .registrationDate(LocalDateTime.now(clock))
                .address(toAddressEntity(customerRequestDTO.getAddressDTO()))
                .build();
    }

    public AddressEntity toAddressEntity(AddressRequestDTO addressRequestDTO) {
        return AddressEntity.builder()
                .street(addressRequestDTO.getStreet())
                .city(addressRequestDTO.getCity())
                .state(addressRequestDTO.getState())
                .zipcode(addressRequestDTO.getZipcode())
                .build();
    }
}
