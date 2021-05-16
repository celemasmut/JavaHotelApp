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
}
