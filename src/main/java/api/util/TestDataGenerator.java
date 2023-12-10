package api.util;

import api.model.Courier;

public class TestDataGenerator {

    // Генерация рандомных данных
    public String getRandomString(int length) {
        // Implementation of getRandomString
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = (int) (Math.random() * characters.length());
            randomString.append(characters.charAt(randomIndex));
        }

        return randomString.toString();
    }

    public Courier generateCourier() {
        Courier courier = new Courier();
        courier.setLogin(getRandomString(5));
        courier.setPassword("1234");
        courier.setFirstName(getRandomString(5));
        return courier;
    }
}
