package HotelApp;

import HotelApp.hotel.Hotel;
import HotelApp.hotel.users.Admin;

public class Main {
    public static void main(String[] args) {
        Hotel hotelPrueba= new Hotel();
        Admin adminPrueba=new Admin(4,"admin","admin");
        hotelPrueba.addUserToList(adminPrueba);
        hotelPrueba.login();

    }

}
