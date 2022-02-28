package au.com.cashapp.onlinebank.service;

import au.com.cashapp.onlinebank.entity.Account;
import au.com.cashapp.onlinebank.exception.InvalidAccountException;
import au.com.cashapp.onlinebank.model.CustomerResponse;
import au.com.cashapp.onlinebank.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {

    @Autowired
    private AccountRepository accountRepository;

    public CustomerResponse getCustomerAccountDetails(long id) {
        List<Account> accounts = accountRepository.findByCustomerId(id)
                .orElseThrow(() -> new InvalidAccountException("Could not find account for provided customer id"));
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setId(id);
        customerResponse.setAccountList(accounts);
        return customerResponse;
    }
}
