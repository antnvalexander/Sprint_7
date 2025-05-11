package steps;

import io.qameta.allure.Step;

import static steps.AuthCourierSteps.loginCourier;

public class CommonSteps {
    @Step("Получили id курьера после авторизации")
    public static String getIdCourier(String login, String password) {
        return loginCourier(login, password).then()
                .extract()
                .path("id").toString();
    }
}
