package HotelApp.hotel.users;

public class Passenger extends User {
    private String name;
    private String dni;
    private String source;
    private String homeAddress;

    public Passenger(int id, String loginName, String password, String name, String dni, String source, String homeAddress) {
        super(id, loginName, password);
        this.name = name;
        this.dni = dni;
        this.source = source;
        this.homeAddress = homeAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    @Override
    public void menu() {

    }
}