package com.prizioprinciple.flowerpotapi.core.repositories.system;

import com.prizioprinciple.flowerpotapi.core.enums.system.PhoneType;
import com.prizioprinciple.flowerpotapi.core.models.entities.system.PhoneNumber;
import com.prizioprinciple.flowerpotapi.core.repositories.FlowerpotRepository;
import org.springframework.stereotype.Repository;

/**
 * Data-access layer for {@link PhoneNumber} entities
 *
 * @author Stephen Prizio
 * @version 0.0.1
 */
@Repository
public interface PhoneNumberRepository extends FlowerpotRepository<PhoneNumber> {

    /**
     * Returns a {@link PhoneNumber} for the given {@link PhoneType}, country code and telephone number
     *
     * @param phoneType       {@link PhoneType}
     * @param countryCode     country code, ex: 1 for USA/Canada
     * @param telephoneNumber actual phone number
     * @return {@link PhoneNumber}
     */
    PhoneNumber findPhoneNumberByPhoneTypeAndCountryCodeAndTelephoneNumber(final PhoneType phoneType, final short countryCode, final long telephoneNumber);
}
