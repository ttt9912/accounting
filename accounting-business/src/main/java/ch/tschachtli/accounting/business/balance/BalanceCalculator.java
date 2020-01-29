package ch.tschachtli.accounting.business.balance;

import ch.tschachtli.accounting.domain.account.Transaction;

import java.util.List;

public final class BalanceCalculator {
    private BalanceCalculator() {
    }

    public static Double calculateBalance(final List<Transaction> transactionLog) {
        return transactionLog.stream()
                .reduce(0.0,
                        (a, b) -> b.apply(a),
                        (a, b) -> b);
    }
}
