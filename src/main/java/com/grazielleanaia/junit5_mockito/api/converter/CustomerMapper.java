package com.grazielleanaia.junit5_mockito.api.converter;

import com.grazielleanaia.junit5_mockito.api.response.CustomerResponseDTO;
import com.grazielleanaia.junit5_mockito.infrastructure.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {


    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "document", target = "document")
    @Mapping(source = "address", target = "addressDTO")
    CustomerResponseDTO toCustomerResponseDTO(CustomerEntity customerEntity);


}
