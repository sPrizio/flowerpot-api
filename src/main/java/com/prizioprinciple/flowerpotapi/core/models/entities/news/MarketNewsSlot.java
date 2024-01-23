package com.prizioprinciple.flowerpotapi.core.models.entities.news;

import com.prizioprinciple.flowerpotapi.core.models.entities.GenericEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representation of a market news entry which represents a time of day that can have 1 or more pieces of news
 *
 * @author Stephen Prizio
 * @version 0.0.4
 */
@Getter
@Entity
@Table(name = "market_news_slots")
public class MarketNewsSlot implements GenericEntity, Comparable<MarketNewsSlot> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column
    private LocalTime time;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private MarketNews news;

    @Setter
    @OneToMany(mappedBy = "slot", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("severity DESC")
    private List<MarketNewsEntry> entries;


    //  METHODS

    /**
     * Database assistance method
     *
     * @param entry {@link MarketNewsEntry}
     */
    public void addEntry(MarketNewsEntry entry) {

        if (getEntries() == null) {
            this.entries = new ArrayList<>();
        }

        getEntries().add(entry);
        entry.setSlot(this);
    }

    /**
     * Database assistance method
     *
     * @param entry {@link MarketNewsEntry}
     */
    public void removeEntry(MarketNewsEntry entry) {
        if (getEntries() != null) {
            List<MarketNewsEntry> e = new ArrayList<>(getEntries());
            e.remove(entry);
            this.entries = e;
            entry.setSlot(null);
        }
    }

    @Override
    public int compareTo(MarketNewsSlot o) {
        return this.time.compareTo(o.time);
    }
}
