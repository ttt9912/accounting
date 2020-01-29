package ch.tschachtli.accounting.business.debit;

import ch.tschachtli.accounting.domain.account.Transaction;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.UUID;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class DebitTransaction extends Transaction {
    private final String id;
    private final Double amount;

    @Override
    public Double apply(final Double argument) {
        return argument - amount;
    }

    public static DebitTransaction from(final Double amount) {
        if (amount < 0) {
            throw new RuntimeException("Amount must be greater 0");
        }
        return new DebitTransaction(UUID.randomUUID().toString(), amount);
    }
}
