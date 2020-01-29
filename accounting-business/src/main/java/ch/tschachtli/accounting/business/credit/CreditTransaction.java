package ch.tschachtli.accounting.business.credit;

import ch.tschachtli.accounting.domain.account.Transaction;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.UUID;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class CreditTransaction extends Transaction {
    private final String id;
    private final Double amount;

    @Override
    public Double apply(final Double argument) {
        return argument + amount;
    }

    public static CreditTransaction from(final Double amount) {
        if (amount < 0) {
            throw new RuntimeException("Amount must be greater 0");
        }
        return new CreditTransaction(UUID.randomUUID().toString(), amount);
    }
}
