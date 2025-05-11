package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;
import static steps.AuthCourierSteps.*;
import static steps.CommonSteps.getIdCourier;
import static steps.CreateCourierSteps.createCourier;
import static steps.DeleteCourierSteps.deleteCourier;
import static utils.BaseHelper.BASE_URL;

public class CourierLoginTest {
    String login = "tesAuthUser";
    String password = "qwerty1234";
    String firstName = "Test Courier";

    @Before
    public void setup() {
        RestAssured.baseURI = BASE_URL;
        createCourier(login, password, firstName);
    }

    @After
    public void clean() {
        deleteCourier(getIdCourier(login, password));
    }

    @DisplayName("Проверка авторизации курьера")
    @Description("Выполняем авторизацию с валидными данными для входа")
    @Test
    public void testLoginSuccessful() {
        loginCourier(login, password)
                .then()
                .statusCode(200)
                .body("id", notNullValue());
    }

    @DisplayName("Проверка обязательности полей для авторизации")
    @Test
    public void testFailWithoutRequiredFields() {
        loginCourierWithoutPassword(login, 400);
    }

    @DisplayName("Проверка ввода некорректного пароля")
    @Description("Выполняем авторизацию с неверным паролем")
    @Test
    public void testFailWithInvalidCredentials() {
        loginCourierWithIncorrectPassword(login, "password", "Учетная запись не найдена", 404);
    }


    @DisplayName("Проверка авторизации несуществующего пользователя")
    @Description("Выполняем авторизацию с логином пользователя, которого нет в системе")
    @Test
    public void testFailForNonexistentUser() {
        loginNonExistCourier("NonexistentUser", password, "Учетная запись не найдена", 404);
    }
}
