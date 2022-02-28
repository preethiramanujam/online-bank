package au.com.cashapp.onlinebank.unit.handler;

import au.com.cashapp.onlinebank.entity.Account;
import au.com.cashapp.onlinebank.exception.InvalidTransactionException;
import au.com.cashapp.onlinebank.handler.DebitTransactionHandler;
import au.com.cashapp.onlinebank.model.TransactionRequest;
import au.com.cashapp.onlinebank.model.TransactionResponse;
import au.com.cashapp.onlinebank.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static au.com.cashapp.onlinebank.unit.TestFixtureUtil.getAccount;
import static au.com.cashapp.onlinebank.unit.TestFixtureUtil.getTransactionRequest;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DebitTransactionHandlerTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private DebitTransactionHandler debitTransactionHandler;

    @Test
    public void testisRightHandlerForTransaction_Credit_Return_True(){
        assertTrue(debitTransactionHandler.isRightHandlerForTransaction("withdraw"));
    }

    @Test

    public void testisRightHandlerForTransaction_Debit_Return_False(){
        assertFalse(debitTransactionHandler.isRightHandlerForTransaction("deposit"));
    }

    @Test
    public void testHandleTransaction_Success(){
        Account account = getAccount();
        TransactionRequest request = getTransactionRequest("deposit");
        when(accountRepository.save(Mockito.any())).thenReturn(account);

        TransactionResponse response = debitTransactionHandler.handleTransaction(request, account);
        assertNotNull(response);
        assertEquals(response.getBalance(), BigDecimal.valueOf(290));

    }

    @Test
    public void testHandleTransaction_InvalidTransaction_AmountGreaterThanBalance(){
        TransactionRequest request = getTransactionRequest("deposit");
        request.setAmount(BigDecimal.valueOf(400));

        Exception exception = assertThrows(InvalidTransactionException.class, () ->
                debitTransactionHandler.handleTransaction(request, getAccount()));
        assertTrue(exception instanceof InvalidTransactionException);

    }

    @Test
    public void testHandleTransaction_DbFailure(){
        TransactionRequest request = getTransactionRequest("deposit");

        when(accountRepository.save(Mockito.any())).thenThrow(new IllegalArgumentException());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                debitTransactionHandler.handleTransaction(request, getAccount()));
        assertTrue(exception instanceof IllegalArgumentException);

    }
}
