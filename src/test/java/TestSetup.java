import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TestSetup {

    protected Courier courier;
    private String baseUri;


    // Конструктор с явным указанием базового URI
    public TestSetup(String baseUri) {
        setBaseUri(baseUri);
        RestAssured.baseURI = baseUri;
        initializeTestData();
    }

    // Конструктор по умолчанию, если не указан базовый URI
    public TestSetup() {
        this("https://qa-scooter.praktikum-services.ru");
        initializeTestData();
    }

    public void setBaseUri(String baseUri) {
        this.baseUri = baseUri;
    }

    private void initializeTestData() {
        courier = new Courier();
        courier.setLogin(getRandomString(5));
        courier.setPassword("1234");
        courier.setFirstName(getRandomString(5));
    }

    //Генерация рандомных данных
    protected String getRandomString(int length) {
        // Implementation of getRandomString
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = (int) (Math.random() * characters.length());
            randomString.append(characters.charAt(randomIndex));
        }

        return randomString.toString();
    }

    @Step("Create courier")
    protected Response createCourier() {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier.toString())
                .when()
                .post("/api/v1/courier");

    }

    @Step("Login courier")
    protected Response loginCourier() {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier.toString())
                .when()
                .post("/api/v1/courier/login");
    }
}

