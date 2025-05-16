package com.grazielleanaia.junit5_mockito.business;

import com.grazielleanaia.junit5_mockito.api.converter.CustomerConverter;
import com.grazielleanaia.junit5_mockito.api.converter.CustomerMapper;
import com.grazielleanaia.junit5_mockito.api.converter.CustomerUpdateMapper;
import com.grazielleanaia.junit5_mockito.api.request.AddressRequestDTO;
import com.grazielleanaia.junit5_mockito.api.request.AddressRequestDTOFixture;
import com.grazielleanaia.junit5_mockito.api.request.CustomerRequestDTO;
import com.grazielleanaia.junit5_mockito.api.request.CustomerRequestDTOFixture;
import com.grazielleanaia.junit5_mockito.api.response.AddressResponseDTO;
import com.grazielleanaia.junit5_mockito.api.response.AddressResponseDTOFixture;
import com.grazielleanaia.junit5_mockito.api.response.CustomerResponseDTO;
import com.grazielleanaia.junit5_mockito.api.response.CustomerResponseDTOFixture;
import com.grazielleanaia.junit5_mockito.infrastructure.entity.AddressEntity;
import com.grazielleanaia.junit5_mockito.infrastructure.entity.CustomerEntity;
import com.grazielleanaia.junit5_mockito.infrastructure.exception.BusinessException;
import com.grazielleanaia.junit5_mockito.infrastructure.exception.ResourceNotFoundException;
import com.grazielleanaia.junit5_mockito.infrastructure.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringBootConfiguration;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootConfiguration
public class CustomerServiceTest {
    @InjectMocks
    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;
    @Mock
    CustomerConverter customerConverter;
    @Mock
    CustomerMapper customerMapper;
    @Mock
    CustomerUpdateMapper customerUpdateMapper;
    @Mock
    CustomerEntity customerEntity;
    @Mock
    CustomerRequestDTO customerRequestDTO;
    @Mock
    AddressEntity addressEntity;
    @Mock
    AddressRequestDTO addressRequestDTO;
    @Mock
    CustomerResponseDTO customerResponseDTO;
    @Mock
    AddressResponseDTO addressResponseDTO;

    LocalDateTime dateTime;

    String email;

    @BeforeEach
    public void setup() {
        dateTime = LocalDateTime.of(2025, 05, 12, 11, 30, 59);

        addressEntity = AddressEntity.builder().id(123L).street("17 Java St").city("Spring").state("OH").zipcode(17017L).build();
        customerEntity = CustomerEntity.builder().id(123L).name("Customer Server").email("customer@email.com").document("12345").
                registrationDate(dateTime).address(addressEntity).build();


        addressRequestDTO = AddressRequestDTOFixture.build("17 Java St", "Spring", "OH", 17017L);
        customerRequestDTO = CustomerRequestDTOFixture.build("Customer Server", "customer@email.com", "12345", dateTime, addressRequestDTO);

        addressResponseDTO = AddressResponseDTOFixture.build(123L, "17 Java St", "Spring", "OH", 17017L);
        customerResponseDTO = CustomerResponseDTOFixture.build(123L, "Customer Server", "customer@email.com", "12345", dateTime, addressResponseDTO);
        email = "customer@email.com";
    }

    @Test
    void shouldSaveCustomer() {
        when(customerRepository.saveAndFlush(customerEntity)).thenReturn(customerEntity);
        CustomerEntity customer = customerService.saveCustomer(customerEntity);
        assertEquals(customerEntity, customer);
        verify(customerRepository).saveAndFlush(customerEntity);

    }

    @Test
    void shouldCreateCustomerSuccessfully() {
        when(customerConverter.toCustomerEntity(customerRequestDTO)).thenReturn(customerEntity);
        when(customerRepository.saveAndFlush(customerEntity)).thenReturn(customerEntity);
        when(customerMapper.toCustomerResponseDTO(customerEntity)).thenReturn(customerResponseDTO);
        CustomerResponseDTO customerDTO = customerService.createCustomer(customerRequestDTO);
        assertEquals(customerDTO, customerResponseDTO);
        verify(customerConverter).toCustomerEntity(customerRequestDTO);
        verify(customerRepository).saveAndFlush(customerEntity);
        verify(customerMapper).toCustomerResponseDTO(customerEntity);
        verifyNoMoreInteractions(customerConverter, customerRepository, customerMapper);
    }

    @Test
    void shouldNotCreateCustomerIfRequestDTONull() {
        BusinessException exception = assertThrows(BusinessException.class, () ->
                customerService.createCustomer(null));
        assertThat(exception, notNullValue());
        assertThat(exception.getMessage(), is("Error to save customer info"));
        assertThat(exception.getCause().getMessage(), is("Customer info is mandatory"));
        assertThat(exception.getClass(), notNullValue());
        verifyNoInteractions(customerConverter, customerMapper, customerRepository);
    }

