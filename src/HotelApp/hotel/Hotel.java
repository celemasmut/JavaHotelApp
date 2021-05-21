package HotelApp.hotel;
import java.util.Scanner;

import HotelApp.hotel.bedrooms.Room;
import HotelApp.hotel.users.Admin;
import HotelApp.hotel.users.Passenger;
import HotelApp.hotel.users.Receptionist;
import HotelApp.hotel.users.User;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private List <User> usersList;;
    private List <Room> roomsList;
    private List<Reservation> reservationsList;

    public Hotel() {
        this.usersList = new ArrayList<>();
        this.roomsList = new ArrayList<>();
        this.reservationsList = new ArrayList<>();
    }

    public void login ()
    {
         int posInList;
         Scanner keyboard = new Scanner (System.in);
         String inputUserName;
         String inputPassword;

         System.out.println("Ingrese su nombre de usuario:");
         inputUserName=keyboard.nextLine();
         inputUserName.toLowerCase();
         System.out.println("Ingrese su contraseña:");
         inputPassword=keyboard.nextLine();
         posInList=searchUserInList(inputUserName,inputPassword);
         if (posInList>-1)
         {
             if (usersList.get(posInList) instanceof Passenger)
             {
                 usersList.get(posInList).menu();
             }
             else if (usersList.get(posInList) instanceof Receptionist)
             {
                 usersList.get(posInList).menu();
             }
             if (usersList.get(posInList) instanceof Admin)
             {
                 usersList.get(posInList).menu();
             }
         }
         else
             System.out.println("Algo fue ingresado de manera erronea");





    }
    public int searchUserInList(String inputUserName,String inputPassword)
    {
        int posInList=-1;
        for (User aux:usersList)
        {
            if (inputUserName.equalsIgnoreCase(aux.getLoginName()) && inputPassword.equals(aux.getPassword()))
            {
                posInList=usersList.indexOf(aux);

            }
        }
        return posInList;
    }
}
