package au.com.cashapp.onlinebank.unit.service;

import au.com.cashapp.onlinebank.entity.Account;
import au.com.cashapp.onlinebank.exception.InvalidAccountException;
import au.com.cashapp.onlinebank.exception.InvalidTransactionException;
import au.com.cashapp.onlinebank.handler.CreditTransactionHandler;
import au.com.cashapp.onlinebank.handler.DebitTransactionHandler;
import au.com.cashapp.onlinebank.model.TransactionRequest;
import au.com.cashapp.onlinebank.model.TransactionResponse;
import au.com.cashapp.onlinebank.repository.AccountRepository;
import au.com.cashapp.onlinebank.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.Optional;

import static au.com.cashapp.onlinebank.unit.TestFixtureUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CreditTransactionHandler creditTransactionHandler;

    @Mock
    private DebitTransactionHandler debitTransactionHandler;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(transactionService, "transactionHandlers",
                Arrays.asList(creditTransactionHandler, debitTransactionHandler));
    }

    @Test
    public void testCreateNewTransactionWith_Debit_Transaction_Type() {

        //Given
        TransactionRequest transactionRequest = getTransactionRequest(WITHDRAW);

        //When
        when(debitTransactionHandler.isRightHandlerForTransaction(WITHDRAW)).thenReturn(true);
        when(debitTransactionHandler.handleTransaction(any(TransactionRequest.class), any(Account.class))).thenReturn(new TransactionResponse());
        when(accountRepository.findById(VALID_ACCT_ID)).thenReturn(Optional.of(getAccount()));

        //Then
        TransactionResponse response = transactionService.createNewTransaction(VALID_ACCT_ID, transactionRequest);
        assertNotNull(response);
    }

    @Test
    public void testCreateNewTransactionWith_Credit_Transaction_Type() {

        //Given
        TransactionRequest transactionRequest = getTransactionRequest(DEPOSIT);

        //When
        when(creditTransactionHandler.isRightHandlerForTransaction(DEPOSIT)).thenReturn(true);
        when(creditTransactionHandler.handleTransaction(any(TransactionRequest.class), any(Account.class))).thenReturn(new TransactionResponse());
        when(accountRepository.findById(VALID_ACCT_ID)).thenReturn(Optional.of(getAccount()));

        //Then
        TransactionResponse response = transactionService.createNewTransaction(VALID_ACCT_ID, transactionRequest);
        assertNotNull(response);
    }

    @Test
    public void testCreateNewTransactionWith_InvalidAccountId() {
        Exception exception = assertThrows(InvalidAccountException.class, () ->
                transactionService.createNewTransaction(100, new TransactionRequest()));
        assertTrue(exception instanceof InvalidAccountException);
    }

    @Test
    public void testCreateNewTransactionWith_InvalidTransactionType() {
        when(accountRepository.findById(VALID_ACCT_ID)).thenReturn(Optional.of(getAccount()));

        Exception exception = assertThrows(InvalidTransactionException.class, () ->
                transactionService.createNewTransaction(VALID_ACCT_ID, new TransactionRequest()));
        assertTrue(exception instanceof InvalidTransactionException);
    }


}
