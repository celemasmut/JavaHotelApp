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
        DataFile dataFile = new DataFile();
        Hotel hotelPrueba= new Hotel();


    /*   SingleRoom sroom1 = new SingleRoom( State.AVAILABLE,1,500);
        SingleRoom sroom2 = new SingleRoom( State.OCCUPIED,1,500);
        SingleRoom sroom3 = new SingleRoom( State.AVAILABLE,1,500);


        DoubleRoom droom = new DoubleRoom(State.AVAILABLE, 2, 750);
        DoubleRoom droom2 = new DoubleRoom(State.IN_MAINTENANCE, 2, 750);
        DoubleRoom droom3 = new DoubleRoom(State.CLEANING, 2, 750);


        FamilyRoom fmroom = new FamilyRoom(State.AVAILABLE,3, 850);
        FamilyRoom fmroom2 = new FamilyRoom(State.IN_MAINTENANCE,3, 850);
        FamilyRoom fmroom3 = new FamilyRoom(State.AVAILABLE,3, 850);


        KingRoom knroom = new KingRoom(State.AVAILABLE,1,1500);
        KingRoom knroom2 = new KingRoom(State.CLEANING,1,1500);
        KingRoom knroom3 = new KingRoom(State.AVAILABLE,1,1500);


        Hotel.addRoomToList(sroom1);
        Hotel.addRoomToList(sroom2);
        Hotel.addRoomToList(sroom3);

        Hotel.addRoomToList(droom);
        Hotel.addRoomToList(droom2);
        Hotel.addRoomToList(droom3);

        Hotel.addRoomToList(fmroom);
        Hotel.addRoomToList(fmroom2);
        Hotel.addRoomToList(fmroom3);

        Hotel.addRoomToList(knroom);
        Hotel.addRoomToList(knroom2);
        Hotel.addRoomToList(knroom3);
        SaveTypeRoom saveRooms = new SaveTypeRoom();
       // saveRooms.addRoomsToList(Hotel.getRoomGenericList().getList());
        //dataFile.writeJsonRooms(saveRooms,"files/room.json");
     saveRooms = dataFile.readRoomJson("files/room.json");
     Hotel.addRoomsToList(saveRooms);

     Hotel.showListOfRoom();*/



     // dataFile.writeJsonRooms(Hotel.getRoomGenericList().getList(),"files/room.json");

     Menu menu = new Menu();
        menu.initiate();
      // dataFile.writeJsonBookings(Hotel.getReservationGenericList().getList(),"files/booking.json");


    }

}
