package ch.tschachtli.accounting.data.account;

import ch.tschachtli.accounting.domain.account.Account;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AccountStore {

    public Map<String, Account> getAccountData() {
        return new HashMap<>();
    }
}
