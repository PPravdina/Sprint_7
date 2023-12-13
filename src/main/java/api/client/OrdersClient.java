package api.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class OrdersClient extends BaseClient {

    private static final String ORDERS_PATH = "/api/v1/orders";

    @Step("Create order")
    public Response createOrder(Object body) {
        return getRequestSpecification()
                .and()
                .body(body)
                .when()
                .post(ORDERS_PATH);
    }

    @Step("Get all orders")
    public Response getOrders() {
        return getRequestSpecification()
                .when()
                .get(ORDERS_PATH);
    }
}
