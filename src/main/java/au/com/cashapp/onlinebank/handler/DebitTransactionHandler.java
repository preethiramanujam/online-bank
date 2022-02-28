package au.com.cashapp.onlinebank.handler;

import au.com.cashapp.onlinebank.entity.Account;
import au.com.cashapp.onlinebank.exception.InvalidTransactionException;
import au.com.cashapp.onlinebank.model.TransactionRequest;
import au.com.cashapp.onlinebank.model.TransactionResponse;
import au.com.cashapp.onlinebank.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static au.com.cashapp.onlinebank.mapper.AccountMapper.mapAccountEntity;
import static au.com.cashapp.onlinebank.model.TransactionType.DEBIT;

@Component
@RequiredArgsConstructor
@Slf4j
public class DebitTransactionHandler implements TransactionHandler {

    @Autowired
    protected AccountRepository accountRepository;

    @Override
    public boolean isRightHandlerForTransaction(String transactionType) {
        log.info("Checking isRightHandlerFor debit transaction {}", transactionType);
        return DEBIT.getOperation().equals(transactionType);
    }

    @Override
    public TransactionResponse handleTransaction(TransactionRequest transactionRequest, Account account) {
        log.info("Handling debit transaction request for account {}", account.getId());

        BigDecimal currentAccountBalance = account.getBalance();
        BigDecimal newBalance;

        //Check if amount can be withdrawn to prevent negative balance
        if (isValidTransactionRequest(currentAccountBalance, transactionRequest)) {
            newBalance = currentAccountBalance.subtract(transactionRequest.getAmount());
            mapAccountEntity(transactionRequest, account, newBalance);
            accountRepository.save(account);

            log.info("Successfully handled debit transaction for account: {} with new balance: {}", account.getId(), newBalance);
        } else {
            log.error("Invalid transaction for the amount provided");
            throw new InvalidTransactionException("Insufficient funds to proceed with transaction");
        }

        TransactionResponse response = new TransactionResponse();
        response.setBalance(newBalance);
        return response;
    }

    private boolean isValidTransactionRequest(BigDecimal balance, TransactionRequest transactionRequest) {
        return balance.subtract(transactionRequest.getAmount()).compareTo(BigDecimal.ZERO) > -1;
    }
}
