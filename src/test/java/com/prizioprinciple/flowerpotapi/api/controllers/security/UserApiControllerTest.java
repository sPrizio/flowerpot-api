package com.prizioprinciple.flowerpotapi.api.controllers.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prizioprinciple.flowerpotapi.AbstractGenericTest;
import com.prizioprinciple.flowerpotapi.api.constants.ApiConstants;
import com.prizioprinciple.flowerpotapi.api.converters.account.AccountDTOConverter;
import com.prizioprinciple.flowerpotapi.core.services.platform.UniqueIdentifierService;
import com.prizioprinciple.flowerpotapi.core.services.security.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Testing class for {@link UserApiController}
 *
 * @author Stephen Prizio
 * @version 0.0.3
 */
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@RunWith(SpringRunner.class)
public class UserApiControllerTest extends AbstractGenericTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UniqueIdentifierService uniqueIdentifierService;

    @MockBean
    private AccountDTOConverter accountDTOConverter;

    @MockBean
    private UserService userService;

    @Before
    public void setUp() throws Throwable {
        Mockito.when(this.uniqueIdentifierService.generateUid(any())).thenReturn("MTE4");
        Mockito.when(this.accountDTOConverter.convert(any())).thenReturn(generateTestAccountDTO());
        Mockito.when(this.userService.createUser(anyMap())).thenReturn(generateTestUser());
        Mockito.when(this.userService.updateUser(any(), anyMap())).thenReturn(generateTestUser());
    }


    //  ----------------- getCountryCodes -----------------

    @Test
    public void test_getCountryCodes_success() throws Exception {
        this.mockMvc.perform(get("/api/v1/user/country-codes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[1]", is("1")));
    }


    //  ----------------- getPhoneTypes -----------------

    @Test
    public void test_getPhoneTypes_success() throws Exception {
        this.mockMvc.perform(get("/api/v1/user/phone-types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0]", is("MOBILE")));
    }


    //  ----------------- getCurrencies -----------------

    @Test
    public void test_getCurrencies_success() throws Exception {
        this.mockMvc.perform(get("/api/v1/user/currencies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0]", is("ARS")));
    }


    //  ----------------- getCountries -----------------

    @Test
    public void test_getCountries_success() throws Exception {
        this.mockMvc.perform(get("/api/v1/user/countries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[1]", is("ARGENTINA")));
    }


    //  ----------------- getLanguages -----------------

    @Test
    public void test_getLanguages_success() throws Exception {
        this.mockMvc.perform(get("/api/v1/user/languages"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0]", is("ENGLISH")));
    }


    //  ----------------- postCreateUser -----------------

    @Test
    public void test_postCreateUser_badJsonIntegrity() throws Exception {
        this.mockMvc.perform(post("/api/v1/user/create").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(Map.of("hello", "world"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", containsString(ApiConstants.CLIENT_ERROR_DEFAULT_MESSAGE)));
    }

    @Test
    public void test_postCreateUser_success() throws Exception {

        Map<String, Object> temp = new HashMap<>();
        temp.put("email", "2022-09-05");
        temp.put("password", "2022-09-11");
        temp.put("lastName", "Prizio");
        temp.put("firstName", "Stephen");
        temp.put("username", "s.prizio");
        temp.put("country", "CAN");
        temp.put("townCity", "Montreal");
        temp.put("timeZoneOffset", "America/Toronto");

        Map<String, Object> data = new HashMap<>(Map.of("user", temp));

        this.mockMvc.perform(post("/api/v1/user/create").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(data)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.firstName", is("Stephen")));
    }


    //  ----------------- putUpdateUser -----------------

    @Test
    public void test_putUpdateRetrospective_badJsonIntegrity() throws Exception {

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("email", List.of("test@email.com"));

        this.mockMvc.perform(put("/api/v1/user/update").params(map).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(Map.of("hello", "world"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", containsString(ApiConstants.CLIENT_ERROR_DEFAULT_MESSAGE)));
    }

    @Test
    public void test_putUpdateRetrospective_success() throws Exception {

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("email", List.of("test@email.com"));

        Map<String, Object> temp = new HashMap<>();
        temp.put("email", "2022-09-05");
        temp.put("password", "2022-09-11");
        temp.put("lastName", "Prizio");
        temp.put("firstName", "Stephen");
        temp.put("username", "s.prizio");
        temp.put("country", "CAN");
        temp.put("townCity", "Montreal");
        temp.put("timeZoneOffset", "America/Toronto");

        Map<String, Object> data = new HashMap<>(Map.of("user", temp));

        this.mockMvc.perform(put("/api/v1/user/update").params(map).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(data)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.firstName", is("Stephen")));
    }
}
