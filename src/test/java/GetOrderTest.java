import api.client.OrdersClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class GetOrderTest extends TestSetup {

    private final OrdersClient ordersClient;

    public GetOrderTest() {
        super();
        this.ordersClient = new OrdersClient();
    }

    @Test
    @DisplayName("Get all orders")
    @Description("Проверка, что возвращается список заказов и он больше 0")
    public void getAllOrders() {
        Response response = ordersClient.getOrders();

        response.then().assertThat().statusCode(200)
                .and().body("orders", not(empty()))
                .and().body("pageInfo.total", notNullValue());
    }
}
