package au.com.cashapp.onlinebank.handler;

import au.com.cashapp.onlinebank.entity.Account;
import au.com.cashapp.onlinebank.model.TransactionRequest;
import au.com.cashapp.onlinebank.model.TransactionResponse;

public interface TransactionHandler {

    TransactionResponse handleTransaction(TransactionRequest request, Account account);

    boolean isRightHandlerForTransaction(String transactionType);
}
