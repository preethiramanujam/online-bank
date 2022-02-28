package au.com.cashapp.onlinebank.service;

import au.com.cashapp.onlinebank.entity.Account;
import au.com.cashapp.onlinebank.exception.InvalidAccountException;
import au.com.cashapp.onlinebank.exception.InvalidTransactionException;
import au.com.cashapp.onlinebank.handler.TransactionHandler;
import au.com.cashapp.onlinebank.model.TransactionRequest;
import au.com.cashapp.onlinebank.model.TransactionResponse;
import au.com.cashapp.onlinebank.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TransactionService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private List<TransactionHandler> transactionHandlers;

    public TransactionResponse createNewTransaction(long accountId, TransactionRequest transactionRequest) {
        log.info("Creating new transaction for accountId : {}", accountId);
        Account account = accountRepository.findById(accountId).orElseThrow(() ->
                new InvalidAccountException("Could not find account for provided account id"));

        TransactionResponse response = transactionHandlers.stream()
                .filter(transactionHandler -> transactionHandler.isRightHandlerForTransaction(transactionRequest.getTransactionType()))
                .map(transactionHandler -> transactionHandler.handleTransaction(transactionRequest, account))
                .findFirst().orElseThrow(() -> new InvalidTransactionException("Transaction type invalid"));
        return response;
    }
}
