package au.com.cashapp.onlinebank.mapper;

import au.com.cashapp.onlinebank.entity.Account;
import au.com.cashapp.onlinebank.entity.Transaction;
import au.com.cashapp.onlinebank.model.TransactionRequest;

import java.math.BigDecimal;

public class AccountMapper {

    public static void mapAccountEntity(TransactionRequest transactionRequest, Account account, BigDecimal newBalance) {
        //TO-DO Can use frameworks like mapstruct for mapping.
        account.setBalance(newBalance);
        Transaction transaction = mapTransactionEntity(transactionRequest, newBalance);
        account.getTransactions().add(transaction);
    }

    private static Transaction mapTransactionEntity(TransactionRequest transactionRequest, BigDecimal newBalance) {
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionRequest.getAmount());
        //transaction.setTransactionType(transactionRequest.getTransactionType());
        transaction.setRemainingBalance(newBalance);
        return transaction;

    }
}
