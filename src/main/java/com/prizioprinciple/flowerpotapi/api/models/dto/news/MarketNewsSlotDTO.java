package com.prizioprinciple.flowerpotapi.api.models.dto.news;

import com.prizioprinciple.flowerpotapi.api.models.dto.GenericDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

/**
 * A DTO representation of a {@link MarketNewsSlotDTO}
 *
 * @author Stephen Prizio
 * @version 0.0.4
 */
@Getter
@Setter
public class MarketNewsSlotDTO implements GenericDTO, Comparable<MarketNewsSlotDTO> {

    private String uid;

    private LocalTime time;

    private List<MarketNewsEntryDTO> entries;

    private boolean active;


    //  METHODS

    @Override
    public int compareTo(MarketNewsSlotDTO o) {
        return this.time.compareTo(o.time);
    }
}
