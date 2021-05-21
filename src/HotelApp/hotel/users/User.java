package HotelApp.hotel.users;

public abstract class User {
    private int id;
    private String loginName;
    private String password;

    public User(int autoIncrease, String loginName, String password) {
        this.id = autoIncrease;
        this.loginName = loginName;
        this.password = password;
    }
    public abstract void menu();

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
