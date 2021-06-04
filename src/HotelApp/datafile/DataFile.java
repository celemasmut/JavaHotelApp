package HotelApp.datafile;
import HotelApp.model.reservation.Reservation;
import HotelApp.model.bedrooms.Room;
import HotelApp.model.users.Admin;
import HotelApp.model.users.Passenger;
import HotelApp.model.users.Receptionist;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataFile  {
    public DataFile() {
    }

    public static void writeJsonBookings(List<Reservation> reservationList,String nameFile){
        try{
            File file = new File(nameFile);
            if(!file.exists()){
                file.createNewFile();
            }
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
            mapper.writeValue(file,reservationList);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void writeJsonPassenger(List<Passenger> user, String fileName)
    {
        try{
            File file = new File(fileName);
            if(!file.exists()){
                file.createNewFile();
            }
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(file,user);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public static void writeJsonReceptionist(List<Receptionist> user,String fileName)
    {
        try{
            File file = new File(fileName);
            if(!file.exists()){
                file.createNewFile();
            }
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(file,user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void writeJsonAdmin(List<Admin> user,String fileName)
    {
        try{
            File file = new File(fileName);
            if(!file.exists()){
                file.createNewFile();
            }
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(file,user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeJsonRooms(List<Room> rooms, String fileName){
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

    public static List<Reservation> readReservationJson(String fileName){
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

    public static List<Room> readRoomJson(String fileName){
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

        roomList.forEach(r->System.out.println(r.toString()));
        return roomList;
    }
    public static List <Passenger> readPassengerJson(String nameFile)
    {
        List<Passenger> userList=new ArrayList<>();
        ObjectMapper mapper= new ObjectMapper();
        try{
            File file =new File(nameFile);
            if (file.exists())
            {
                userList=mapper.readValue(file,mapper.getTypeFactory().constructCollectionType(List.class,Passenger.class));
            }
        }catch(IOException e)
        {
            e.printStackTrace();
        }

        return userList;
    }

    public static List <Receptionist> readReceptionistJson(String nameFile)
    {
        List<Receptionist> recepList=new ArrayList<>();
        try{
            File file =new File(nameFile);
            if (file.exists())
            {
                ObjectMapper mapper= new ObjectMapper();
                recepList=mapper.readValue(file,mapper.getTypeFactory().constructCollectionType(List.class,Receptionist.class));
            }
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        return recepList;
    }
    public static List <Admin> readAdminJson(String nameFile)
    {
        List<Admin> adminList=new ArrayList<>();
        ObjectMapper mapper= new ObjectMapper();
        try{
            File file =new File(nameFile);
            if (file.exists())
            {
                adminList=mapper.readValue(file,mapper.getTypeFactory().constructCollectionType(List.class,Admin.class));
            }
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        return adminList;
    }
}
