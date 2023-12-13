import api.client.CourierClient;
import api.model.Courier;
import api.util.TestDataGenerator;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.After;

import static org.hamcrest.CoreMatchers.equalTo;

public class BaseTest {
    protected Courier courier;
    protected CourierClient courierClient;
    protected TestDataGenerator testDataGenerator;

    @Step("Set up test data")
    public void setUp() {
        TestDataGenerator testDataGenerator = new TestDataGenerator();
        initializeTestData(testDataGenerator);
    }

    private void initializeTestData(TestDataGenerator testDataGenerator) {
        courier = testDataGenerator.generateCourier();
    }

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

