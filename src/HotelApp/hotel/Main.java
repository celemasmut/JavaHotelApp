package HotelApp.hotel;

import HotelApp.datafile.DataFile;
import HotelApp.model.users.Admin;
import HotelApp.model.users.Passenger;
import HotelApp.model.users.Receptionist;

public class Main {
    public static void main(String[] args) {
        DataFile dataFile = new DataFile();
        Hotel hotelPrueba= new Hotel();

        ///hotelPrueba.setRoomsList(DataFile.readRoomJson("files/room.json"));

        Menu menu = new Menu();
        menu.initiate();
        dataFile.writeJsonBookings(Hotel.getReservationGenericList().getList(),"files/booking.json");


    }

}
