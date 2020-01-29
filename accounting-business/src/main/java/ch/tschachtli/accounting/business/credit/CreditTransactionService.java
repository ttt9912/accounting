package ch.tschachtli.accounting.business.credit;

import ch.tschachtli.accounting.business.account.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class CreditTransactionService {
    private final AccountRepository accountRepository;

    public CreditTransactionService(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public synchronized void creditAccount(final String accountId, final CreditTransaction transaction) {
        accountRepository.addTransaction(accountId, transaction);
    }
}
