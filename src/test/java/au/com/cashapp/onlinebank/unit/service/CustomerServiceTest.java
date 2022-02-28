package au.com.cashapp.onlinebank.unit.service;

import au.com.cashapp.onlinebank.entity.Account;
import au.com.cashapp.onlinebank.exception.InvalidAccountException;
import au.com.cashapp.onlinebank.model.CustomerResponse;
import au.com.cashapp.onlinebank.repository.AccountRepository;
import au.com.cashapp.onlinebank.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static au.com.cashapp.onlinebank.unit.TestFixtureUtil.VALID_CUSTOMER_ID;
import static au.com.cashapp.onlinebank.unit.TestFixtureUtil.getAccount;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    public void testGetCustomerAccountDetailsWithValidCustId() {
        Account account = getAccount();
        when(accountRepository.findByCustomerId(VALID_CUSTOMER_ID)).thenReturn(Optional.of(Arrays.asList(account)));
        CustomerResponse customerResponse = customerService.getCustomerAccountDetails(VALID_CUSTOMER_ID);
        assertNotNull(customerResponse);
        assertEquals(VALID_CUSTOMER_ID, customerResponse.getId());
        assertEquals(123, customerResponse.getAccountList().stream().findFirst().get().getId());
    }

    @Test
    public void testGetCustomerAccountDetailsWithInvalidCustId() {

        Exception exception = assertThrows(InvalidAccountException.class, () ->
                customerService.getCustomerAccountDetails(100));
        assertTrue(exception instanceof InvalidAccountException);

    }

}
