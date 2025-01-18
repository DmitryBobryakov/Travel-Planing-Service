package org.tps.autorization;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AuthControllerTest {
    private static final String BASE_URI = "http://localhost";
    private static final int PORT = 8080;
    private static final String LOGIN_ENDPOINT = "/login";
    private static final String REGISTER_ENDPOINT = "/register";
    private static final String VALID_USERNAME = "testuser";
    private static final String PASSWORD = "1234";

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = PORT;
    }

    @Test
    void testLoginSuccess() {
        given()
                .contentType("application/json")
                .body("{\"username\":\"" + VALID_USERNAME + "\", \"password\":\"" + PASSWORD + "\"}")
                .when()
                .post(LOGIN_ENDPOINT)
                .then()
                .statusCode(200)
                .body("status", equalTo("SUCCESS"));
    }

    @Test
    void testLoginFailure() {
        given()
                .contentType("application/json")
                .body("{\"username\":\"invaliduser\", \"password\":\"wrongpassword\"}")
                .when()
                .post(LOGIN_ENDPOINT)
                .then()
                .statusCode(401)
                .body("status", equalTo("ERROR"));
    }

    @Test
    void testRegisterSuccess() {
        given()
                .contentType("application/json")
                .body("{\"username\":\"newuser\", \"password\":\"1234\", \"email\":\"new@example.com\", \"phone\":\"1234567890\"}")
                .when()
                .post(REGISTER_ENDPOINT)
                .then()
                .statusCode(201)
                .body("status", equalTo("SUCCESS"));
    }
}
