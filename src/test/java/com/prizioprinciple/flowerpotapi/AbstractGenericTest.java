package com.prizioprinciple.flowerpotapi;

import com.prizioprinciple.flowerpotapi.api.models.dto.account.AccountDTO;
import com.prizioprinciple.flowerpotapi.core.enums.account.AccountType;
import com.prizioprinciple.flowerpotapi.core.enums.account.Broker;
import com.prizioprinciple.flowerpotapi.core.enums.account.Currency;
import com.prizioprinciple.flowerpotapi.core.enums.security.UserRole;
import com.prizioprinciple.flowerpotapi.core.enums.system.PhoneType;
import com.prizioprinciple.flowerpotapi.core.enums.trade.info.TradeType;
import com.prizioprinciple.flowerpotapi.core.enums.trade.platform.TradePlatform;
import com.prizioprinciple.flowerpotapi.core.models.entities.account.Account;
import com.prizioprinciple.flowerpotapi.core.models.entities.security.User;
import com.prizioprinciple.flowerpotapi.core.models.entities.system.PhoneNumber;
import com.prizioprinciple.flowerpotapi.core.models.entities.trade.Trade;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Parent-level testing class to provide testing assistance
 *
 * @author Stephen Prizio
 * @version 0.0.3
 */
public abstract class AbstractGenericTest {

    /**
     * Generates a test BUY {@link Trade}
     *
     * @return {@link Trade}
     */
    public Trade generateTestBuyTrade() {

        Trade trade = new Trade();

        trade.setTradeId("testId1");
        trade.setTradePlatform(TradePlatform.CMC_MARKETS);
        trade.setTradeType(TradeType.BUY);
        trade.setClosePrice(13098.67);
        trade.setTradeCloseTime(LocalDateTime.of(2022, 8, 24, 11, 37, 24));
        trade.setTradeOpenTime(LocalDateTime.of(2022, 8, 24, 11, 32, 58));
        trade.setLotSize(0.75);
        trade.setNetProfit(14.85);
        trade.setOpenPrice(13083.41);
        trade.setAccount(generateTestAccount());

        return trade;
    }

    /**
     * Generates a test SELL {@link Trade}
     *
     * @return {@link Trade}
     */
    public Trade generateTestSellTrade() {

        Trade trade = new Trade();

        trade.setTradeId("testId2");
        trade.setTradePlatform(TradePlatform.CMC_MARKETS);
        trade.setTradeType(TradeType.SELL);
        trade.setClosePrice(13156.12);
        trade.setTradeCloseTime(LocalDateTime.of(2022, 8, 24, 10, 24, 36));
        trade.setTradeOpenTime(LocalDateTime.of(2022, 8, 24, 10, 25, 12));
        trade.setLotSize(0.75);
        trade.setNetProfit(-4.50);
        trade.setOpenPrice(13160.09);
        trade.setAccount(generateTestAccount());

        return trade;
    }

    /**
     * Generates a test {@link Account}
     *
     * @return {@link Account}
     */
    public Account generateTestAccount() {

        Account account = new Account();

        account.setId(-1L);
        account.setDefaultAccount(true);
        account.setAccountOpenTime(LocalDateTime.of(2022, 10, 25, 22, 48, 0));
        account.setBalance(1000.0);
        account.setActive(true);
        account.setAccountType(AccountType.CFD);
        account.setAccountNumber(1234);
        account.setName("Test Account");
        account.setCurrency(Currency.CANADIAN_DOLLAR);
        account.setBroker(Broker.CMC_MARKETS);
        account.setTradePlatform(TradePlatform.CMC_MARKETS);

        return account;
    }

    /**
     * Generates a test {@link AccountDTO}
     *
     * @return {@link AccountDTO}
     */
    public AccountDTO generateTestAccountDTO() {

        AccountDTO accountDTO = new AccountDTO();

        accountDTO.setAccountOpenTime(LocalDateTime.of(2022, 10, 25, 22, 48, 0));
        accountDTO.setBalance(1000.0);
        accountDTO.setActive(true);

        return accountDTO;
    }

    /**
     * Generates a test {@link User}
     *
     * @return {@link User}
     */
    public User generateTestUser() {
        User user = new User();

        user.setAccounts(List.of(generateTestAccount()));
        user.setEmail("test@email.com");
        user.setUsername("s.prizio");
        user.setPassword("1234");
        user.setFirstName("Stephen");
        user.setLastName("Test");
        user.setPhones(List.of(generateTestPhoneNumber()));
        user.setRoles(List.of(UserRole.ADMINISTRATOR, UserRole.TRADER));

        return user;
    }

    /**
     * Generates a test {@link PhoneNumber}
     *
     * @return {@link PhoneNumber}
     */
    public PhoneNumber generateTestPhoneNumber() {

        final PhoneNumber phoneNumber = new PhoneNumber();

        phoneNumber.setPhoneType(PhoneType.MOBILE);
        phoneNumber.setTelephoneNumber(1112223333);
        phoneNumber.setCountryCode((short) 1);

        return phoneNumber;
    }
}
