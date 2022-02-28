package au.com.cashapp.onlinebank.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequest {

    private String transactionType;
    private BigDecimal amount;
}
