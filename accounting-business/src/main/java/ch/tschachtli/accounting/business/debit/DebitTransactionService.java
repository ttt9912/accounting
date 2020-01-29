package ch.tschachtli.accounting.business.debit;

import ch.tschachtli.accounting.business.account.AccountRepository;
import ch.tschachtli.accounting.business.balance.BalanceCalculator;
import ch.tschachtli.accounting.domain.account.Account;
import ch.tschachtli.accounting.domain.account.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DebitTransactionService {
    private final AccountRepository accountRepository;

    public DebitTransactionService(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public synchronized void debitAccount(final String accountId, final DebitTransaction transaction) {
        final Account existing = accountRepository.findById(accountId);

        if (causesNegativeBalance(existing.getTransactionLog(), transaction)) {
            throw new RuntimeException("Transaction causes negative balance");
        }

        accountRepository.addTransaction(accountId, transaction);
    }

    private boolean causesNegativeBalance(final List<Transaction> transactionLog, final DebitTransaction transaction) {
        final Double currentBalance = BalanceCalculator.calculateBalance(transactionLog);
        return transaction.apply(currentBalance) <= 0;
    }
}
