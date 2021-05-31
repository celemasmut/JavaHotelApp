package HotelApp.hotel.data;

import HotelApp.hotel.users.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataFile {

    public static void userToJson(List<User> users){
       try{
            File file = new File("users_file.json");
            if(!file.exists()){
                file.createNewFile();
            }
            ObjectMapper mapper = new ObjectMapper();
                mapper.writeValue(file,users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<User> readUserFile(){
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
    }
}
