package ch.tschachtli.accounting;

import ch.tschachtli.accounting.business.balance.BalanceService;
import ch.tschachtli.accounting.business.credit.CreditTransaction;
import ch.tschachtli.accounting.business.credit.CreditTransactionService;
import ch.tschachtli.accounting.business.debit.DebitTransaction;
import ch.tschachtli.accounting.business.debit.DebitTransactionService;
import ch.tschachtli.accounting.data.account.AccountStore;
import ch.tschachtli.accounting.domain.account.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class AccountingAppTest {
    public static final String ACCOUNT_ID = "1";

    @Autowired
    CreditTransactionService creditTransactionService;

    @Autowired
    DebitTransactionService debitTransactionService;

    @Autowired
    BalanceService balanceService;

    @MockBean
    AccountStore accountStore;

    @Test
    void executeTransactions() {
        when(accountStore.getAccountData()).thenReturn(createAccounts());

        creditTransactionService.creditAccount(ACCOUNT_ID, CreditTransaction.from(100.0));
        debitTransactionService.debitAccount(ACCOUNT_ID, DebitTransaction.from(50.0));

        assertThat(balanceService.calculateBalance(ACCOUNT_ID), equalTo(50.0));
    }

    @Test
    void executeTransactions_failOnSameCreditTransaction() {
        when(accountStore.getAccountData()).thenReturn(createAccounts());

        final CreditTransaction transaction = CreditTransaction.from(100.0);

        creditTransactionService.creditAccount(ACCOUNT_ID, transaction);

        assertThrows(RuntimeException.class,
                () -> creditTransactionService.creditAccount(ACCOUNT_ID, transaction));
    }

    @Test
    void executeTransactions_failOnSameDebitTransaction() {
        when(accountStore.getAccountData()).thenReturn(createAccounts());

        creditTransactionService.creditAccount(ACCOUNT_ID, CreditTransaction.from(100.0));

        final DebitTransaction transaction = DebitTransaction.from(10.0);

        debitTransactionService.debitAccount(ACCOUNT_ID, transaction);

        assertThrows(RuntimeException.class,
                () -> debitTransactionService.debitAccount(ACCOUNT_ID, transaction));
    }

    @Test
    void executeTransactions_failOnDebitTransactionCausingNegativeBalance() {
        when(accountStore.getAccountData()).thenReturn(createAccounts());

        assertThrows(RuntimeException.class,
                () -> debitTransactionService.debitAccount(ACCOUNT_ID, DebitTransaction.from(100.0)));
    }

    private Map<String, Account> createAccounts() {
        final Account account1 = createAccount();
        final Map<String, Account> accounts = new HashMap<>();
        accounts.put(account1.getId(), account1);
        return accounts;
    }

    private Account createAccount() {
        return new Account(ACCOUNT_ID, Collections.emptyList());
    }
}