package com.grazielleanaia.junit5_mockito.business;

import com.grazielleanaia.junit5_mockito.api.converter.CustomerConverter;
import com.grazielleanaia.junit5_mockito.api.converter.CustomerMapper;
import com.grazielleanaia.junit5_mockito.api.converter.CustomerUpdateMapper;
import com.grazielleanaia.junit5_mockito.api.request.CustomerRequestDTO;
import com.grazielleanaia.junit5_mockito.api.response.CustomerResponseDTO;
import com.grazielleanaia.junit5_mockito.infrastructure.entity.CustomerEntity;
import com.grazielleanaia.junit5_mockito.infrastructure.exception.BusinessException;
import com.grazielleanaia.junit5_mockito.infrastructure.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static org.springframework.util.Assert.notNull;

@Service
@RequiredArgsConstructor

public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerConverter customerConverter;
    private final CustomerMapper customerMapper;
    private final CustomerUpdateMapper customerUpdateMapper;

    public CustomerEntity saveCustomer(CustomerEntity customerEntity) {
        return customerRepository.saveAndFlush(customerEntity);
    }

    public CustomerResponseDTO createCustomer(CustomerRequestDTO customerRequestDTO) {
        try {
            notNull(customerRequestDTO, "Customer info is mandatory");
            CustomerEntity entity = saveCustomer(customerConverter.toCustomerEntity(customerRequestDTO));
            return customerMapper.toCustomerResponseDTO(entity);
        } catch (Exception e) {
            throw new BusinessException("Error to save customer info", e.getCause());
        }
    }

    public CustomerResponseDTO getCustomerByEmail(String email) {
        CustomerEntity customer = customerRepository.findByEmail(email);
        return customer != null ? customerMapper.toCustomerResponseDTO(customer) : null;
    }

    public void deleteCustomer(String email) {
        customerRepository.deleteByEmail(email);
    }

    public CustomerResponseDTO updateCustomer(CustomerRequestDTO customerRequestDTO) {
        try {
            notNull(customerRequestDTO, "Customer info is mandatory");
            CustomerEntity customer = customerRepository.findByEmail(customerRequestDTO.getEmail());
            CustomerEntity entity = customerUpdateMapper.updateToCustomerEntity(customerRequestDTO, customer);
            return customerMapper.toCustomerResponseDTO(saveCustomer(entity));
        } catch (Exception e) {
            throw new BusinessException("Error to save customer info", e.getCause());
        }
    }

}
