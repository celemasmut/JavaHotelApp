package HotelApp.hotel;

import HotelApp.model.bedrooms.Room;
import HotelApp.model.reservation.Reservation;
import HotelApp.model.users.Admin;
import HotelApp.model.users.Passenger;
import HotelApp.model.users.Receptionist;
import HotelApp.model.users.User;
import HotelApp.util.State;
import HotelApp.util.Status;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private static List <User> userList;
    private static List <Passenger> passengerList;;
    private static List <Receptionist> receptionistsList;
    private static List <Admin> adminsList;
    private static List <Room> roomsList;
    private static List<Reservation> reservationsList;

    public Hotel() {
        this.roomsList = new ArrayList<>();
        this.reservationsList = new ArrayList<>();
        this.passengerList= new ArrayList<>();
        this.adminsList = new ArrayList<>();
        this.receptionistsList=new ArrayList<>();
        this.userList=new ArrayList<>();
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

    public static List<User> getUserList() {
        return userList;
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

    public static boolean addPassenger (Passenger passengerToAdd)
    {
        userList.add(passengerToAdd);
        return passengerList.add(passengerToAdd);

    }
    public static boolean addAdmin(Admin adminToAdd)
    {
        userList.add(adminToAdd);
        return adminsList.add(adminToAdd);
    }
    public static boolean addRecepcionists (Receptionist receptionistToAdd)
    {
        userList.add(receptionistToAdd);
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
       return (passengerList
               .stream()
               .filter(passenger -> passenger.getDni().equals(dni))
               .findFirst()
               .get());
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
    protected static void showUsers()
    {
        for (User receptionist:userList)
        {
            System.out.println(receptionist.toString());
        }
    }
    protected  static void addToList(List<Admin> adminNewList,List<Receptionist> receptionistsNewList,List<Passenger> passengerNewList)
    {
        for (Admin adminToAdd:adminNewList)
        {
            userList.add(adminToAdd);
            adminsList.add(adminToAdd);
        }
        for (Passenger passengerToAdd:passengerNewList)
        {
            userList.add(passengerToAdd);
            passengerList.add(passengerToAdd);
        }
        for (Receptionist receptionistToAdd:receptionistsNewList)
        {
            userList.add(receptionistToAdd);
            receptionistsList.add(receptionistToAdd);
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
