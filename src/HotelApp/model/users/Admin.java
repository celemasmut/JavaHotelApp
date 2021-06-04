package HotelApp.model.users;


public class Admin extends User {
    public Admin(){}
    public Admin(String loginName, String password) {
        super(loginName, password);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "user= '" +this.getLoginName()+'\''+
                '}';
    }
}

