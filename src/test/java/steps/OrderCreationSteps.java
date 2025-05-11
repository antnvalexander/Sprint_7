package steps;

import dto.OrderReq;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static utils.BaseHelper.ORDER_ENDPOINT;

public class OrderCreationSteps {

    @Step("Создание заказа самоката с параметрами: {orderReq}")
    public static Response createOrder(OrderReq orderReq) {
        return given()
                .contentType("application/json")
                .body(orderReq)
                .when()
                .post(ORDER_ENDPOINT);
    }
}
