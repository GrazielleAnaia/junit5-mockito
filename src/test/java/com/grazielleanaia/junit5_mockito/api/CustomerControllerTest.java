package com.grazielleanaia.junit5_mockito.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.grazielleanaia.junit5_mockito.api.request.AddressRequestDTO;
import com.grazielleanaia.junit5_mockito.api.request.AddressRequestDTOFixture;
import com.grazielleanaia.junit5_mockito.api.request.CustomerRequestDTO;
import com.grazielleanaia.junit5_mockito.api.request.CustomerRequestDTOFixture;
import com.grazielleanaia.junit5_mockito.api.response.AddressResponseDTO;
import com.grazielleanaia.junit5_mockito.api.response.AddressResponseDTOFixture;
import com.grazielleanaia.junit5_mockito.api.response.CustomerResponseDTO;
import com.grazielleanaia.junit5_mockito.api.response.CustomerResponseDTOFixture;
import com.grazielleanaia.junit5_mockito.business.CustomerService;
import com.grazielleanaia.junit5_mockito.infrastructure.entity.AddressEntity;
import com.grazielleanaia.junit5_mockito.infrastructure.entity.CustomerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)

public class CustomerControllerTest {

    @InjectMocks
    CustomerController customerController;


    @Mock
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private CustomerService customerService;
    private CustomerResponseDTO customerResponseDTO;
    private AddressResponseDTO addressResponseDTO;

    private CustomerRequestDTO customerRequestDTO;
    private AddressRequestDTO addressRequestDTO;

    private String email;
    private String json;
    private String url;

    LocalDateTime dateTime;


    @BeforeEach
    void setup() throws JsonProcessingException {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).alwaysDo(print()).build();
        url = "/customer";
        objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(dateTime);
        dateTime = LocalDateTime.of(2025, 05, 12, 11, 30, 59);
        addressRequestDTO = AddressRequestDTOFixture.build("17 Java St", "Spring", "OH", 17017L);
        customerRequestDTO = CustomerRequestDTOFixture.build("Customer Server", "customer@email.com", "12345", dateTime, addressRequestDTO);

        addressResponseDTO = AddressResponseDTOFixture.build(123L, "17 Java St", "Spring", "OH", 17017L);
        customerResponseDTO = CustomerResponseDTOFixture.build(123L, "Customer Server", "customer@email.com", "12345", dateTime, addressResponseDTO);
        email = "customer@email.com";
        json = objectMapper.writeValueAsString(customerRequestDTO);

    }

    @Test
    void shouldSaveCustomerSuccessfuly() throws Exception {
        when(customerService.createCustomer(customerRequestDTO)).thenReturn(customerResponseDTO);
        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
        verify(customerService).createCustomer(customerRequestDTO);
        verifyNoMoreInteractions(customerService);
    }

    @Test
    void shouldNotSaveCustomerIfJsonNull() throws Exception {
        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verifyNoInteractions(customerService);
    }

    @Test
    void shouldUpdateCustomerSuccessfully() throws Exception {
        when(customerService.updateCustomer(customerRequestDTO)).thenReturn(customerResponseDTO);
        mockMvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
        verify(customerService).updateCustomer(customerRequestDTO);
        verifyNoMoreInteractions(customerService);
    }

    @Test
    void shouldNotUpdateCustomerIfJsonNull() throws Exception {
        mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verifyNoInteractions(customerService);
    }

    @Test
    void shouldFindCustomerByEmailSuccessfully() throws Exception {
        when(customerService.getCustomerByEmail("customer@email.com")).thenReturn(customerResponseDTO);
        mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("email", "customer@email.com"))
                .andExpect(status().isOk());
        verify(customerService).getCustomerByEmail(email);
        verifyNoMoreInteractions(customerService);
    }

    @Test
    void shouldNotFindCustomerIfEmailNotFound() throws Exception {
        mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verifyNoInteractions(customerService);
    }

    @Test
    void shouldDeleteCustomerSuccessfully() throws Exception {
        doNothing().when(customerService).deleteCustomer(email);
        mockMvc.perform(delete(url).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("email", email))
                .andExpect(status().isAccepted());
        verify(customerService).deleteCustomer(email);
        verifyNoMoreInteractions(customerService);
    }

    @Test
    void shouldDeleteCustomerIfEmailNotPassed() throws Exception {
        mockMvc.perform(delete(url).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verifyNoInteractions(customerService);
    }


}
