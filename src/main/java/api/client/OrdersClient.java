package api.client;

import io.restassured.response.Response;

public class OrdersClient extends BaseClient {

    private static final String ORDERS_PATH = "/api/v1/orders";

    public Response createOrder(Object body) {
        return getRequestSpecification()
                .and()
                .body(body)
                .when()
                .post(ORDERS_PATH);
    }

    public Response getOrders() {
        return getRequestSpecification()
                .when()
                .get(ORDERS_PATH);
    }
}
