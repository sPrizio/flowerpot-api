package com.prizioprinciple.flowerpotapi.core.models.entities.account;

import com.prizioprinciple.flowerpotapi.core.enums.account.AccountType;
import com.prizioprinciple.flowerpotapi.core.enums.account.Broker;
import com.prizioprinciple.flowerpotapi.core.enums.account.Currency;
import com.prizioprinciple.flowerpotapi.core.enums.trade.platform.TradePlatform;
import com.prizioprinciple.flowerpotapi.core.models.entities.GenericEntity;
import com.prizioprinciple.flowerpotapi.core.models.entities.security.User;
import com.prizioprinciple.flowerpotapi.core.models.entities.trade.Trade;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Class representation of a trading account, an entity that can hold {@link Trade}s and other information
 *
 * @author Stephen Prizio
 * @version 0.0.1
 */
@Getter
@Setter
@Entity
@Table(name = "accounts")
public class Account implements GenericEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private boolean defaultAccount;

    @Column
    private LocalDateTime accountOpenTime;

    @Column
    private LocalDateTime accountCloseTime;

    @Column
    private double balance;

    @Column
    private boolean active;

    @Column
    private String name;

    @Column
    private long accountNumber;

    @Column
    private Currency currency;

    @Column
    private Broker broker;

    @Column
    private AccountType accountType;

    @Column
    private TradePlatform tradePlatform;

    @Column
    private LocalDateTime lastTraded;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Trade> trades;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;
}
