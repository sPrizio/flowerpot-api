package com.prizioprinciple.flowerpotapi.api.controllers.advice;

import com.prizioprinciple.flowerpotapi.api.constants.ApiConstants;
import com.prizioprinciple.flowerpotapi.api.exceptions.InvalidEnumException;
import com.prizioprinciple.flowerpotapi.api.models.records.json.StandardJsonResponse;
import com.prizioprinciple.flowerpotapi.core.exceptions.system.EntityCreationException;
import com.prizioprinciple.flowerpotapi.core.exceptions.system.EntityModificationException;
import com.prizioprinciple.flowerpotapi.core.exceptions.system.GenericSystemException;
import com.prizioprinciple.flowerpotapi.core.exceptions.validation.*;
import com.prizioprinciple.flowerpotapi.importing.exceptions.FileExtensionNotSupportedException;
import org.hibernate.boot.beanvalidation.IntegrationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileNotFoundException;
import java.sql.SQLSyntaxErrorException;
import java.time.DateTimeException;

/**
 * Handles the exceptions thrown by the application
 *
 * @author Stephen Prizio
 * @version 0.0.1
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    //  METHODS

    @ResponseBody
    @ExceptionHandler(value = {
            DateTimeException.class,
            FileExtensionNotSupportedException.class,
            IllegalParameterException.class,
            InvalidEnumException.class,
            JsonMissingPropertyException.class,
            MissingRequiredDataException.class,
            NonUniqueItemFoundException.class,
            NoResultFoundException.class,
            UnsupportedOperationException.class
    })
    public StandardJsonResponse handleClientError(final Exception exception) {
        LOGGER.error("Bad Request by the client. Please try again: ", exception);
        return generateResponse(ApiConstants.CLIENT_ERROR_DEFAULT_MESSAGE, exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler({
            EntityCreationException.class,
            EntityModificationException.class,
            FileNotFoundException.class,
            GenericSystemException.class,
            IllegalArgumentException.class,
            IntegrationException.class,
            SQLSyntaxErrorException.class
    })
    public StandardJsonResponse handleServerError(final Exception exception) {
        LOGGER.error("An internal server error occurred. ", exception);
        return generateResponse(ApiConstants.SERVER_ERROR_DEFAULT_MESSAGE, exception.getMessage());
    }


    //  HELPERS

    /**
     * Generates a {@link StandardJsonResponse}
     */
    private StandardJsonResponse generateResponse(final String message, final String internalMessage) {
        return new StandardJsonResponse(false, null, message, internalMessage);
    }
}
