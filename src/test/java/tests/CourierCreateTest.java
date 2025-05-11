package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static steps.CommonSteps.getIdCourier;
import static steps.CreateCourierSteps.*;
import static steps.DeleteCourierSteps.deleteCourier;
import static utils.BaseHelper.BASE_URL;

public class CourierCreateTest {

    String login = "testUser";
    String password = "qwerty1234";
    String firstName = "Test Courier";

    @Before
    public void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    @DisplayName("Проверка создания курьера")
    @Description("Создание курьера с валидным заполнением полей")
    @Test
    public void testSuccessfulCourierCreation() {
        createCourier(login, password, firstName)
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));
        deleteCourier(getIdCourier(login, password));
    }

    @DisplayName("Проверка создания курьера с существующим логином")
    @Description("Если выполнить повторную регистрацию с существующим логином - система должна отдавать ошибку")
    @Test
    public void testDuplicateCourierCreation() {
        createCourier(login, password, firstName);
        createCourierWithError(login, password, firstName, "Этот логин уже используется. Попробуйте другой.", 409);
        deleteCourier(getIdCourier(login, password));
    }

    @DisplayName("Проверка выполнения запроса без обязательных полей")
    @Description("В тесте выполняем два запроса. В первом отсутствует Логин, Во втором - пароль")
    @Test
    public void testMissingRequiredFields() {
        createCourierWithoutLogin(login, password, firstName, "Недостаточно данных для создания учетной записи", 400);
        createCourierWithoutPassword(login, password, firstName, "Недостаточно данных для создания учетной записи", 400);
    }

    @DisplayName("Создание курьера с невалидным наименованием поля")
    @Description("В тело запроса указываем поле Login с заглавной буквы")
    @Test
    public void testFieldCaseSensitivity() {
        createCourierWithIncorrectLoginFieldName(login, password, firstName, "Недостаточно данных для создания учетной записи", 400);
    }
}
