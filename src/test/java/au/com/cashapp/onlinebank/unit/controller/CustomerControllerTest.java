package au.com.cashapp.onlinebank.unit.controller;

import au.com.cashapp.onlinebank.controller.CustomerController;
import au.com.cashapp.onlinebank.entity.Account;
import au.com.cashapp.onlinebank.exception.InvalidAccountException;
import au.com.cashapp.onlinebank.model.CustomerResponse;
import au.com.cashapp.onlinebank.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static au.com.cashapp.onlinebank.unit.TestFixtureUtil.getAccount;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CustomerControllerTest {

    public static final int CUSTOMER_ID = 1234;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    void testGetCustomerAccountDetailsSuccess() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Account> accounts = new ArrayList<>();
        accounts.add(getAccount());
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setAccountList(accounts);
        customerResponse.setId(CUSTOMER_ID);
        when(customerService.getCustomerAccountDetails(CUSTOMER_ID)).thenReturn(customerResponse);

        MvcResult result = mockMvc.perform(get("/banking-api/v1/customers/1234/accounts"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = result.getResponse().getContentAsString();
        String expectedResponse = objectMapper.writeValueAsString(customerResponse);
        assertEquals(expectedResponse, actualResponseBody);
    }

    @Test
    void testGetCustomerAccountSystemFailure() throws Exception {
        when(customerService.getCustomerAccountDetails(123)).thenThrow(new RuntimeException());
        mockMvc.perform(get("/banking-api/v1/customers/123/accounts"))
                .andExpect(status().is5xxServerError())
                .andReturn();
    }

    @Test
    void testCreateNewTransactionInvalidAccountId() throws Exception {
        when(customerService.getCustomerAccountDetails(123)).thenThrow(new InvalidAccountException("Invalid account id"));
        mockMvc.perform(get("/banking-api/v1/customers/123/accounts"))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }
}
