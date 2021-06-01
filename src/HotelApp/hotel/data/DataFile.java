package HotelApp.hotel.data;

import HotelApp.hotel.Hotel;
import HotelApp.hotel.users.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataFile {

    public static void hotelToJson(Hotel hotel){
       try{
            File file = new File("users_file.json");
            if(!file.exists()){
                file.createNewFile();
            }
            ObjectMapper mapper = new ObjectMapper();
                mapper.writeValue(file,hotel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //por ahora la idea es levantar el hotel

  /*  public static List<User> readUserFile(){
        List<User> users = new ArrayList<>();
        try{
            File file = new File("users_file.json");
            if(file.exists()) {
                ObjectMapper mapper = new ObjectMapper();
                users = mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, User.class));
            }
            } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }*/
}
