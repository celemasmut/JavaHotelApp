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
    private static List <Passenger> passengerList;;
    private static List <Receptionist> receptionistsList;
    private static List <Admin> adminsList;
    private static List <Room> roomsList;
    private static List<Reservation> reservationsList;

    public Hotel() {
        this.usersList = new ArrayList<>();
        this.roomsList = new ArrayList<>();
        this.reservationsList = new ArrayList<>();
        this.passengerList= new ArrayList<>();
        this.adminsList = new ArrayList<>();
        this.receptionistsList=new ArrayList<>();
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

    public static void setUsersList(List<User> usersList) {
        Hotel.usersList = usersList;
    }

    public static List<Passenger> getPassengerList() {
        return passengerList;
    }

    public static void setPassengerList(List<Passenger> passengerList) {
        Hotel.passengerList = passengerList;
    }

    public static List<Receptionist> getReceptionistsList() {
        return receptionistsList;
    }

    public static void setReceptionistsList(List<Receptionist> receptionistsList) {
        Hotel.receptionistsList = receptionistsList;
    }

    public static List<Admin> getAdminsList() {
        return adminsList;
    }

    public static void setAdminsList(List<Admin> adminsList) {
        Hotel.adminsList = adminsList;
    }

    public static void setRoomsList(List<Room> roomsList) {
        Hotel.roomsList = roomsList;
    }

    public static void setReservationsList(List<Reservation> reservationsList) {
        Hotel.reservationsList = reservationsList;
    }

    // esto sera por ahora publico solo por que no tenemos los archivos para levantarlos. luego sera protected.
    public static boolean addUserToList (User userToAdd){
        usersList.add(userToAdd);
        return true;
    }
    public static boolean addPassenger (Passenger passengerToAdd)
    {
        return passengerList.add(passengerToAdd);

    }
    public static boolean addAdmin(Admin adminToAdd)
    {
        return adminsList.add(adminToAdd);
    }
    public static boolean addRecepcionists (Receptionist receptionistToAdd)
    {
        return receptionistsList.add(receptionistToAdd);
    }
    protected static boolean changeStateOfRoom(Passenger passengerToRoom,Room roomToReserve, State state)
    {
        int posInList;



            posInList=roomsList.indexOf(roomToReserve);
            roomsList.get(posInList).setStateRoom(state);
            roomsList.get(posInList).setOccupant(passengerToRoom);
            return true;


    }

    protected static Passenger searchPassengerInList (String dni)
    {
       int posToReturn=-1;
       for (User user:usersList)
       {
           if (user instanceof Passenger)
           {
               if (((Passenger) user).getDni().equalsIgnoreCase(dni))
               {
                   posToReturn=usersList.indexOf(user);
                   return (Passenger) usersList.get(posToReturn);
               }
           }
       }
       return null;
    }
    protected static Reservation searchReservation (String dniPassenger)
    {
        for (Reservation reservation:reservationsList)
        {
            if (dniPassenger.equalsIgnoreCase(reservation.getDniPassenger()))
            {
                return reservation;
            }
        }
        return null;
    }
    protected static void showListOfRoom()
    {
        for (Room roomAux:roomsList)
        {
            System.out.println(roomAux.toString());
        }
    }

    protected static List<Reservation> getPassengerReservations(String dni){
        List<Reservation> passengerReservations = new ArrayList<>();
        if(reservationsList.size() > 0) {
            for (Reservation reserv : reservationsList) {
                if (reserv.getDniPassenger().equals(dni)) {
                    if (reserv.getStatus() != Status.CANCELLED)
                        passengerReservations.add(reserv);
                }
            }
        }
        return passengerReservations;
    }

    protected static List<Reservation> getStatusReservations(List<Reservation> passengerReservList, Status status){
        List<Reservation> statusReservation = new ArrayList<>();
        if(passengerReservList.size() > 0) {
            for (Reservation reserv : passengerReservList) {
                if (reserv.getStatus().equals(status)) {
                    statusReservation.add(reserv);
                }
            }
        }
        return  statusReservation;
    }

    protected static boolean toCancelReservation(Reservation canceledReservation){
        if(reservationsList.size() > 0) {
            for (Reservation reservation : reservationsList) {
                if (reservation.equals(canceledReservation)) {
                    reservation.setStatus(Status.CANCELLED);
                    return true;
                }
            }
        }
        return false;
    }
    protected static boolean deleteReservationInList(Reservation reservationToDelete)
    {
        if (reservationToDelete!=null){
        int posInList=reservationsList.indexOf(reservationToDelete);
        reservationsList.get(posInList).setStatus(Status.COMPLETED);
        return true;
        }
        return false;
    }
    protected static void showReservation()
    {
        for (Reservation reservation:reservationsList)
        {
            System.out.println(reservation.toString());
        }
    }
    protected static Reservation searchReservationInList (String dniPassenger)
    {
        for (Reservation reservationAux:reservationsList)
        {
            if (dniPassenger.equalsIgnoreCase(reservationAux.getDniPassenger()))
            {
                return reservationAux;
            }
        }
        return null;
    }
    protected static void showPassenger()
    {
        for (Passenger passenger:passengerList)
        {
            System.out.println(passenger.toString());
        }
    }

    protected static void showAdmins()
    {
        for (Admin admin:adminsList)
        {
            System.out.println(admin.toString());
        }
    }

    protected static void showReceptionist()
    {
        for (Receptionist receptionist:receptionistsList)
        {
            System.out.println(receptionist.toString());
        }
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
