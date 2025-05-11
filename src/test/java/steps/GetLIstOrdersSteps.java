package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static utils.BaseHelper.ORDER_ENDPOINT;

public class GetLIstOrdersSteps {

    @Step("Получить список заказов")
    public static Response checkListOrder() {
        return given()
                .contentType("application/json")
                .when()
                .get(ORDER_ENDPOINT);
    }
}
