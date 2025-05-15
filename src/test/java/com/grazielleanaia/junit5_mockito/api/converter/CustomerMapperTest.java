package com.grazielleanaia.junit5_mockito.api.converter;


import com.grazielleanaia.junit5_mockito.api.response.AddressResponseDTO;
import com.grazielleanaia.junit5_mockito.api.response.AddressResponseDTOFixture;
import com.grazielleanaia.junit5_mockito.api.response.CustomerResponseDTO;
import com.grazielleanaia.junit5_mockito.api.response.CustomerResponseDTOFixture;
import com.grazielleanaia.junit5_mockito.infrastructure.entity.AddressEntity;
import com.grazielleanaia.junit5_mockito.infrastructure.entity.CustomerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class CustomerMapperTest {

    CustomerMapper customerMapper;
    CustomerResponseDTO customerResponseDTO;
    CustomerEntity customerEntity;
    AddressEntity addressEntity;
    AddressResponseDTO addressResponseDTO;

    LocalDateTime dateTime;

    @BeforeEach
    public void setup() {
        customerMapper = Mappers.getMapper(CustomerMapper.class);
        dateTime = LocalDateTime.of(2025, 05, 12, 11, 30, 59);
        addressEntity = AddressEntity.builder().id(123L).street("17 Java St").city("Spring").state("OH").zipcode(17017L).build();
        customerEntity = CustomerEntity.builder().id(123L).name("Customer Server").email("customer@email.com").document("12345").
                registrationDate(dateTime).address(addressEntity).build();

        addressResponseDTO = AddressResponseDTOFixture.build(123L, "17 Java St", "Spring", "OH", 17017L);
        customerResponseDTO = CustomerResponseDTOFixture.build(123L, "Customer Server", "customer@email.com", "12345", dateTime, addressResponseDTO);
    }

    @Test
    void convertToCustomerResponseDTO() {
        CustomerResponseDTO customerDTO = customerMapper.toCustomerResponseDTO(customerEntity);
        assertEquals(customerResponseDTO, customerDTO);
    }

}
