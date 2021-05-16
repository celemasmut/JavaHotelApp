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

    @Override
    public void menu() {

    }
}