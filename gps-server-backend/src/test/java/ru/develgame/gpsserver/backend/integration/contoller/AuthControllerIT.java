package ru.develgame.gpsserver.backend.integration.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MvcResult;
import ru.develgame.gpsserver.backend.dto.auth.AuthResponseDto;
import ru.develgame.gpsserver.backend.entity.GPSUser;
import ru.develgame.gpsserver.backend.integration.configuration.BaseRestControllerIT;
import ru.develgame.gpsserver.backend.repository.GPSUserRepository;
import ru.develgame.gpsserver.backend.security.jwt.util.JwtSecretManagerService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerIT extends BaseRestControllerIT {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GPSUserRepository gpsUserRepository;

    @Autowired
    private JwtSecretManagerService jwtSecretManagerService;

    @AfterEach
    void cleanUp() {
        gpsUserRepository.deleteAll();
    }

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void should_getBadRequest_whenLoginNullOrBlank() throws Exception {
        MvcResult actual = mockMvc.perform(post("/auth")
                        .content("""
                                {
                                    "login": null
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andReturn();

        Assertions.assertTrue(actual.getResponse().getContentAsString().contains("Login cannot be empty"));

        actual = mockMvc.perform(post("/auth")
                        .content("""
                                {
                                    "login": " "
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andReturn();

        Assertions.assertTrue(actual.getResponse().getContentAsString().contains("Login cannot be empty"));
    }

    @Test
    void should_getBadRequest_whenPasswordNullOrBlank() throws Exception {
        MvcResult actual = mockMvc.perform(post("/auth")
                        .content("""
                                {
                                    "login": "test",
                                    "password": null
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andReturn();

        Assertions.assertTrue(actual.getResponse().getContentAsString().contains("Password cannot be empty"));

        actual = mockMvc.perform(post("/auth")
                        .content("""
                                {
                                    "login": "test",
                                    "password": " "
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andReturn();

        Assertions.assertTrue(actual.getResponse().getContentAsString().contains("Password cannot be empty"));
    }

    @Test
    void should_getBadRequest_expireInDaysZeroOrLess() throws Exception {
        MvcResult actual = mockMvc.perform(post("/auth")
                        .content("""
                                {
                                    "login": "test",
                                    "password": "pwd",
                                    "expireDays": 0
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andReturn();

        Assertions.assertTrue(actual.getResponse().getContentAsString().contains("Expires must be more than zero"));

        actual = mockMvc.perform(post("/auth")
                        .content("""
                                {
                                    "login": "test",
                                    "password": "pwd",
                                    "expireDays": -1
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andReturn();

        Assertions.assertTrue(actual.getResponse().getContentAsString().contains("Expires must be more than zero"));
    }

    @Test
    void should_loginNotFound() throws Exception {
        GPSUser gpsUser = new GPSUser();
        gpsUser.setName("login123");
        gpsUser.setPwd(passwordEncoder.encode("pwd"));
        gpsUserRepository.save(gpsUser);

        mockMvc.perform(post("/auth")
                        .content("""
                                {
                                    "login": "test",
                                    "password": "pwd",
                                    "expireDays": 1
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()))
                .andReturn();
    }

    @Test
    void should_wrongPassword() throws Exception {
        GPSUser gpsUser = new GPSUser();
        gpsUser.setName("login123");
        gpsUser.setPwd(passwordEncoder.encode("pwd123"));
        gpsUserRepository.save(gpsUser);

        mockMvc.perform(post("/auth")
                        .content("""
                                {
                                    "login": "login123",
                                    "password": "pwd",
                                    "expireDays": 1
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()))
                .andReturn();
    }

    @Test
    void should_createToken() throws Exception {
        GPSUser gpsUser = new GPSUser();
        gpsUser.setName("login123");
        gpsUser.setPwd(passwordEncoder.encode("pwd123"));
        gpsUserRepository.save(gpsUser);

        MvcResult actual = mockMvc.perform(post("/auth")
                        .content("""
                                {
                                    "login": "login123",
                                    "password": "pwd123",
                                    "expireDays": 2
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();

        AuthResponseDto response = objectMapper.readValue(actual.getResponse().getContentAsString(), AuthResponseDto.class);
        Claims actualClaims = Jwts.parser()
                .setSigningKey(jwtSecretManagerService.getSecret())
                .parseClaimsJws(response.token())
                .getBody();

        Assertions.assertEquals(gpsUser.getId(), Long.parseLong(actualClaims.getSubject()));
        Assertions.assertEquals(actualClaims.getIssuedAt().getTime() + 2L * 24L * 3600L * 1000L,
                actualClaims.getExpiration().getTime());
    }
}
