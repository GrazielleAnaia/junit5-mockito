package com.grazielleanaia.junit5_mockito.api;

import com.grazielleanaia.junit5_mockito.api.request.CustomerRequestDTO;
import com.grazielleanaia.junit5_mockito.api.response.CustomerResponseDTO;
import com.grazielleanaia.junit5_mockito.business.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor

public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> saveCustomerData(@RequestBody CustomerRequestDTO customerRequestDTO) {

        return ResponseEntity.ok(customerService.createCustomer(customerRequestDTO));
    }

    @GetMapping
    public ResponseEntity<CustomerResponseDTO> findCustomerByEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(customerService.getCustomerByEmail(email));
    }

    @PutMapping
    public ResponseEntity<CustomerResponseDTO> updateCustomerData(@RequestBody CustomerRequestDTO customerRequestDTO) {
        return ResponseEntity.ok(customerService.updateCustomer(customerRequestDTO));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCustomerByEmail(@RequestParam("email") String email) {
        customerService.deleteCustomer(email);
        return ResponseEntity.accepted().build();
    }
}
