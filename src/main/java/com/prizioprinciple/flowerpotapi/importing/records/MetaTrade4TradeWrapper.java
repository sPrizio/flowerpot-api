package com.prizioprinciple.flowerpotapi.importing.records;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * A wrapper class for MetaTrader4 trades
 *
 * @author Stephen Prizio
 * @version 0.0.1
 */
public record MetaTrade4TradeWrapper(
        @Getter String ticketNumber,
        @Getter LocalDateTime openTime,
        @Getter LocalDateTime closeTime,
        @Getter String type,
        @Getter double size,
        @Getter String item,
        @Getter double openPrice,
        @Getter double stopLoss,
        @Getter double takeProfit,
        @Getter double closePrice,
        @Getter double profit
) {
}
