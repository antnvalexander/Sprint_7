package tests;

import dto.OrderReq;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.Matchers.notNullValue;
import static steps.OrderCreationSteps.createOrder;
import static utils.BaseHelper.BASE_URL;

@RunWith(Parameterized.class)
public class OrderCreationTest {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String[] color;

    public OrderCreationTest(String firstName, String lastName, String address,
                             String metroStation, String phone, int rentTime,
                             String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters()
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Andrey", "Petrov", "Novaya d1", "Centralnaya", "+56111223", 14, "2020-06-06", "Test1", new String[]{"BLACK"}},
                {"Ivan", "Petrov", "Novaya d3", "Centralnaya", "+561112511", 1, "2020-06-10", "Test2", new String[]{"BLACK", "GREY"}},
                {"Sergey", "Ivanov", "Novaya d4", "Novaya", "+761112511", 1, "2020-11-10", "Test3", new String[]{}},
        });
    }


    @Before
    public void setup() {
        RestAssured.baseURI = BASE_URL;
    }


    @Test
    @Description("Проверка создания заказа")
    public void testCreateOrderWithMinimumData() {
        Allure.getLifecycle().updateTestCase(testResult -> {
            testResult.setName(String.format("Создание заказа для: %s %s, цвет самоката: %s", firstName, lastName, Arrays.toString(color)));
        });
        createOrder(new OrderReq(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color))
                .then()
                .statusCode(201)
                .body("track", notNullValue());
    }
}
