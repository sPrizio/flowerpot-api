package com.prizioprinciple.flowerpotapi.api.controllers;

import com.prizioprinciple.flowerpotapi.core.constants.CoreConstants;

import static com.prizioprinciple.flowerpotapi.core.validation.GenericValidator.validateLocalDateFormat;

/**
 * Parent-level controller providing common functionality
 *
 * @author Stephen Prizio
 * @version 0.0.4
 */
public abstract class AbstractApiController {

    /**
     * Basic data integrity validation
     *
     * @param date date of format yyyy-MM-dd
     */
    public void validate(final String date) {
        validateLocalDateFormat(date, CoreConstants.DATE_FORMAT, CoreConstants.Validation.DateTime.START_DATE_INVALID_FORMAT, date, CoreConstants.DATE_FORMAT);
    }

    /**
     * Basic data integrity validation
     *
     * @param start start date of format yyyy-MM-dd
     * @param end   end date of format yyyy-MM-dd
     */
    public void validate(final String start, final String end) {
        validateLocalDateFormat(start, CoreConstants.DATE_FORMAT, CoreConstants.Validation.DateTime.START_DATE_INVALID_FORMAT, start, CoreConstants.DATE_FORMAT);
        validateLocalDateFormat(end, CoreConstants.DATE_FORMAT, CoreConstants.Validation.DateTime.END_DATE_INVALID_FORMAT, end, CoreConstants.DATE_FORMAT);
    }
}
