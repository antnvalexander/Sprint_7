package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static utils.BaseHelper.LOGIN_COURIER_ENDPOINT;

public class AuthCourierSteps {
    @Step("Авторизация курьера с логином: {login}, и паролем: {password}")
    public static Response loginCourier(String login, String password) {
        return given()
                .contentType("application/json")
                .body(String.format("{\"login\": \"%s\", \"password\": \"%s\"}",
                        login, password))
                .when()
                .post(LOGIN_COURIER_ENDPOINT);
    }

    @Step("Авторизация курьера без обязательного поля - Пароль. Получили ошибку - {message}, и статус код - {code}")
    public static void loginCourierWithoutPassword(String login, int code) {
        given()
                .contentType("application/json")
                .body(String.format("{\"login\": \"%s\"",
                        login))
                .when()
                .post(LOGIN_COURIER_ENDPOINT)
                .then()
                .statusCode(code);
    }

    @Step("Авторизация курьера с неправильным паролем. Получили ошибку - {message}, и статус код - {code}")
    public static void loginCourierWithIncorrectPassword(String login, String password, String message, int code) {
        loginCourier(login, password)
                .then()
                .statusCode(code)
                .body("message", equalTo(message));
    }

    @Step("Авторизация курьера несуществующего пользователя. Получили ошибку - {message}, и статус код - {code}")
    public static void loginNonExistCourier(String login, String password, String message, int code) {
        loginCourier(login, password)
                .then()
                .statusCode(code)
                .body("message", equalTo(message));
    }
}
