package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static steps.GetLIstOrdersSteps.checkListOrder;
import static utils.BaseHelper.BASE_URL;

public class GetListOrdersTest {
    @Before
    public void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @DisplayName("Проверка получения списка заказов")
    @Description("Получение всего списка заказов и проверка, что тело ответа не пустое")
    public void testGetListOrders() {
        checkListOrder()
                .then()
                .statusCode(200)
                .body("orders", is(not(empty())))
                .body("orders", is(instanceOf(List.class)));
    }
}
