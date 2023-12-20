package com.springboot.openapi.generator.api;

import java.util.HashMap;

import com.springboot.openapi.generator.DTO.CustomerDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.openapi.generator.model.Customer;
import com.springboot.openapi.generator.model.CustomerFullData;


@RestController
public class CustomerController implements CustomerApi {

    private final HashMap<Long, CustomerDTO> customers = new HashMap<>();
    private Long index = 0L;

    @Override
    public ResponseEntity<CustomerFullData> createCustomer(Customer apiCustomer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerId(index);
        customerDTO.setFirstName(apiCustomer.getFirstName());
        customerDTO.setLastName(apiCustomer.getLastName());

        customers.put(index, customerDTO);
        index++;

        return ResponseEntity.ok(domainToApi(customerDTO));
    }

    @Override
    public ResponseEntity<CustomerFullData> getCustomer(Long customerId) {
        if (customers.containsKey(customerId)) {
            return ResponseEntity.ok(domainToApi(customers.get(customerId)));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private CustomerFullData domainToApi(CustomerDTO customerDTO) {
        CustomerFullData cfd = new CustomerFullData();
        cfd.setCustomerId(customerDTO.getCustomerId());
        cfd.setFirstName(customerDTO.getFirstName());
        cfd.setLastName(customerDTO.getLastName());
        return cfd;
    }
}
