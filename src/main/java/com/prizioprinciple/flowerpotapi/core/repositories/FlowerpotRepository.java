package com.prizioprinciple.flowerpotapi.core.repositories;

import com.prizioprinciple.flowerpotapi.core.models.entities.GenericEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Parent-level repository for all repositories in the system
 *
 * @author Stephen Prizio
 * @version 0.0.1
 */
public interface FlowerpotRepository<E extends GenericEntity> extends PagingAndSortingRepository<E, Long>, CrudRepository<E, Long> {
}
