import api.client.CourierClient;
import api.util.TestDataGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest extends BaseTest {

    @Override
    @Before
    public void setUp() {
        super.setUp();
        courierClient = new CourierClient(courier);
        testDataGenerator = new TestDataGenerator();
        courierClient.createCourier();
    }

    @Test
    @DisplayName("Login courier successfully")
    @Description("Успешная авторизация курьера")
    public void loginCourierSuccessfully() {
        Response response = courierClient.loginCourier();

        response.then().assertThat().statusCode(200).and().body("id", notNullValue());
    }

    @Test
    @DisplayName("Login courier without login")
    @Description("Проверка, что без логина авторизоваться нельзя")
    public void loginCourierWithoutLogin() {
        courier.setLogin(null); // убираем логин

        Response response = courierClient.loginCourier();

        response.then().assertThat().statusCode(400).and().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Login courier without password")
    @Description("Проверка, что без пароля авторизоваться нельзя")
    public void loginCourierWithoutPassword() {
        courier.setPassword(null); // убираем пароль

        Response response = courierClient.loginCourier();

        response.then().assertThat().statusCode(400).and().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Login courier with wrong password")
    @Description("Проверка, что с несуществующим паролем авторизоваться нельзя")
    public void loginCourierWrongPassword() {
        // Меняем пароль
        courier.setPassword(testDataGenerator.getRandomString(5)); // меняем пароль

        Response response = courierClient.loginCourier();

        response.then().assertThat().statusCode(404).and().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Login courier with wrong login")
    @Description("Проверка, что с несуществующим логином авторизоваться нельзя")
    public void loginCourierWrongLogin() {
        // Меняем логин
        courier.setLogin(testDataGenerator.getRandomString(5)); // меняем логин
        Response response = courierClient.loginCourier();

        response.then().assertThat().statusCode(404).and().body("message", equalTo("Учетная запись не найдена"));
    }
}
