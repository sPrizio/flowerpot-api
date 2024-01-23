package com.prizioprinciple.flowerpotapi.integration.client;

import org.springframework.util.MultiValueMap;

/**
 * Defines how API clients should be interfacing with their external web services
 *
 * @author Stephen Prizio
 * @version 0.0.4
 */
public interface IntegrationClient {

    /**
     * Returns a JSON string for the given url and query params
     *
     * @param url         endpoint urls
     * @param queryParams query parameters
     * @return {@link String}
     */
    String get(final String url, final MultiValueMap<String, String> queryParams);
}
