package model;

public class User {
    private String login;
    private String password;
    private String access;

    public User() {}

    public User(String login, String password, String access) {
        this.login = login;
        this.password = password;
        this.access = access;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }
}
