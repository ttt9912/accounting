package ch.tschachtli.accounting.domain.account;

public abstract class Transaction {
    public abstract String getId();
    public abstract Double apply(Double argument);
}
