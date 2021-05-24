package HotelApp;

import HotelApp.hotel.Hotel;
import HotelApp.hotel.Menu;
import HotelApp.hotel.users.Admin;

public class Main {
    public static void main(String[] args) {
        Hotel hotelPrueba= new Hotel();
        Admin adminPrueba=new Admin("admin","admin");
        Menu menu = new Menu();
        menu.initiate();

    }

}
