package HotelApp.datafile;

import HotelApp.model.bedrooms.Room;
import HotelApp.model.reservation.Reservation;
import java.io.IOException;
import java.util.List;

public interface Fileable {
     void writeJsonBookings(List<Reservation> reservationList, String nameFile)throws IOException;

      void writeJsonRooms(List<Room> rooms, String fileName)throws IOException;


     List<Reservation> readReservationJson(String fileName)throws IOException;


     List<Room> readRoomJson(String fileName)throws IOException;

     void writeInfo (SaveInfoUsers infoToSave, String fileName)throws IOException;

      SaveInfoUsers readInfo(String fileName)throws IOException;

}

