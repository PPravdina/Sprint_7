package api.client;

import api.model.Courier;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class CourierClient extends BaseClient {

    private static final String COURIER_PATH = "/api/v1/courier";
    private final Courier courier;

    public CourierClient(Courier courier) {
        this.courier = courier;
    }

    @Step("Create courier")
    public Response createCourier() {
        return getRequestSpecification()
                .and()
                .body(courier)
                .when()
                .post(COURIER_PATH);
    }

    @Step("Login courier")
    public Response loginCourier() {
        Response response = getRequestSpecification()
                .and()
                .body(courier)
                .when()
                .post(COURIER_PATH + "/login");

        return response;
    }

    @Step("Delete courier")
    public Response deleteCourier() {
        return getRequestSpecification()
                .pathParam("id", courier.getId())
                .when()
                .delete(COURIER_PATH + "/{id}");
    }
}
