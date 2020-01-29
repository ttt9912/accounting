package ch.tschachtli.accounting.business.balance;

import ch.tschachtli.accounting.domain.account.Transaction;
import ch.tschachtli.accounting.business.credit.CreditTransaction;
import ch.tschachtli.accounting.business.debit.DebitTransaction;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class BalanceCalculatorTest {


    @Test
    void calculateBalance() {
        final Double balance = BalanceCalculator.calculateBalance(createTransactionLog());

        assertThat(balance, is(not(nullValue())));
        assertThat(balance, equalTo(70.0));
    }

    private static List<Transaction> createTransactionLog() {
        return List.of(
                CreditTransaction.from(100.0),
                CreditTransaction.from(20.0),
                DebitTransaction.from(50.0));
    }
}