    @Test
    void shouldGenerateExceptionMessageToCreateCustomer() {
        when(customerConverter.toCustomerEntity(customerRequestDTO)).thenReturn(customerEntity);
        when(customerRepository.saveAndFlush(customerEntity)).thenThrow(new RuntimeException("Failed to save customer"));
        BusinessException exception = assertThrows(BusinessException.class, () ->
                customerService.createCustomer(customerRequestDTO));
        assertThat(exception, notNullValue());
        assertThat(exception.getMessage(), is("Error to save customer info"));
        assertThat(exception.getCause().getMessage(), is("Failed to save customer"));
        assertThat(exception.getCause().getClass(), is(RuntimeException.class));
        verify(customerConverter).toCustomerEntity(customerRequestDTO);
        verify(customerRepository).saveAndFlush(customerEntity);
        verifyNoMoreInteractions(customerConverter, customerRepository);
    }

    @Test
    void shouldUpdateCustomer() {
        when(customerRepository.findByEmail(email)).thenReturn(customerEntity);
        when(customerUpdateMapper.updateToCustomerEntity(customerRequestDTO, customerEntity)).thenReturn(customerEntity);
        when(customerRepository.saveAndFlush(customerEntity)).thenReturn(customerEntity);
        when(customerMapper.toCustomerResponseDTO(customerEntity)).thenReturn(customerResponseDTO);

        CustomerResponseDTO customerResponse = customerService.updateCustomer(customerRequestDTO);
        assertEquals(customerResponseDTO, customerResponse);
        verify(customerRepository).findByEmail(email);
        verify(customerUpdateMapper).updateToCustomerEntity(customerRequestDTO, customerEntity);
        verify(customerRepository).saveAndFlush(customerEntity);
        verify(customerMapper).toCustomerResponseDTO(customerEntity);
        verifyNoMoreInteractions(customerRepository, customerUpdateMapper, customerMapper);
    }

    @Test
    void shouldNotUpdateCustomerIfRequestDTONull() {
        BusinessException exception = assertThrows(BusinessException.class, () ->
                customerService.updateCustomer(null));
        assertThat(exception, notNullValue());
        assertThat(exception.getMessage(), is("Error to save customer info"));
        assertThat(exception.getCause().getMessage(), is("Customer info is mandatory"));
        assertThat(exception.getClass(), is(BusinessException.class));
        assertThat(exception.getClass(), notNullValue());
        verifyNoInteractions(customerRepository, customerUpdateMapper, customerMapper);
    }

    @Test
    void shouldGenerateExceptionMessageToUpdateCustomer() {
        when(customerRepository.findByEmail(email)).thenThrow(new RuntimeException("Failed to save customer"));
        BusinessException exception = assertThrows(BusinessException.class, () ->
                customerService.updateCustomer(customerRequestDTO));
        assertThat(exception, notNullValue());
        assertThat(exception.getMessage(), is("Error to save customer info"));
        assertThat(exception.getCause().getMessage(), is("Failed to save customer"));
        assertThat(exception.getCause().getClass(), is(RuntimeException.class));
        verify(customerRepository).findByEmail(email);
        verifyNoMoreInteractions(customerRepository);
        verifyNoInteractions(customerUpdateMapper, customerMapper);
    }



    @Test
    void shouldGetCustomerByEmail() {
        when(customerRepository.findByEmail(email)).thenReturn(customerEntity);
        when(customerMapper.toCustomerResponseDTO(customerEntity)).thenReturn(customerResponseDTO);
        CustomerResponseDTO customerResponse = customerService.getCustomerByEmail(email);
        assertEquals(customerResponseDTO, customerResponse);
        verify(customerRepository).findByEmail(email);
        verify(customerMapper).toCustomerResponseDTO(customerEntity);
        verifyNoMoreInteractions(customerRepository, customerMapper);
    }

    @Test
    void shouldReturnNullIfCustomerEmailNotFound() {
        when(customerRepository.findByEmail(email)).thenReturn(null);
        CustomerResponseDTO customerResponse = customerService.getCustomerByEmail(email);
        assertNull(customerResponse);
        verify(customerRepository).findByEmail(email);
        verifyNoMoreInteractions(customerRepository);
        verifyNoInteractions(customerMapper);
    }

    @Test
    void shouldDeleteCustomerByEmail() {
        when(customerRepository.findByEmail(email)).thenReturn(customerEntity);
        doNothing().when(customerRepository).deleteByEmail(email);
        customerService.deleteCustomer(email);
        verify(customerRepository).findByEmail(email);
        verify(customerRepository).deleteByEmail(email);
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    void shouldGenerateExceptionIfCustomerNotFound() {
        when(customerRepository.findByEmail(email)).thenThrow(new ResourceNotFoundException("Failed to find customer"));
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, ()->
                customerService.deleteCustomer(email));
        assertThat(exception, notNullValue());
        assertThat(exception.getMessage(), is("Customer not found"));
        assertThat(exception.getCause().getMessage(), is("Failed to find customer"));
        assertThat(exception.getCause().getClass(), is(ResourceNotFoundException.class));
        verify(customerRepository).findByEmail(email);
        verifyNoMoreInteractions(customerRepository);
    }



}
