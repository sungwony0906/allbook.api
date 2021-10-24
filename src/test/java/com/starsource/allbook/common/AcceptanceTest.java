package com.starsource.allbook.common;

import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Profile(value = {"local","test"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@WithMockUser(value = "spring", roles = "MEMBER")
public abstract class AcceptanceTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.authentication = RestAssured.form("spring","secret",
                new FormAuthConfig("/login", "username", "password")
                        .withLoggingEnabled());
    }
}
