package com.prizioprinciple.flowerpotapi.core.repositories.security;

import com.prizioprinciple.flowerpotapi.core.models.entities.security.User;
import com.prizioprinciple.flowerpotapi.core.repositories.FlowerpotRepository;
import org.springframework.stereotype.Repository;

/**
 * Data-access layer for {@link User} entities
 *
 * @author Stephen Prizio
 * @version 0.0.1
 */
@Repository
public interface UserRepository extends FlowerpotRepository<User> {

    /**
     * Attempts to look up a {@link User} by their username
     *
     * @param username username
     * @return {@link User}, can return null
     */
    User findUserByUsername(final String username);

    /**
     * Attempts to look up a {@link User} by their email
     *
     * @param email email
     * @return {@link User}, can return null
     */
    User findUserByEmail(final String email);
}
