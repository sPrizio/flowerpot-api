package com.prizioprinciple.flowerpotapi.core.repositories.news;

import com.prizioprinciple.flowerpotapi.core.models.entities.news.MarketNewsSlot;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Data-access layer for {@link MarketNewsSlot}
 *
 * @author Stephen Prizio
 * @version 0.0.4
 */
@Repository
public interface MarketNewsSlotRepository extends PagingAndSortingRepository<MarketNewsSlot, Long>, CrudRepository<MarketNewsSlot, Long> {
}
