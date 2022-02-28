package au.com.cashapp.onlinebank.entity;

import lombok.Data;

import java.util.List;

@Data
public class Customer {

    private long id;
    private String status;
    private List<Account> accounts;
}
