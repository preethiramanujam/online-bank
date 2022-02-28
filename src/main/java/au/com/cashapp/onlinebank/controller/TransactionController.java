package au.com.cashapp.onlinebank.controller;

import au.com.cashapp.onlinebank.model.TransactionRequest;
import au.com.cashapp.onlinebank.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1")
@Slf4j
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(final TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(path = "/accounts/{id}/transaction", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNewTransaction(@PathVariable long id, @RequestBody TransactionRequest request) {
        log.info("Entering activate phone number for customer {}", id);
        return ResponseEntity.ok(transactionService.createNewTransaction(id, request));
    }
}




