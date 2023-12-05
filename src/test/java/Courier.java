import com.google.gson.Gson;

public class Courier {
    private String login;
    private String password;
    private String firstName;

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
