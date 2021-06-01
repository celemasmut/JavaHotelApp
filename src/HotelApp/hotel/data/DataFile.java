package HotelApp.hotel.data;

import HotelApp.hotel.Hotel;
import HotelApp.hotel.users.Passenger;
import HotelApp.hotel.users.User;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataFile <T>{

    public DataFile() {
    }

    //hacerlo generico
    public void hotelToJson(List<T> user,String fileName){
       try{
            File file = new File(fileName);
            if(!file.exists()){
                file.createNewFile();
            }
            ObjectMapper mapper = new ObjectMapper();
           mapper.writerWithDefaultPrettyPrinter().writeValue(file,user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static List<User> readUserFile(){
        List<User> users = new ArrayList<>();
        try{
            File file = new File("passenger.json");
            if(file.exists()) {
                ObjectMapper mapper = new ObjectMapper();
                users = mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, Passenger.class));
            }
            } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
}
