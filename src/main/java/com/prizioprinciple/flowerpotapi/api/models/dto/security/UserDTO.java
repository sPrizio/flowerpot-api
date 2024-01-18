package com.prizioprinciple.flowerpotapi.api.models.dto.security;

import com.prizioprinciple.flowerpotapi.api.models.dto.GenericDTO;
import com.prizioprinciple.flowerpotapi.api.models.dto.account.AccountDTO;
import com.prizioprinciple.flowerpotapi.api.models.dto.system.PhoneNumberDTO;
import com.prizioprinciple.flowerpotapi.core.models.entities.security.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * A DTO representation of a {@link User}
 *
 * @author Stephen Prizio
 * @version 0.0.1
 */
@Setter
@Getter
public class UserDTO implements GenericDTO {

    private String uid;

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private List<PhoneNumberDTO> phones;

    private List<AccountDTO> accounts;

    private List<String> roles;
}
