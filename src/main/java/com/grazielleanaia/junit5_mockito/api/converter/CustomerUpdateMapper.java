package com.grazielleanaia.junit5_mockito.api.converter;

import com.grazielleanaia.junit5_mockito.api.request.CustomerRequestDTO;
import com.grazielleanaia.junit5_mockito.infrastructure.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CustomerUpdateMapper {
CustomerUpdateMapper INSTANCE = Mappers.getMapper(CustomerUpdateMapper.class);
    CustomerEntity updateToCustomerEntity(CustomerRequestDTO requestDTO, @MappingTarget CustomerEntity entity);
}
