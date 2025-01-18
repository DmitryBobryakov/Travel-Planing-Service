package org.tps.authorization;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AuthControllerTest {

    private static final String BASE_URI = "http://localhost";
    private static final int PORT = 8080;
    private static final String LOGIN_ENDPOINT = "/login";
    private static final String REGISTER_ENDPOINT = "/register";

    @BeforeAll
    public static void setup() {
        // Установка базового URL и порта для RestAssured
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = PORT;
    }

    @Test
    void testRegisterSuccess() {
        // Тест на успешную регистрацию.
        // Предполагается, что пользователя с таким username пока нет в БД.
        given()
                .contentType(ContentType.URLENC)
                .formParam("username", "newuser123")
                .formParam("password", "1234")
                .formParam("email", "newuser123@example.com")
                .formParam("phone", "5550001112")
                .when()
                .post(REGISTER_ENDPOINT)
                .then()
                .statusCode(201)
                .body("status", equalTo("SUCCESS"));
    }

    @Test
    void testRegisterConflict() {
        // Тест на конфликт при регистрации, если пользователь уже существует.
        // Сначала создаём пользователя:
        given()
                .contentType(ContentType.URLENC)
                .formParam("username", "existinguser")
                .formParam("password", "1234")
                .formParam("email", "existing@example.com")
                .formParam("phone", "5559998887")
                .when()
                .post(REGISTER_ENDPOINT)
                .then()
                // Первый раз ожидаем SUCCESS или Conflict, в зависимости от БД.
                // Чтобы гарантировать SUCCESS при первом вызове,
                // убедитесь, что 'existinguser' нет в БД.
                .statusCode(201)
                .body("status", equalTo("SUCCESS"));

        // Пытаемся зарегистрировать того же пользователя
        given()
                .contentType(ContentType.URLENC)
                .formParam("username", "existinguser")  // тот же username
                .formParam("password", "1234")
                .formParam("email", "existing2@example.com")
                .formParam("phone", "5559998887")
                .when()
                .post(REGISTER_ENDPOINT)
                .then()
                // Ожидаем Conflict
                .statusCode(409)
                .body("status", equalTo("ERROR"));
    }

    @Test
    void testLoginSuccess() {
        // Перед тестом нужно убедиться, что в БД
        // есть пользователь с phone = "1234567890" и паролем "1234"
        // либо мы можем сами его зарегистрировать:

        given()
                .contentType(ContentType.URLENC)
                .formParam("username", "loginUser")
                .formParam("password", "1234")
                .formParam("email", "loginuser@example.com")
                .formParam("phone", "1234567890")  // <-- phone для логина
                .when()
                .post(REGISTER_ENDPOINT)
                .then()
                .statusCode(201)
                .body("status", equalTo("SUCCESS"));

        // Теперь пробуем логиниться
        given()
                .contentType(ContentType.URLENC)
                .formParam("phone", "1234567890")
                .formParam("password", "1234")
                .when()
                .post(LOGIN_ENDPOINT)
                .then()
                .statusCode(200)
                .body("status", equalTo("SUCCESS"));
    }

    @Test
    void testLoginFailure() {
        // Пытаемся залогиниться с неверным паролем или
        // на несуществующего пользователя
        given()
                .contentType(ContentType.URLENC)
                .formParam("phone", "9999999999")  // Скорее всего не существует
                .formParam("password", "wrongpassword")
                .when()
                .post(LOGIN_ENDPOINT)
                .then()
                .statusCode(401)
                .body("status", equalTo("ERROR"));
    }
}
