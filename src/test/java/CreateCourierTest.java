import api.client.CourierClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTest extends BaseTest {

    @Override
    @Before
    public void setUp() {
        super.setUp();
        courierClient = new CourierClient(courier);
    }

    @Test
    @DisplayName("Create courier successfully")
    @Description("В результате теста должен быть создан курьер")
    public void createCourierSuccessfully() {
        Response response = courierClient.createCourier();
        response.then().assertThat().statusCode(201)
                .and()
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Create courier with duplicate login")
    @Description("Проверка, что нельзя создать два курьера с одинаковым логином")
    public void createCourierWithDuplicateLogin() {
        courierClient.createCourier();
        // Попытка создать курьера с тем же логином
        Response response = courierClient.createCourier();

        // Проверка, что возвращается ошибка с кодом 409 (Conflict)
        response.then().assertThat().statusCode(409)
                .and()
                .body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Create courier without login")
    @Description("Проверка, что без логина создать курьера не получится")
    public void createCourierWithoutLogin() {
        // Попытка создать курьера без логина
        courier.setLogin(null); // убираем логин
        Response response = courierClient.createCourier();

        // Проверка, что возвращается ошибка с кодом 400 (Bad Request)
        response.then().assertThat().statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Create courier without password")
    @Description("Проверка, что без пароля создать курьера не получится")
    public void createCourierWithoutPassword() {
        // Попытка создать курьера без пароля
        courier.setPassword(null); // убираем пароль
        Response response = courierClient.createCourier();

        // Проверка, что возвращается ошибка с кодом 400 (Bad Request)
        response.then().assertThat().statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Create courier without name")
    @Description("Проверка, что имя пользователя необязательное поле")
    public void createCourierWithoutName() {
        // Попытка создать курьера без имени
        courier.setFirstName(null); // убираем имя
        Response response = courierClient.createCourier();

        // Проверка, что firstName - необязательное
        response.then().assertThat().statusCode(201)
                .and()
                .body("ok", equalTo(true));
    }
}
