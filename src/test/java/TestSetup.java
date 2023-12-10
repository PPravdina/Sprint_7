import api.client.CourierClient;
import api.model.Courier;
import api.util.TestDataGenerator;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.After;

import static org.hamcrest.CoreMatchers.equalTo;

public class TestSetup {

    private final CourierClient courierClient;
    protected Courier courier;

    public TestSetup() {
        initializeTestData(new TestDataGenerator());
        courierClient = new CourierClient(courier);
    }

    private void initializeTestData(TestDataGenerator testDataGenerator) {
        courier = testDataGenerator.generateCourier();
    }

    @Step("Create courier")
    protected Response createCourier() {
        return courierClient.createCourier();
    }

    @Step("Login courier")
    protected Response loginCourier() {
        return courierClient.loginCourier();
    }

    @Step("Delete courier")
    @After
    public void deleteCourier() {
        if (courier != null && courier.getId() != null) {
            Response response = courierClient.deleteCourier();
            response.then()
                    .log().all()
                    .statusCode(200)
                    .body("ok", equalTo(true));
        }
    }
}

