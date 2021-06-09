package HotelApp.datafile;

import HotelApp.model.bedrooms.Room;
import HotelApp.model.reservation.Reservation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataFile implements Fileable  {
    public DataFile() {
    }
    @Override
    public  void writeJsonBookings(List<Reservation> reservationList,String nameFile){
        try{
            File file = new File(nameFile);
            if(!file.exists()){
                file.createNewFile();
            }
            ObjectMapper mapper = new ObjectMapper();
           // mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
            mapper.writeValue(file,reservationList);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public  void writeJsonRooms(List<Room> rooms, String fileName){
        try{
            File file = new File((fileName));
            if(!file.exists()){
                file.createNewFile();
            }
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(file,rooms);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public  List<Reservation> readReservationJson(String fileName){
        List<Reservation> reservationList = new ArrayList<>();
        try{
            File file = new File(fileName);
            if(file.exists()){
                ObjectMapper mapper = new ObjectMapper();
                reservationList = mapper.readValue(file,mapper.getTypeFactory().constructCollectionType(List.class,Reservation.class));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return reservationList;
    }
    @Override
    public  List<Room> readRoomJson(String fileName){
        List<Room> roomList = new ArrayList<>();
        try {
            File file = new File(fileName);
            if(file.exists()){
                ObjectMapper mapper = new ObjectMapper();
                roomList = mapper.readValue(file,mapper.getTypeFactory().constructCollectionType(List.class,Room.class));
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return roomList;
    }
    @Override
    public void writeInfo (SaveInfoUsers infoToSave, String fileName)
    {
        try{
            File file = new File((fileName));
            if(!file.exists()){
                file.createNewFile();
            }
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(file,infoToSave);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public  SaveInfoUsers readInfo(String fileName)
    {
        SaveInfoUsers infoToRead = new SaveInfoUsers();
        ObjectMapper mapper= new ObjectMapper();
        try
        {
            File file =new File(fileName);
            if (file.exists())
            {
                infoToRead=mapper.readValue(file,SaveInfoUsers.class);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return infoToRead;
    }
}

