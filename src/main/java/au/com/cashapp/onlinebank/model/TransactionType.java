package au.com.cashapp.onlinebank.model;

import lombok.Getter;

@Getter
public enum TransactionType {

    DEPOSIT("CREDIT"), WITHDRAW("DEBIT");

    private String type;

    TransactionType(String type) {
        this.type = type;
    }
}
