package ch.tschachtli.accounting.business.balance;

import ch.tschachtli.accounting.business.account.AccountRepository;
import ch.tschachtli.accounting.domain.account.Account;
import org.springframework.stereotype.Service;

@Service
public class BalanceService {
    private final AccountRepository accountRepository;

    public BalanceService(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Double calculateBalance(final String accountId) {
        final Account account = accountRepository.findById(accountId);
        return BalanceCalculator.calculateBalance(account.getTransactionLog());
    }
}
