package HotelApp.model.users;


public class Passenger extends User {
    private String name;
    private String dni;
    private String hometown;
    private String homeAddress;

    public Passenger(){
        super();
    }

    public Passenger(String loginName, String password, String name, String dni, String hometown, String homeAddress) {
        super(loginName, password);
        this.name = name;
        this.dni = dni;
        this.hometown =hometown;
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
        return hometown;
    }

    public void setSource(String source) {
        this.hometown = source;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "name='" + name + '\'' +
                ", dni='" + dni + '\'' +
                ", hometown='" + hometown + '\'' +
                ", homeAddress='" + homeAddress + '\'' +
                '}';
    }
}