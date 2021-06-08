package HotelApp.datafile;

import HotelApp.model.bedrooms.Room;
import HotelApp.model.reservation.Reservation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface Fileable {
     void writeJsonBookings(List<Reservation> reservationList, String nameFile);

      void writeJsonRooms(List<Room> rooms, String fileName);


     List<Reservation> readReservationJson(String fileName);


     List<Room> readRoomJson(String fileName);

     void writeInfo (SaveInfoUsers infoToSave, String fileName);

      SaveInfoUsers readInfo(String fileName);

}

