package HotelApp.hotel.datafile;
import HotelApp.hotel.bedrooms.Room;
import HotelApp.hotel.users.Admin;
import HotelApp.hotel.users.Passenger;
import HotelApp.hotel.users.Receptionist;
import HotelApp.hotel.users.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataFile  {
    public DataFile() {
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
    public static void writeJsonRecepcionist(List<Receptionist> user,String fileName)
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
