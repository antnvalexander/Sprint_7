package steps;

import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;
import static utils.BaseHelper.DELETE_COURIER_ENDPOINT;

public class DeleteCourierSteps {
    @Step("Удалить курьера с id: {id}")
    public static void deleteCourier(String id) {
        given()
                .pathParam("id", id)
                .when()
                .delete(DELETE_COURIER_ENDPOINT)
                .then()
                .statusCode(200);
    }
}
