package au.com.cashapp.onlinebank.unit;

import au.com.cashapp.onlinebank.entity.Account;
import au.com.cashapp.onlinebank.model.TransactionRequest;
import au.com.cashapp.onlinebank.model.TransactionResponse;

import java.math.BigDecimal;

public class TestFixtureUtil {

    public static final int VALID_CUSTOMER_ID = 1234;
    public static final Long VALID_ACCT_ID = 123L;
    public static final String DEPOSIT = "DEPOSIT";
    public static final String WITHDRAW = "WITHDRAW";

    public static Account getAccount() {
        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(300));
        account.setId(VALID_ACCT_ID);
        account.setCustomerId(VALID_CUSTOMER_ID);
        return account;
    }

    public static TransactionRequest getTransactionRequest(String transactionType) {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(BigDecimal.TEN);
        transactionRequest.setTransactionType(transactionType);
        return transactionRequest;
    }

    public static TransactionResponse getTransactionResponse() {
        TransactionResponse response = new TransactionResponse();
        response.setBalance(BigDecimal.TEN);
        return response;
    }
}
