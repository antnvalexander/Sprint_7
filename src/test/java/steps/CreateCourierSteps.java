package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static utils.BaseHelper.CREATE_COURIER_ENDPOINT;

public class CreateCourierSteps {

    @Step("Создать курьера с логином: {login}, паролем: {password}, и именем: {firstName}")
    public static Response createCourier(String login, String password, String firstName) {
        return given()
                .contentType("application/json")
                .body(String.format("{\"login\": \"%s\", \"password\": \"%s\", \"firstName\": \"%s\"}",
                        login, password, firstName))
                .when()
                .post(CREATE_COURIER_ENDPOINT);
    }

    @Step("Получили ошибку: {message} при попытке создать курьера, и статус код: {code}")
    public static void createCourierWithError(String login, String password, String firstName, String message, int code) {
        createCourier(login, password, firstName)
                .then()
                .statusCode(code)
                .body("message", equalTo(message));
    }

    @Step("Указать в теле запроса название поля Login вместо login")
    public static void createCourierWithIncorrectLoginFieldName(String login, String password, String firstName, String message, int code) {
        given()
                .contentType("application/json")
                .body(String.format("{\"Login\": \"%s\", \"password\": \"%s\", \"firstName\": \"%s\"}", login, password, firstName))
                .when()
                .post(CREATE_COURIER_ENDPOINT)
                .then()
                .statusCode(code)
                .body("message", equalTo(message));
    }

    @Step("Создать курьера заполнения поля Login. Получили ошибку: {message} и статус код: {code}")
    public static void createCourierWithoutLogin(String login, String password, String firstName, String message, int code) {
        given()
                .contentType("application/json")
                .body(String.format("{\"password\": \"%s\", \"firstName\": \"%s\"}", password, firstName))
                .when()
                .post(CREATE_COURIER_ENDPOINT)
                .then()
                .statusCode(code)
                .body("message", equalTo(message));
    }

    @Step("Создать курьера заполнения поля Password. Получили ошибку: {message} и статус код: {code}")
    public static void createCourierWithoutPassword(String login, String password, String firstName, String message, int code) {
        given()
                .contentType("application/json")
                .body(String.format("{\"login\": \"%s\", \"firstName\": \"%s\"}", login, firstName))
                .when()
                .post(CREATE_COURIER_ENDPOINT)
                .then()
                .statusCode(code)
                .body("message", equalTo(message));
    }
}
