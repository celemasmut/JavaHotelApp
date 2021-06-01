package HotelApp.hotel.data;

import HotelApp.hotel.Hotel;
import HotelApp.hotel.users.Employee;
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
    public void employeeToJson(Employee user, String fileName){
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



    public  List<T> readFile(T o,String nameFile){
        List<T> passengerList = new ArrayList<>();
        try{
            File file = new File(nameFile);
            if(file.exists()) {
                ObjectMapper mapper = new ObjectMapper();
                passengerList = mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, o.getClass()));
            }
            } catch (IOException e) {
            e.printStackTrace();
        }
        return passengerList;
    }
}
