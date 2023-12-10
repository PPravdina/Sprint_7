import api.util.TestDataGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest extends TestSetup {

    private final TestDataGenerator testDataGenerator;

    public LoginCourierTest() {
        this.testDataGenerator = new TestDataGenerator();
    }
    @Before
    public void setUp() {
        // Создаем курьера перед выполнением каждого теста
        createCourier();
    }

    @Test
    @DisplayName("Login courier successfully")
    @Description("Успешная авторизация курьера")
    public void loginCourierSuccessfully() {
        Response response = loginCourier();

        response.then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Login courier without login")
    @Description("Проверка, что без логина авторизоваться нельзя")
    public void loginCourierWithoutLogin() {
        courier.setLogin(null); // убираем логин

        Response response = loginCourier();

        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Login courier without password")
    @Description("Проверка, что без пароля авторизоваться нельзя")
    public void loginCourierWithoutPassword() {
        courier.setPassword(null); // убираем пароль

        Response response = loginCourier();

        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Login courier with wrong password")
    @Description("Проверка, что с несуществующим паролем авторизоваться нельзя")
    public void loginCourierWrongPassword() {
        // Меняем пароль
        courier.setPassword(testDataGenerator.getRandomString(5)); // меняем пароль

        Response response = loginCourier();

        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test
    @DisplayName("Login courier with wrong login")
    @Description("Проверка, что с несуществующим логином авторизоваться нельзя")
    public void loginCourierWrongLogin() {
        // Меняем логин
        courier.setLogin(testDataGenerator.getRandomString(5)); // меняем логин
        Response response = loginCourier();

        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }
}
