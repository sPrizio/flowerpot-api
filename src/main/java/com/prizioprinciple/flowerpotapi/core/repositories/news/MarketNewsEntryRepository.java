package com.prizioprinciple.flowerpotapi.core.repositories.news;

import com.prizioprinciple.flowerpotapi.core.models.entities.news.MarketNewsEntry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Data-access layer for {@link MarketNewsEntry}
 *
 * @author Stephen Prizio
 * @version 0.0.4
 */
@Repository
public interface MarketNewsEntryRepository extends PagingAndSortingRepository<MarketNewsEntry, Long>, CrudRepository<MarketNewsEntry, Long> {
}
