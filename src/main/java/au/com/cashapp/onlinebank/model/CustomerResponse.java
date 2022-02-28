package au.com.cashapp.onlinebank.model;

import au.com.cashapp.onlinebank.entity.Account;
import lombok.Data;

import java.util.List;

@Data
public class CustomerResponse {

    private long id;
    private List<Account> accountList;

}
