import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest extends TestSetup {
    @Test
    @DisplayName("Login courier successfully")
    @Description("Успешная авторизация курьера")
    public void loginCourierSuccessfully() {
        // Создаем курьера перед выполнением теста
        createCourier();

        Response response = loginCourier();

        response.then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Login courier without login")
    @Description("Проверка, что без логина авторизоваться нельзя")
    public void loginCourierWithoutLogin() {
        // Создаем курьера перед выполнением теста
        createCourier();

        // Подготовка JSON-данных для входа курьера
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
        // Создаем курьера перед выполнением теста
        createCourier();

        // Подготовка JSON-данных для входа курьера
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
        // Создаем курьера перед выполнением теста
        createCourier();

        // Подготовка JSON-данных для входа курьера
        courier.setPassword(getRandomString(5)); // меняем пароль

        Response response = loginCourier();

        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test
    @DisplayName("Login courier with wrong login")
    @Description("Проверка, что с несуществующим логином авторизоваться нельзя")
    public void loginCourierWrongLogin() {
        // Создаем курьера перед выполнением теста
        createCourier();

        // Подготовка JSON-данных для входа курьера
        courier.setLogin(getRandomString(5)); // меняем логин
        Response response = loginCourier();

        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }
}
