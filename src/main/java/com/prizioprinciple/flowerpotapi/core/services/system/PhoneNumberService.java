package com.prizioprinciple.flowerpotapi.core.services.system;

import com.prizioprinciple.flowerpotapi.core.constants.CoreConstants;
import com.prizioprinciple.flowerpotapi.core.enums.system.PhoneType;
import com.prizioprinciple.flowerpotapi.core.exceptions.system.EntityCreationException;
import com.prizioprinciple.flowerpotapi.core.exceptions.system.EntityModificationException;
import com.prizioprinciple.flowerpotapi.core.exceptions.validation.MissingRequiredDataException;
import com.prizioprinciple.flowerpotapi.core.exceptions.validation.NoResultFoundException;
import com.prizioprinciple.flowerpotapi.core.models.entities.security.User;
import com.prizioprinciple.flowerpotapi.core.models.entities.system.PhoneNumber;
import com.prizioprinciple.flowerpotapi.core.repositories.system.PhoneNumberRepository;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

import static com.prizioprinciple.flowerpotapi.core.validation.GenericValidator.validateNonNegativeValue;
import static com.prizioprinciple.flowerpotapi.core.validation.GenericValidator.validateParameterIsNotNull;

/**
 * Service-layer for {@link PhoneNumber} entities
 *
 * @author Stephen Prizio
 * @version 0.0.1
 */
@Service
public class PhoneNumberService {

    @Resource(name = "phoneNumberRepository")
    private PhoneNumberRepository phoneNumberRepository;


    //  METHODS

    /**
     * Obtains a {@link PhoneNumber} for the given {@link PhoneType}, country code and telephone number
     *
     * @param phoneType       {@link PhoneType}
     * @param countryCode     country code, ex: 1 for USA/Canada
     * @param telephoneNumber actual phone number
     * @return {@link Optional} {@link PhoneNumber}
     */
    public Optional<PhoneNumber> findPhoneNumberForPhoneTypeAndCountryCodeAndTelephoneNumber(final PhoneType phoneType, final short countryCode, final long telephoneNumber) {

        validateParameterIsNotNull(phoneType, CoreConstants.Validation.System.PhoneNumber.PHONE_TYPE_CANNOT_BE_NULL);
        validateNonNegativeValue(countryCode, CoreConstants.Validation.System.PhoneNumber.COUNTRY_CODE_CANNOT_BE_NEGATIVE);
        validateNonNegativeValue(telephoneNumber, CoreConstants.Validation.System.PhoneNumber.TELEPHONE_NUMBER_CANNOT_BE_NEGATIVE);

        return Optional.ofNullable(this.phoneNumberRepository.findPhoneNumberByPhoneTypeAndCountryCodeAndTelephoneNumber(phoneType, countryCode, telephoneNumber));
    }

    /**
     * Creates a new {@link PhoneNumber} from the given {@link Map} of data
     *
     * @param data {@link Map}
     * @param user {@link User}
     * @return newly created {@link PhoneNumber}
     */
    public PhoneNumber createPhoneNumber(final Map<String, Object> data, final User user) {

        validateParameterIsNotNull(user, CoreConstants.Validation.Security.User.USER_CANNOT_BE_NULL);

        if (MapUtils.isEmpty(data)) {
            throw new MissingRequiredDataException("The required data for creating a PhoneNumber was null or empty");
        }

        try {
            return applyChanges(new PhoneNumber(), data, user);
        } catch (Exception e) {
            throw new EntityCreationException(String.format("A PhoneNumber could not be created : %s", e.getMessage()), e);
        }
    }

    /**
     * Updates an existing {@link PhoneNumber} with the given {@link Map} of data. Update methods are designed to be idempotent.
     *
     * @param phoneType       {@link PhoneType}
     * @param countryCode     country code, ex: 1 for USA/Canada
     * @param telephoneNumber actual phone number
     * @param data            {@link Map}
     * @param user            {@link User}
     * @return modified {@link PhoneNumber}
     */
    public PhoneNumber updatePhoneNumber(final PhoneType phoneType, final short countryCode, final long telephoneNumber, final Map<String, Object> data, final User user) {

        validateParameterIsNotNull(phoneType, CoreConstants.Validation.System.PhoneNumber.PHONE_TYPE_CANNOT_BE_NULL);
        validateNonNegativeValue(countryCode, CoreConstants.Validation.System.PhoneNumber.COUNTRY_CODE_CANNOT_BE_NEGATIVE);
        validateNonNegativeValue(telephoneNumber, CoreConstants.Validation.System.PhoneNumber.TELEPHONE_NUMBER_CANNOT_BE_NEGATIVE);
        validateParameterIsNotNull(user, CoreConstants.Validation.Security.User.USER_CANNOT_BE_NULL);

        if (MapUtils.isEmpty(data)) {
            throw new MissingRequiredDataException("The required data for updating a PhoneNumber was null or empty");
        }

        try {
            PhoneNumber phoneNumber =
                    findPhoneNumberForPhoneTypeAndCountryCodeAndTelephoneNumber(phoneType, countryCode, telephoneNumber)
                            .orElseThrow(() -> new NoResultFoundException(String.format("No PhoneNumber found for phone type %s, country code %d and number %d", phoneType, countryCode, telephoneNumber)));

            return applyChanges(phoneNumber, data, user);
        } catch (Exception e) {
            throw new EntityModificationException(String.format("An error occurred while modifying the PhoneNumber : %s", e.getMessage()), e);
        }
    }

    /**
     * Deletes the given {@link PhoneNumber}
     *
     * @param phoneNumber {@link PhoneNumber}
     */
    public void deletePhoneNumber(final PhoneNumber phoneNumber) {
        validateParameterIsNotNull(phoneNumber, CoreConstants.Validation.System.PhoneNumber.PHONE_NUMBER_CANNOT_BE_NULL);
        this.phoneNumberRepository.delete(phoneNumber);
    }


    //  HELPERS

    /**
     * Applies changes to the given {@link PhoneNumber} with the given data
     *
     * @param phoneNumber {@link PhoneNumber}
     * @param data        {@link Map}
     * @param user        {@link User}
     * @return updated {@link PhoneNumber}
     */
    private PhoneNumber applyChanges(PhoneNumber phoneNumber, final Map<String, Object> data, final User user) {

        Map<String, Object> ud = (Map<String, Object>) data.get("phoneNumber");

        phoneNumber.setPhoneType(PhoneType.valueOf(ud.get("phoneType").toString()));
        phoneNumber.setCountryCode(Short.parseShort(ud.get("countryCode").toString()));
        phoneNumber.setTelephoneNumber(Long.parseLong(ud.get("phoneNumber").toString()));
        phoneNumber.setUser(user);

        return this.phoneNumberRepository.save(phoneNumber);
    }
}
