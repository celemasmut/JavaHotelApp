package HotelApp.hotel;
import java.util.Scanner;

import HotelApp.hotel.bedrooms.Room;
import HotelApp.hotel.bedrooms.State;
import HotelApp.hotel.users.Admin;
import HotelApp.hotel.users.Passenger;
import HotelApp.hotel.users.Receptionist;
import HotelApp.hotel.users.User;


import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private static List <User> usersList;;
    private static List <Room> roomsList;
    private static List<Reservation> reservationsList;

    public Hotel() {
        this.usersList = new ArrayList<>();
        this.roomsList = new ArrayList<>();
        this.reservationsList = new ArrayList<>();
    }

    public static List<User> getUsersList() {
        return usersList;
    }

    public static List<Room> getRoomsList() {
        return roomsList;
    }

    public static List<Reservation> getReservationsList() {
        return reservationsList;
    }

    protected static void addReservation( Reservation reservation){
        reservationsList.add(reservation);
    }

    public static void addRoomToList(Room room){
        roomsList.add(room);
    }


// esto sera por ahora publico solo por que no tenemos los archivos para levantarlos. luego sera protected.
    public static boolean addUserToList (User userToAdd){
        usersList.add(userToAdd);
        return true;
    }
    private static boolean changeStateOfRoom(Reservation reservation)
    {
        int posInList;
        int posInListOfPassenger;


        posInListOfPassenger=searchPassengerInList(reservation.getDniPassenger());
        if (posInListOfPassenger>-1)
        {
            posInList=roomsList.indexOf(reservation.getRoomToReserve());
            roomsList.get(posInList).setStateRoom(State.OCCUPIED);
            roomsList.get(posInList).setOccupant((Passenger) usersList.get(posInListOfPassenger));
            return true;

        }
        return false;
    }
    protected static int searchPassengerInList (String dni)
    {
       int posToReturn=-1;
       for (User user:usersList)
       {
           if (user instanceof Passenger)
           {
               if (((Passenger) user).getDni().equalsIgnoreCase(dni))
               {
                   posToReturn=usersList.indexOf(user);
                   return posToReturn;
               }
           }
       }
       return posToReturn;
    }
    /*protected static boolean existenceInTheList(User userToSearch)
    {
        for (User aux:usersList)
        {
            if (userToSearch.equals(aux))
                return true;
        }
        return false;
    }

    protected static boolean changeStateRoom(Passenger passengerToAdd)
    {

    }*/
}
