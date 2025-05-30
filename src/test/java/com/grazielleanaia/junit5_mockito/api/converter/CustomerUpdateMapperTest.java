package com.grazielleanaia.junit5_mockito.api.converter;

import com.grazielleanaia.junit5_mockito.api.request.AddressRequestDTO;
import com.grazielleanaia.junit5_mockito.api.request.AddressRequestDTOFixture;
import com.grazielleanaia.junit5_mockito.api.request.CustomerRequestDTO;
import com.grazielleanaia.junit5_mockito.api.request.CustomerRequestDTOFixture;
import com.grazielleanaia.junit5_mockito.infrastructure.entity.AddressEntity;
import com.grazielleanaia.junit5_mockito.infrastructure.entity.CustomerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringBootConfiguration;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@SpringBootConfiguration
public class CustomerUpdateMapperTest {

    CustomerUpdateMapper customerUpdateMapper;
    CustomerEntity customerEntity;
    CustomerEntity customerEntityExpect;
    AddressEntity addressEntity;
    CustomerRequestDTO customerRequestDTO;
    AddressRequestDTO addressRequestDTO;

    LocalDateTime dateTime;

    @BeforeEach
    public void setup() {
        customerUpdateMapper = Mappers.getMapper(CustomerUpdateMapper.class);
        dateTime = LocalDateTime.of(2025, 05, 12, 11, 30, 59);
        addressEntity = AddressEntity.builder().id(123L).street("17 Java St").city("Spring").state("OH").zipcode(17017L).build();
        customerEntity = CustomerEntity.builder().id(123L).name("Customer Server").email("customer@email.com").document("12345").
                registrationDate(dateTime).address(addressEntity).build();

        addressRequestDTO = AddressRequestDTOFixture.build("17 Java St", "Spring", "OH", 17017L);
        customerRequestDTO = CustomerRequestDTOFixture.build("Customer Server", "customer@email.com", "123456", dateTime, addressRequestDTO);
        customerEntityExpect = CustomerEntity.builder().id(123L).name("Customer Server").email("customer@email.com").document("123456").
                registrationDate(dateTime).address(addressEntity).build();
    }

    @Test
    void updateToCustomerEntity() {
        CustomerEntity customer = customerUpdateMapper.updateToCustomerEntity(customerRequestDTO, customerEntity);
        assertEquals(customerEntityExpect, customer);
    }
}
