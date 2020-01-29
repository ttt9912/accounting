package ch.tschachtli.accounting.business.account;

import ch.tschachtli.accounting.domain.account.Account;
import ch.tschachtli.accounting.domain.account.Transaction;

public interface AccountRepository {
    Account findById(final String id);
    void save(final Account account);
    void addTransaction(final String accountId, final Transaction transaction);
}
