package com.grazielleanaia.junit5_mockito.api.converter;

import com.grazielleanaia.junit5_mockito.api.response.CustomerResponseDTO;
import com.grazielleanaia.junit5_mockito.infrastructure.entity.CustomerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerResponseDTO toCustomerResponseDTO(CustomerEntity customerEntity);


}
