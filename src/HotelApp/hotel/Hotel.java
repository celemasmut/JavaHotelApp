package HotelApp.hotel;
import java.io.Serializable;
import java.util.Scanner;

import HotelApp.hotel.bedrooms.Room;
import HotelApp.hotel.bedrooms.State;
import HotelApp.hotel.data.DataFile;
import HotelApp.hotel.users.*;


import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

public class Hotel implements Serializable {
    private static Employee employees;
    private static List<Passenger>passengerList;
    private static List <Room> roomsList;
    private static List<Reservation> reservationsList;

    public Hotel() {
        this.employees = new Employee();
        this.passengerList = new ArrayList<>();
        this.roomsList = new ArrayList<>();
        this.reservationsList = new ArrayList<>();
    }



    public static List<Room> getRoomsList() {
        return roomsList;
    }

    public static Employee getEmployees() {
        return employees;
    }

    public static void setEmployees(Employee employees) {
        Hotel.employees = employees;
    }

    public static List<Passenger> getPassengerList() {
        return passengerList;
    }

    public static void setPassengerList(List<Passenger> passengerList) {
        Hotel.passengerList = passengerList;
    }

    public static void setRoomsList(List<Room> roomsList) {
        Hotel.roomsList = roomsList;
    }

    public static void setReservationsList(List<Reservation> reservationsList) {
        Hotel.reservationsList = reservationsList;
    }

    public static List<Reservation> getReservationsList() {
        return reservationsList;
    }

    public static void addReservation( Reservation reservation){
        reservationsList.add(reservation);
    }

    public static void addRoomToList(Room room){
        roomsList.add(room);
    }


// esto sera por ahora publico solo por que no tenemos los archivos para levantarlos. luego sera protected.
    public static boolean addPassengerToList(Passenger passenger){
        passengerList.add(passenger);
        return true;
    }

    public static void addReceptionistToList(Receptionist receptionist){
        employees.addReceptionistList(receptionist);
    }

    public static void addAdminToList(Admin admin){
        employees.addAdmins(admin);
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
       for (Passenger user:passengerList)
       {
           if (user instanceof Passenger)
           {
               if (user.getDni().equalsIgnoreCase(dni))
               {
                   return user;
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
       roomsList.forEach(room -> System.out.println(room.toString()));
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

    protected static boolean changeStatusReservation(Reservation reservation,Status status)
    {
        if (reservation!=null){
            int posInList=reservationsList.indexOf(reservation);
            reservationsList.get(posInList).setStatus(status);
            return true;
        }
        return false;
    }
    protected static void showReservation()
    {
        reservationsList.forEach(reservation -> System.out.println(reservation.toString()));
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

    protected static void backUp (){
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
