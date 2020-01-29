package ch.tschachtli.accounting.domain.account;

import lombok.Value;

import java.util.List;

@Value
public class Account {
    private String id;
    private List<Transaction> transactionLog;
}
