package au.com.cashapp.onlinebank.handler;

import au.com.cashapp.onlinebank.entity.Account;
import au.com.cashapp.onlinebank.model.TransactionRequest;
import au.com.cashapp.onlinebank.model.TransactionResponse;
import au.com.cashapp.onlinebank.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static au.com.cashapp.onlinebank.mapper.AccountMapper.mapAccountEntity;
import static au.com.cashapp.onlinebank.model.TransactionType.CREDIT;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreditTransactionHandler implements TransactionHandler {

    @Autowired
    protected AccountRepository accountRepository;

    @Override
    public boolean isRightHandlerForTransaction(String transactionType) {
        log.info("Checking isRightHandlerFor credit transaction {}", transactionType);
        return CREDIT.getOperation().equals(transactionType);
    }

    @Override
    public TransactionResponse handleTransaction(TransactionRequest transactionRequest, Account account) {
        log.info("Handling credit transaction request for account {}", account.getId());

        BigDecimal currentAccountBalance = account.getBalance();

        //Add the new amount to existing balance for credit operation. CurrentBalance initialized to have non null value
        BigDecimal newBalance = currentAccountBalance.add(transactionRequest.getAmount());

        // TO-Do Saving transaction separately and returning in the response.
        // Making the call transactional
        //Mapping can be improvised with frameworks like mapstruct.
        mapAccountEntity(transactionRequest, account, newBalance);
        accountRepository.save(account);

        log.info("Successfully handled credit transaction for account:  {} with new balance: {}", account.getId(), newBalance);

        TransactionResponse response = new TransactionResponse();
        response.setBalance(newBalance);
        return response;
    }
}
