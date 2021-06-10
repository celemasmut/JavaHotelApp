package HotelApp.hotel;

import HotelApp.datafile.DataFile;
import HotelApp.datafile.SaveTypeRoom;
import HotelApp.exception.UserDoesNotExistException;
import HotelApp.model.bedrooms.DoubleRoom;
import HotelApp.model.bedrooms.FamilyRoom;
import HotelApp.model.bedrooms.KingRoom;
import HotelApp.model.bedrooms.SingleRoom;
import HotelApp.model.users.Admin;
import HotelApp.model.users.Passenger;
import HotelApp.model.users.Receptionist;
import HotelApp.util.State;

public class Main {
    public static void main(String[] args) {
        Hotel hotelPrueba= new Hotel();
        hotelPrueba.menu();


    }

}
