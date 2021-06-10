package HotelApp.hotel;

import HotelApp.datafile.DataFile;
import HotelApp.datafile.SaveInfoUsers;
import HotelApp.exception.UserDoesNotExistException;
import HotelApp.model.bedrooms.DoubleRoom;
import HotelApp.model.bedrooms.FamilyRoom;
import HotelApp.model.bedrooms.KingRoom;
import HotelApp.model.bedrooms.SingleRoom;
import HotelApp.model.users.Admin;
import HotelApp.model.users.Passenger;
import HotelApp.model.users.Receptionist;
import HotelApp.util.State;

import static HotelApp.hotel.Hotel.*;

public class Main {
    public static void main(String[] args) {
        DataFile dataFile = new DataFile();
        Hotel hotelPrueba= new Hotel();

        SaveInfoUsers saveinfo = new SaveInfoUsers();
        saveinfo=dataFile.readInfo("files/user.json");
        addToList(saveinfo);
        setRoom(dataFile.readRoomJson("files/room.json"));

        showListOfRoom();
        try {
            showUsers();
        } catch (UserDoesNotExistException e) {
            e.printStackTrace();
        }


        //  dataFile.writeInfo(saveInfo,"files/user.json");

        //dataFile.writeJsonRooms(Hotel.getRoomGenericList().getList(),"files/room.json");

        ///hotelPrueba.setRoomsList(DataFile.readRoomJson("files/room.json"));

      //  Menu menu = new Menu();
       // menu.initiate();
      //  dataFile.writeJsonBookings(Hotel.getReservationGenericList().getList(),"files/booking.json");


    }

}
