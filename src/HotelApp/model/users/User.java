package HotelApp.model.users;

import java.util.Objects;

public abstract class User  {
    private static int idStatic = 0;
    private int id;
    private String loginName;
    private String password;
    private int state = 1; //activo == 1

    public User(String loginName, String password) {
        id = getId();
        this.loginName = loginName;
        this.password = password;
    }

    public User() {

    }

    public int getId() {
        return id = idStatic++;
    }

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

    public int deleteLogic(){
        return this.state = 0; /// 0 == eliminado logico
    }

    public int getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(loginName, user.loginName) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, loginName, password);
    }
}
