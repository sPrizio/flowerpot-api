package com.prizioprinciple.flowerpotapi.api.converters.account;

import com.prizioprinciple.flowerpotapi.api.converters.GenericDTOConverter;
import com.prizioprinciple.flowerpotapi.api.models.dto.account.AccountDTO;
import com.prizioprinciple.flowerpotapi.api.models.records.currency.CurrencyDisplay;
import com.prizioprinciple.flowerpotapi.core.models.entities.account.Account;
import com.prizioprinciple.flowerpotapi.core.services.math.MathService;
import com.prizioprinciple.flowerpotapi.core.services.platform.UniqueIdentifierService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * Converts {@link Account}s into {@link AccountDTO}s
 *
 * @author Stephen Prizio
 * @version 0.0.1
 */
@Component("accountDTOConverter")
public class AccountDTOConverter implements GenericDTOConverter<Account, AccountDTO> {

    @Resource(name = "mathService")
    private MathService mathService;

    @Resource(name = "uniqueIdentifierService")
    private UniqueIdentifierService uniqueIdentifierService;


    //  METHODS

    @Override
    public AccountDTO convert(final Account entity) {

        if (entity == null) {
            return new AccountDTO();
        }

        AccountDTO accountDTO = new AccountDTO();

        accountDTO.setUid(this.uniqueIdentifierService.generateUid(entity));
        accountDTO.setDefaultAccount(entity.isDefaultAccount());
        accountDTO.setAccountOpenTime(entity.getAccountOpenTime());
        accountDTO.setAccountCloseTime(entity.getAccountCloseTime());
        accountDTO.setActive(entity.isActive());
        accountDTO.setBalance(this.mathService.getDouble(entity.getBalance()));
        accountDTO.setName(entity.getName());
        accountDTO.setCurrency(new CurrencyDisplay(entity.getCurrency().getIsoCode(), entity.getCurrency().getLabel()));
        accountDTO.setAccountNumber(entity.getAccountNumber());
        accountDTO.setAccountType(entity.getAccountType().getLabel());
        accountDTO.setBroker(entity.getBroker().getName());
        accountDTO.setLastTraded(entity.getLastTraded());

        return accountDTO;
    }
}
