package au.com.cashapp.onlinebank.model;

import lombok.Getter;

@Getter
public enum TransactionType {

    DEBIT("withdraw"), CREDIT("deposit");

    private String operation;

    TransactionType(String operation) {
        this.operation = operation;
    }
}
