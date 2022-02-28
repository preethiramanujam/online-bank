package au.com.cashapp.onlinebank.entity;

import au.com.cashapp.onlinebank.model.TransactionType;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity
public class Transaction {

    @Id
    @GeneratedValue
    private long id;
    private BigDecimal amount;
    private BigDecimal remainingBalance;
    private String transactionType;
}
