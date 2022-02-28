package au.com.cashapp.onlinebank.unit.controller;

import au.com.cashapp.onlinebank.controller.TransactionController;
import au.com.cashapp.onlinebank.exception.InvalidAccountException;
import au.com.cashapp.onlinebank.exception.InvalidTransactionException;
import au.com.cashapp.onlinebank.model.TransactionRequest;
import au.com.cashapp.onlinebank.model.TransactionResponse;
import au.com.cashapp.onlinebank.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static au.com.cashapp.onlinebank.unit.TestFixtureUtil.getTransactionRequest;
import static au.com.cashapp.onlinebank.unit.TestFixtureUtil.getTransactionResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransactionControllerTest {

    public static final int ACCOUNT_ID = 123;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testCreateNewCreditTransactionSuccess() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        TransactionRequest transactionRequest = getTransactionRequest("Deposit");
        TransactionResponse transactionResponse = getTransactionResponse();
        when(transactionService.createNewTransaction(ACCOUNT_ID, transactionRequest)).thenReturn(transactionResponse);

        MvcResult result = mockMvc.perform(post("/v1/accounts/123/transaction")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(transactionRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = result.getResponse().getContentAsString();
        String expectedResponse = objectMapper.writeValueAsString(transactionResponse);
        assertEquals(expectedResponse, actualResponseBody);
    }

    @Test
    void testCreateNewDebitTransactionSuccess() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        TransactionRequest transactionRequest = getTransactionRequest("WithDraw");
        TransactionResponse transactionResponse = getTransactionResponse();
        when(transactionService.createNewTransaction(ACCOUNT_ID, transactionRequest)).thenReturn(transactionResponse);

        MvcResult result = mockMvc.perform(post("/v1/accounts/123/transaction")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(transactionRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = result.getResponse().getContentAsString();
        String expectedResponse = objectMapper.writeValueAsString(transactionResponse);
        assertEquals(expectedResponse, actualResponseBody);
    }

    @Test
    void testCreateNewTransactionInvalidTransaction() throws Exception {
        TransactionRequest transactionRequest = getTransactionRequest("InvalidTransaction");
        when(transactionService.createNewTransaction(ACCOUNT_ID, transactionRequest))
                .thenThrow(new InvalidTransactionException("Invalid transaction"));
        mockMvc.perform(post("/v1/accounts/123/transaction")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(transactionRequest)))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    void testCreateNewTransactionInvalidAccountId() throws Exception {
        TransactionRequest transactionRequest = getTransactionRequest("InvalidAccount");
        when(transactionService.createNewTransaction(ACCOUNT_ID, transactionRequest))
                .thenThrow(new InvalidAccountException("Invalid account id"));
        mockMvc.perform(post("/v1/accounts/123/transaction")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(transactionRequest)))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    void testCreateNewTransactionSystemError() throws Exception {
        TransactionRequest transactionRequest = getTransactionRequest("InvalidAccount");
        when(transactionService.createNewTransaction(ACCOUNT_ID, transactionRequest))
                .thenThrow(new RuntimeException());
        mockMvc.perform(post("/v1/accounts/123/transaction")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(transactionRequest)))
                .andExpect(status().is5xxServerError())
                .andReturn();
    }
}
