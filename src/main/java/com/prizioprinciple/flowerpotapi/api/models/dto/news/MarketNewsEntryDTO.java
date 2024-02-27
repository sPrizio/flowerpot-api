package com.prizioprinciple.flowerpotapi.api.models.dto.news;

import com.prizioprinciple.flowerpotapi.api.models.dto.GenericDTO;
import com.prizioprinciple.flowerpotapi.core.models.entities.news.MarketNewsEntry;
import lombok.Getter;
import lombok.Setter;

/**
 * A DTO representation of  {@link MarketNewsEntry}
 *
 * @author Stephen Prizio
 * @version 0.0.4
 */
@Getter
@Setter
public class MarketNewsEntryDTO implements GenericDTO, Comparable<MarketNewsEntryDTO> {

    private String uid;

    private String content;

    private String severity;

    private int severityLevel;

    private String country;

    private String forecast;

    private String previous;


    //  METHODS

    @Override
    public int compareTo(MarketNewsEntryDTO o) {
        return this.content.compareTo(o.content);
    }
}