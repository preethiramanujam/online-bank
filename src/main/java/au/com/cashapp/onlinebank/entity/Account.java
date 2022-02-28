package au.com.cashapp.onlinebank.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
public class Account {

    @Id
    @GeneratedValue
    private long id;
    private long customerId;
    private String status;
    private BigDecimal balance;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Transaction> transactions;
}
