package au.com.cashapp.onlinebank.unit.handler;

import au.com.cashapp.onlinebank.entity.Account;
import au.com.cashapp.onlinebank.exception.InvalidAccountException;
import au.com.cashapp.onlinebank.handler.CreditTransactionHandler;
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

import static au.com.cashapp.onlinebank.unit.TestFixtureUtil.*;
import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreditTransactionHandlerTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private CreditTransactionHandler creditTransactionHandler;

    @Test
    public void testIsRightHandlerForTransaction_Credit_Return_True(){
        assertTrue(creditTransactionHandler.isRightHandlerForTransaction(DEPOSIT));
    }

    @Test

    public void testIsRightHandlerForTransaction_Debit_Return_False(){
        assertFalse(creditTransactionHandler.isRightHandlerForTransaction(WITHDRAW));
    }

    @Test
    public void testHandleTransaction_Success(){
        Account account = getAccount();
        TransactionRequest request = getTransactionRequest(DEPOSIT);
        when(accountRepository.save(Mockito.any())).thenReturn(account);

        TransactionResponse response = creditTransactionHandler.handleTransaction(request, account);
        assertNotNull(response);
        assertEquals(response.getBalance(), BigDecimal.valueOf(310));

    }

    @Test
    public void testHandleTransaction_DbFailure(){
        TransactionRequest request = getTransactionRequest(DEPOSIT);

        when(accountRepository.save(Mockito.any())).thenThrow(new IllegalArgumentException());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                creditTransactionHandler.handleTransaction(request, getAccount()));
        assertTrue(exception instanceof IllegalArgumentException);

    }
}
