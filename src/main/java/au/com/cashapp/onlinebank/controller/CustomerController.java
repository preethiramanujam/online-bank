package au.com.cashapp.onlinebank.controller;

import au.com.cashapp.onlinebank.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1")
@Slf4j
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping(path = "/customer/{id}/account", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNewTransaction(@PathVariable long id) {
        log.info("Entering activate phone number for customer {}", id);
        return ResponseEntity.ok(customerService.getCustomerAccountDetails(id));
    }
}
