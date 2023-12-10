package api.client;

import api.model.Courier;
import com.google.gson.Gson;
import io.restassured.response.Response;

import java.util.Map;

public class CourierClient extends BaseClient {

    private static final String COURIER_PATH = "/api/v1/courier";
    private final Courier courier;

    public CourierClient(Courier courier) {
        this.courier = courier;
    }

    public Response createCourier() {
        return getRequestSpecification()
                .and()
                .body(courier)
                .when()
                .post(COURIER_PATH);
    }

    public Response loginCourier() {
        Response response = getRequestSpecification()
                .and()
                .body(courier)
                .when()
                .post(COURIER_PATH + "/login");

        //String id = response.then().extract().path("id").toString();
        //courier.setId(id);

        return response;
    }


    public Response deleteCourier() {
        return getRequestSpecification()
                .pathParam("id", courier.getId())
                .when()
                .delete(COURIER_PATH + "/{id}");
    }
}
