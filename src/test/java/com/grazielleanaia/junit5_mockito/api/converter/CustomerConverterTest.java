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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringBootConfiguration;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@SpringBootConfiguration
public class CustomerConverterTest {

    @Mock
    Clock clock;

    @InjectMocks
    CustomerConverter customerConverter;
    CustomerEntity customerEntity;
    CustomerRequestDTO customerRequestDTO;
    AddressEntity addressEntity;
    AddressRequestDTO addressRequestDTO;
    LocalDateTime dateTime;

    @BeforeEach
    public void setup() {
        dateTime = LocalDateTime.of(2025, 05, 12, 11, 30, 59);
        addressEntity = AddressEntity.builder().street("17 Java St").city("Spring").state("OH").zipcode(17017L).build();
        customerEntity = CustomerEntity.builder().name("Customer Server").email("customer@email.com").document("12345").
                registrationDate(dateTime).address(addressEntity).build();

        addressRequestDTO = AddressRequestDTOFixture.build("17 Java St", "Spring", "OH", 17017L);
        customerRequestDTO = CustomerRequestDTOFixture.build("Customer Server", "customer@email.com", "12345", dateTime, addressRequestDTO);
        ZoneId zoneId = ZoneId.systemDefault();
        Clock fixedClock = Clock.fixed(dateTime.atZone(zoneId).toInstant(), zoneId);
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();
    }

    @Test
    void convertToCustomerEntity() {
        CustomerEntity entity = customerConverter.toCustomerEntity(customerRequestDTO);
        assertEquals(customerEntity, entity);
    }


}
