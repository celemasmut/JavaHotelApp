package HotelApp.hotel.users;

import HotelApp.hotel.Register;
import java.util.Scanner;

import static HotelApp.hotel.Hotel.addUserToList;

public class Passenger extends User  {
    private String name;
    private String dni;
    private String hometown;
    private String homeAddress;

    public Passenger(int id, String loginName, String password, String name, String dni, String hometown, String homeAddress) {
        super(id, loginName, password);
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
    public void menu() {

    }


}