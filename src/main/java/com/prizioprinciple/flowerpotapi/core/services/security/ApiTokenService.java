package com.prizioprinciple.flowerpotapi.core.services.security;

import com.prizioprinciple.flowerpotapi.core.constants.CoreConstants;
import com.prizioprinciple.flowerpotapi.core.exceptions.security.InvalidApiTokenException;
import com.prizioprinciple.flowerpotapi.core.models.entities.security.User;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import static com.prizioprinciple.flowerpotapi.core.validation.GenericValidator.validateParameterIsNotNull;

/**
 * Service that handles generating and validating api tokens for incoming API requests
 *
 * @author Stephen Prizio
 * @version 0.0.2
 */
@Service
public class ApiTokenService {

    private static final String DELIMITER = "&";

    @Resource(name = "userService")
    private UserService userService;


    //  METHODS

    /**
     * Generates a unique API token for the given {@link User}
     *
     * @param user {@link User}
     * @return unique API token
     */
    public String generateApiToken(final User user) {
        validateParameterIsNotNull(user, CoreConstants.Validation.Security.User.USER_CANNOT_BE_NULL);
        validateParameterIsNotNull(user.getDateRegistered(), CoreConstants.Validation.Security.User.USER_DATE_REGISTERED_CANNOT_BE_NULL);
        return Base64.getEncoder().encodeToString((user.getEmail() + DELIMITER + user.getDateRegistered().format(DateTimeFormatter.ofPattern(CoreConstants.DATE_TIME_FORMAT))).getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Using the given API token, obtains the associated {@link User}
     *
     * @param apiToken API token
     * @return {@link User}
     * @throws InvalidApiTokenException throws an exception when the api token does not conform to expectations
     */
    public User getUserForApiToken(final String apiToken) {
        validateParameterIsNotNull(apiToken, CoreConstants.Validation.Security.API_TOKEN_CANNOT_BE_NULL);

        final String[] decrypted = new String(Base64.getDecoder().decode(apiToken)).split(DELIMITER);
        if (decrypted.length < 2) {
            throw new InvalidApiTokenException("The given api token did not map to anything");
        }

        final String email = decrypted[0];
        final String registrationDate = decrypted[1];
        final User possibleUser = this.userService.findUserByEmail(email).orElseThrow(() -> new InvalidApiTokenException("The given api token did not map to a known user"));

        if (!possibleUser.getDateRegistered().isEqual(LocalDateTime.parse(registrationDate, DateTimeFormatter.ofPattern(CoreConstants.DATE_TIME_FORMAT)))) {
            throw new InvalidApiTokenException("The given api token was not genuine");
        }

        return possibleUser;
    }
}
