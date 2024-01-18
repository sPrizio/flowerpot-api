package com.prizioprinciple.flowerpotapi.api.controllers.security;

import com.prizioprinciple.flowerpotapi.api.controllers.AbstractApiController;
import com.prizioprinciple.flowerpotapi.api.converters.security.UserDTOConverter;
import com.prizioprinciple.flowerpotapi.api.models.records.json.StandardJsonResponse;
import com.prizioprinciple.flowerpotapi.core.enums.system.Country;
import com.prizioprinciple.flowerpotapi.core.enums.system.Language;
import com.prizioprinciple.flowerpotapi.core.enums.system.PhoneType;
import com.prizioprinciple.flowerpotapi.core.enums.account.Currency;
import com.prizioprinciple.flowerpotapi.core.models.entities.security.User;
import com.prizioprinciple.flowerpotapi.core.services.security.UserService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static com.prizioprinciple.flowerpotapi.core.validation.GenericValidator.validateJsonIntegrity;


/**
 * API controller for {@link User}
 *
 * @author Stephen Prizio
 * @version 0.0.1
 */
@RestController
@RequestMapping("${base.api.controller.endpoint}/user")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT})
public class UserApiController extends AbstractApiController {

    private static final List<String> REQUIRED_JSON_VALUES = List.of("user");

    @Resource(name = "userDTOConverter")
    private UserDTOConverter userDTOConverter;

    @Resource(name = "userService")
    private UserService userService;


    //  METHODS


    //  ----------------- GET REQUESTS -----------------

    /**
     * Returns a {@link StandardJsonResponse} containing all of the {@link Country} codes
     *
     * @return {@link StandardJsonResponse}
     */
    @GetMapping("/country-codes")
    public StandardJsonResponse getCountryCodes() {
        return new StandardJsonResponse(
                true,
                Arrays.stream(Country.values())
                        .map(Country::getPhoneCode)
                        .collect(Collectors.toCollection(TreeSet::new)),
                StringUtils.EMPTY
        );
    }

    /**
     * Returns a {@link StandardJsonResponse} containing all of the {@link PhoneType}s
     *
     * @return {@link StandardJsonResponse}
     */
    @GetMapping("/phone-types")
    public StandardJsonResponse getPhoneTypes() {
        return new StandardJsonResponse(true, PhoneType.values(), StringUtils.EMPTY);
    }

    /**
     * Returns a {@link StandardJsonResponse} containing all of the {@link Currency}s
     *
     * @return {@link StandardJsonResponse}
     */
    @GetMapping("/currencies")
    public StandardJsonResponse getCurrencies() {
        return new StandardJsonResponse(true, Arrays.stream(Currency.values()).map(Currency::getIsoCode).collect(Collectors.toCollection(TreeSet::new)), StringUtils.EMPTY);
    }

    /**
     * Returns a {@link StandardJsonResponse} containing all of the {@link Country}s
     *
     * @return {@link StandardJsonResponse}
     */
    @GetMapping("/countries")
    public StandardJsonResponse getCountries() {
        return new StandardJsonResponse(true, Country.values(), StringUtils.EMPTY);
    }

    /**
     * Returns a {@link StandardJsonResponse} containing all of the {@link Language}s
     *
     * @return {@link StandardJsonResponse}
     */
    @GetMapping("/languages")
    public StandardJsonResponse getLanguages() {
        return new StandardJsonResponse(true, Language.values(), StringUtils.EMPTY);
    }


    //  ----------------- POST REQUESTS -----------------

    /**
     * Creates a new {@link User}
     *
     * @param data json data
     * @return {@link StandardJsonResponse}
     */
    @PostMapping("/create")
    public StandardJsonResponse postCreateUser(final @RequestBody Map<String, Object> data) {
        validateJsonIntegrity(data, REQUIRED_JSON_VALUES, "json did not contain of the required keys : %s", REQUIRED_JSON_VALUES.toString());
        return new StandardJsonResponse(true, this.userDTOConverter.convert(this.userService.createUser(data)), StringUtils.EMPTY);
    }


    //  ----------------- PUT REQUESTS -----------------

    /**
     * Updates an existing {@link User}
     *
     * @param email email
     * @param data json data
     * @return {@link StandardJsonResponse}
     */
    @PutMapping("/update")
    public StandardJsonResponse putUpdateUser(final @RequestParam("email") String email, final @RequestBody Map<String, Object> data) {
        validateJsonIntegrity(data, REQUIRED_JSON_VALUES, "json did not contain of the required keys : %s", REQUIRED_JSON_VALUES.toString());
        return new StandardJsonResponse(true, this.userDTOConverter.convert(this.userService.updateUser(email, data)), StringUtils.EMPTY);
    }
}
