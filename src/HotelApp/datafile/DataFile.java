package HotelApp.datafile;

import HotelApp.model.reservation.Reservation;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataFile implements Fileable  {
    public DataFile() {
    }
    @Override
    public  void writeJsonBookings(List<Reservation> reservationList,String nameFile){
       if(reservationList != null) {
           try {
               File file = new File(nameFile);
               if (!file.exists()) {
                   file.createNewFile();
               }
               ObjectMapper mapper = new ObjectMapper();
               mapper.writerWithDefaultPrettyPrinter().writeValue(file, reservationList);
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
    }
    @Override
    public  void writeJsonRooms(SaveTypeRoom infoRooms, String fileName){
        if(infoRooms != null) {
            try {
                File file = new File((fileName));
                if (!file.exists()) {
                    file.createNewFile();
                }
                ObjectMapper mapper = new ObjectMapper();
                mapper.writerWithDefaultPrettyPrinter().writeValue(file, infoRooms);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public  List<Reservation> readReservationJson(String fileName) {
        List<Reservation> reservationList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try{
            File file = new File(fileName);
            if(file.exists()){
                reservationList = mapper.readValue(file,mapper.getTypeFactory().constructCollectionType(List.class,Reservation.class));
            }else{
                file = new File("files/backUpBooking.json");
                reservationList = mapper.readValue(file,mapper.getTypeFactory().constructCollectionType(List.class,Reservation.class));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return reservationList;
    }
    @Override
    public  SaveTypeRoom readRoomJson(String fileName) {
        SaveTypeRoom infoRooms = new SaveTypeRoom();
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File(fileName);
            if(file.exists()){
                infoRooms = mapper.readValue(file,SaveTypeRoom.class);
            }else{
                file = new File("files/backUpRoom.json");
                infoRooms = mapper.readValue(file, SaveTypeRoom.class);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return infoRooms;
    }
    @Override
    public void writeInfo (SaveInfoUsers infoToSave, String fileName){
        try{
            File file = new File((fileName));
            if(!file.exists()){
                file.createNewFile();
            }
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(file,infoToSave);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public  SaveInfoUsers readInfo(String fileName) {
        SaveInfoUsers infoToRead = new SaveInfoUsers();
        ObjectMapper mapper= new ObjectMapper();
        try
        {
            File file =new File(fileName);
            if (file.exists()) {
                infoToRead=mapper.readValue(file,SaveInfoUsers.class);
            }else{
                file = new File("files/backUpUsers.json");
                infoToRead=mapper.readValue(file,SaveInfoUsers.class);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return infoToRead;
    }
}

