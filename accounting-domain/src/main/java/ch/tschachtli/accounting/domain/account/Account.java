package ch.tschachtli.accounting.domain.account;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Account {
    private String id;
    private List<Transaction> transactionLog;
}
