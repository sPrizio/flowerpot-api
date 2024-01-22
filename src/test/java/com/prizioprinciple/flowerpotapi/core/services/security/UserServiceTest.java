package com.prizioprinciple.flowerpotapi.core.services.security;

import com.prizioprinciple.flowerpotapi.AbstractGenericTest;
import com.prizioprinciple.flowerpotapi.core.exceptions.system.EntityCreationException;
import com.prizioprinciple.flowerpotapi.core.exceptions.system.EntityModificationException;
import com.prizioprinciple.flowerpotapi.core.exceptions.validation.IllegalParameterException;
import com.prizioprinciple.flowerpotapi.core.exceptions.validation.MissingRequiredDataException;
import com.prizioprinciple.flowerpotapi.core.repositories.security.UserRepository;
import com.prizioprinciple.flowerpotapi.core.repositories.system.PhoneNumberRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;

/**
 * Testing class for {@link UserService}
 *
 * @author Stephen Prizio
 * @version 0.0.3
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest extends AbstractGenericTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PhoneNumberRepository phoneNumberRepository;

    @Autowired
    private UserService userService;

    @Before
    public void setUp() {
        Mockito.when(this.userRepository.findUserByEmail("test@email.com")).thenReturn(generateTestUser());
        Mockito.when(this.userRepository.findUserByUsername("test")).thenReturn(generateTestUser());
        Mockito.when(this.userRepository.save(any())).thenReturn(generateTestUser());
        Mockito.when(this.phoneNumberRepository.save(any())).thenReturn(generateTestPhoneNumber());
    }


    //  ----------------- findUserByUsername -----------------

    @Test
    public void test_findUserByUsername_missingParams() {
        assertThatExceptionOfType(IllegalParameterException.class)
                .isThrownBy(() -> this.userService.findUserByUsername(null))
                .withMessage("username cannot be null");
    }

    @Test
    public void test_findUserByUsername_success() {
        assertThat(this.userService.findUserByUsername("test"))
                .isNotEmpty();
    }


    //  ----------------- findUserByEmail -----------------

    @Test
    public void test_findUserByEmail_missingParams() {
        assertThatExceptionOfType(IllegalParameterException.class)
                .isThrownBy(() -> this.userService.findUserByEmail(null))
                .withMessage("email cannot be null");
    }

    @Test
    public void test_findUserByEmail_success() {
        assertThat(this.userService.findUserByEmail("test@email.com"))
                .isNotEmpty();
    }


    //  ----------------- createUser -----------------

    @Test
    public void test_createUser_missingData() {
        assertThatExceptionOfType(MissingRequiredDataException.class)
                .isThrownBy(() -> this.userService.createUser(null))
                .withMessage("The required data for creating a User was null or empty");
    }

    @Test
    public void test_createUser_erroneousCreation() {
        Map<String, Object> map = Map.of("bad", "input");
        assertThatExceptionOfType(EntityCreationException.class)
                .isThrownBy(() -> this.userService.createUser(map))
                .withMessage("A User could not be created : Cannot invoke \"java.util.Map.get(Object)\" because \"ud\" is null");
    }

    @Test
    public void test_createUser_success() {

        Map<String, Object> temp = new HashMap<>();
        temp.put("email", "2022-09-05");
        temp.put("password", "2022-09-11");
        temp.put("lastName", "Prizio");
        temp.put("firstName", "Stephen");
        temp.put("username", "s.prizio");
        temp.put("country", "CAN");
        temp.put("townCity", "Montreal");
        temp.put("timeZoneOffset", "America/Toronto");
        temp.put("phoneNumbers", List.of(Map.of("phoneNumber", Map.of("phoneType", "MOBILE", "countryCode", "1", "telephoneNumber", "5149411025"))));

        Map<String, Object> data = new HashMap<>(Map.of("user", temp));

        assertThat(this.userService.createUser(data))
                .isNotNull()
                .extracting("email", "firstName", "lastName")
                .containsExactly("test@email.com", "Stephen", "Test");
    }


    //  ----------------- updateUser -----------------

    @Test
    public void test_updateUser_missingData() {
        assertThatExceptionOfType(MissingRequiredDataException.class)
                .isThrownBy(() -> this.userService.updateUser(generateTestUser(), null))
                .withMessage("The required data for updating a User was null or empty");
    }

    @Test
    public void test_updateUser_erroneousModification() {
        Map<String, Object> map = Map.of("bad", "input");
        assertThatExceptionOfType(EntityModificationException.class)
                .isThrownBy(() -> this.userService.updateUser(generateTestUser(), map))
                .withMessage("An error occurred while modifying the User : Cannot invoke \"java.util.Map.get(Object)\" because \"ud\" is null");
    }

    @Test
    public void test_updateUser_success() {

        Map<String, Object> temp = new HashMap<>();
        temp.put("email", "2022-09-05");
        temp.put("password", "2022-09-11");
        temp.put("lastName", "Prizio");
        temp.put("firstName", "Stephen");
        temp.put("username", "s.prizio");
        temp.put("country", "CAN");
        temp.put("townCity", "Montreal");
        temp.put("timeZoneOffset", "America/Toronto");
        temp.put("phoneNumbers", List.of(Map.of("phoneNumber", Map.of("phoneType", "MOBILE", "countryCode", "1", "telephoneNumber", "5149411025"))));

        Map<String, Object> data = new HashMap<>(Map.of("user", temp));

        assertThat(this.userService.updateUser(generateTestUser(), data))
                .isNotNull()
                .extracting("email", "firstName", "lastName")
                .containsExactly("test@email.com", "Stephen", "Test");
    }
}
