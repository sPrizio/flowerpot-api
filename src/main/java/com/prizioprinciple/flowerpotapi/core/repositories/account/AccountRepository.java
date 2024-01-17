package com.prizioprinciple.flowerpotapi.core.repositories.account;

import com.prizioprinciple.flowerpotapi.core.models.entities.account.Account;
import com.prizioprinciple.flowerpotapi.core.repositories.FlowerpotRepository;
import org.springframework.stereotype.Repository;

/**
 * Data-access layer for {@link Account} entities
 *
 * @author Stephen Prizio
 * @version 0.0.1
 */
@Repository
public interface AccountRepository extends FlowerpotRepository<Account> {

    /**
     * Obtains an {@link Account} for the given account number
     *
     * @param accountNumber account number
     * @return {@link Account}
     */
    Account findAccountByAccountNumber(final long accountNumber);
}
