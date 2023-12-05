import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest extends TestSetup {
    private final Order order;

    public CreateOrderTest(Order order) {
        this.order = order;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new Order("Naruto1", "Uchiha1", "Konoha, 141 apt.", 1, "+7 800 355 35 31", 1, "2023-06-01", "Saske, come back to Konoha", new String[]{"BLACK"})},
                {new Order("Naruto2", "Uchiha2", "Konoha, 142 apt.", 15, "+7 800 355 35 32", 5, "2023-06-02", "Saske, come back to Konoha", new String[]{"BLACK", "GREY"})},
                {new Order("Naruto3", "Uchiha3", "Konoha, 143 apt.", 30, "+7 800 355 35 33", 10, "2024-06-03", "Saske, come back to Konoha", null)}
        });
    }

    // Метод для создания заказа
    @Step("Create order")
    private Response createOrderRequest() {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post("/api/v1/orders");
    }

    @Test
    @DisplayName("Create order with different color option")
    @Description("Проверка, что можно создать заказ с разными опциями по выбору цвета")
    public void createOrder() {
        Response response = createOrderRequest();

        response.then().assertThat().statusCode(201)
                .and().body("track", notNullValue());
    }

}
