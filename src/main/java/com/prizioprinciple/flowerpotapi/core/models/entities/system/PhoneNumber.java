package com.prizioprinciple.flowerpotapi.core.models.entities.system;

import com.prizioprinciple.flowerpotapi.core.enums.system.PhoneType;
import com.prizioprinciple.flowerpotapi.core.models.entities.GenericEntity;
import com.prizioprinciple.flowerpotapi.core.models.entities.security.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Class representation of a phone number
 *
 * @author Stephen Prizio
 * @version 0.0.1
 */
@Entity
@Table(name = "phone_numbers")
public class PhoneNumber implements GenericEntity {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column
    private PhoneType phoneType;

    @Getter
    @Setter
    @Column
    private short countryCode;

    @Getter
    @Setter
    @Column
    private long telephoneNumber;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;


    //  METHODS

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhoneNumber that = (PhoneNumber) o;

        if (countryCode != that.countryCode) return false;
        if (telephoneNumber != that.telephoneNumber) return false;
        return phoneType == that.phoneType;
    }

    @Override
    public int hashCode() {
        int result = phoneType.hashCode();
        result = 31 * result + (int) countryCode;
        result = 31 * result + (int) (telephoneNumber ^ (telephoneNumber >>> 32));
        return result;
    }
}
