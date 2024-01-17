package com.prizioprinciple.flowerpotapi.core.services.security;

import com.prizioprinciple.flowerpotapi.core.constants.CoreConstants;
import com.prizioprinciple.flowerpotapi.core.enums.security.UserRole;
import com.prizioprinciple.flowerpotapi.core.exceptions.system.EntityCreationException;
import com.prizioprinciple.flowerpotapi.core.exceptions.system.EntityModificationException;
import com.prizioprinciple.flowerpotapi.core.exceptions.validation.MissingRequiredDataException;
import com.prizioprinciple.flowerpotapi.core.exceptions.validation.NoResultFoundException;
import com.prizioprinciple.flowerpotapi.core.models.entities.security.User;
import com.prizioprinciple.flowerpotapi.core.models.entities.system.PhoneNumber;
import com.prizioprinciple.flowerpotapi.core.repositories.security.UserRepository;
import com.prizioprinciple.flowerpotapi.core.services.system.PhoneNumberService;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.prizioprinciple.flowerpotapi.core.validation.GenericValidator.validateParameterIsNotNull;

/**
 * Service-layer for {@link User} entities
 *
 * @author Stephen Prizio
 * @version 0.0.1
 */
@Service
public class UserService {

    @Resource(name = "phoneNumberService")
    private PhoneNumberService phoneNumberService;

    @Resource(name = "userRepository")
    private UserRepository userRepository;


    //  METHODS

    /**
     * Finds a {@link User} by their username
     *
     * @param username username
     * @return {@link Optional} {@link User}
     */
    public Optional<User> findUserByUsername(final String username) {
        validateParameterIsNotNull(username, CoreConstants.Validation.Security.User.USERNAME_CANNOT_BE_NULL);
        return Optional.ofNullable(this.userRepository.findUserByUsername(username));
    }

    /**
     * Finds a {@link User} by their email
     *
     * @param email email
     * @return {@link Optional} {@link User}
     */
    public Optional<User> findUserByEmail(final String email) {
        validateParameterIsNotNull(email, CoreConstants.Validation.Security.User.EMAIL_CANNOT_BE_NULL);
        return Optional.ofNullable(this.userRepository.findUserByEmail(email));
    }

    /**
     * Creates a new {@link User} from the given {@link Map} of data
     *
     * @param data {@link Map}
     * @return newly created {@link User}
     */
    public User createUser(final Map<String, Object> data) {

        if (MapUtils.isEmpty(data)) {
            throw new MissingRequiredDataException("The required data for creating a User was null or empty");
        }

        try {
            return applyChanges(new User(), data);
        } catch (Exception e) {
            throw new EntityCreationException(String.format("A User could not be created : %s", e.getMessage()), e);
        }
    }

    /**
     * Updates an existing {@link User} with the given {@link Map} of data. Update methods are designed to be idempotent.
     *
     * @param email user email
     * @param data  {@link Map}
     * @return modified {@link User}
     */
    public User updateUser(final String email, final Map<String, Object> data) {

        validateParameterIsNotNull(email, "email cannot be null");

        if (MapUtils.isEmpty(data)) {
            throw new MissingRequiredDataException("The required data for updating a User was null or empty");
        }

        try {
            User user =
                    findUserByEmail(email)
                            .orElseThrow(() -> new NoResultFoundException(String.format("No User found for email %s", email)));

            return applyChanges(user, data);
        } catch (Exception e) {
            throw new EntityModificationException(String.format("An error occurred while modifying the User : %s", e.getMessage()), e);
        }
    }


    //  HELPERS

    /**
     * Applies changes to the given {@link User} with the given data
     *
     * @param user {@link User}
     * @param data {@link Map}
     * @return updated {@link User}
     */
    private User applyChanges(User user, final Map<String, Object> data) {

        Map<String, Object> ud = (Map<String, Object>) data.get("user");
        Set<PhoneNumber> phoneNumbers = (CollectionUtils.isEmpty(user.getPhones())) ? new HashSet<>() : new HashSet<>(user.getPhones());

        user.setEmail(ud.get("email").toString());
        user.setPassword(ud.get("password").toString());
        user.setLastName(ud.get("lastName").toString());
        user.setFirstName(ud.get("firstName").toString());
        user.setUsername(ud.get("username").toString());
        user.setRoles(List.of(UserRole.TRADER));

        user = this.userRepository.save(user);

        List<Map<String, Object>> phoneData = (List<Map<String, Object>>) ud.get("phoneNumbers");
        for (Map<String, Object> d : phoneData) {
            final PhoneNumber phoneNumber = this.phoneNumberService.createPhoneNumber(d, user);
            if (!phoneNumbers.contains(phoneNumber)) {
                phoneNumbers.add(phoneNumber);
            } else {
                this.phoneNumberService.deletePhoneNumber(phoneNumber);
            }
        }

        user.setPhones(new ArrayList<>(phoneNumbers));
        return this.userRepository.save(user);
    }
}
