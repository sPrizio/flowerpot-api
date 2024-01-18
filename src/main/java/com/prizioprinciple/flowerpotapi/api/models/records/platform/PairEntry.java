package com.prizioprinciple.flowerpotapi.api.models.records.platform;

import lombok.Getter;

/**
 * A record that represents a pair of values with option symbol
 *
 * @param code code
 * @param label display value
 * @param symbol display symbol
 * @author Stephen Prizio
 * @version 0.0.1
 */
public record PairEntry(Object code, Object label, String symbol) {}
