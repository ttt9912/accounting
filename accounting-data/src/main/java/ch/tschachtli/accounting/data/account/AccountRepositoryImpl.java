package ch.tschachtli.accounting.data.account;

import ch.tschachtli.accounting.business.account.AccountRepository;
import ch.tschachtli.accounting.domain.account.Account;
import ch.tschachtli.accounting.domain.account.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class AccountRepositoryImpl implements AccountRepository {
    private final AccountStore accountStore;

    public AccountRepositoryImpl(final AccountStore accountStore) {
        this.accountStore = accountStore;
    }

    @Override
    public Account findById(final String id) {
        if (!accountStore.getAccountData().containsKey(id)) {
            throw new RuntimeException(String.format("Account with id=%s does not exist", id));
        }
        return accountStore.getAccountData().get(id);
    }

    @Override
    public void save(final Account account) {
        accountStore.getAccountData().put(account.getId(), account);
    }

    @Override
    public void addTransaction(final String accountId, final Transaction transaction) {
        final Account existing = findById(accountId);

        if (containsTransactionId(existing.getTransactionLog(), transaction.getId())) {
            throw new RuntimeException("Transaction has already been applied");
        }

        save(new Account(existing.getId(), concatTransactions(transaction, existing)));
    }

    private boolean containsTransactionId(final List<Transaction> transactionLog, final String id) {
        return transactionLog.stream()
                .map(Transaction::getId)
                .anyMatch(existingId -> existingId.equals(id));
    }

    private List<Transaction> concatTransactions(final Transaction transaction, final Account existing) {
        return Stream.concat(existing.getTransactionLog().stream(), Stream.of(transaction))
                .collect(Collectors.toList());
    }
}
