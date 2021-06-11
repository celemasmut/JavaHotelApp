package HotelApp.hotel;

import HotelApp.datafile.SaveInfoUsers;
import HotelApp.datafile.SaveTypeRoom;
import HotelApp.exception.ReservationNotFoundException;
import HotelApp.exception.UserDoesNotExistException;
import HotelApp.model.bedrooms.*;
import HotelApp.model.reservation.Reservation;
import HotelApp.model.users.Admin;
import HotelApp.model.users.Passenger;
import HotelApp.model.users.Receptionist;
import HotelApp.model.users.User;
import HotelApp.util.GenericList;

import HotelApp.util.State;
import HotelApp.util.Status;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Hotel {

    private static GenericList <User> userGenericList;
    private static GenericList <Room> roomGenericList;
    private static GenericList <Reservation> reservationGenericList;

    public Hotel() {

        this.userGenericList=new GenericList<>();
        this.roomGenericList=new GenericList<>();
        this.reservationGenericList=new GenericList<>();

    }

    public void menu() {
        Menu menu = new Menu();
        menu.initiate();
    }

    public static GenericList<User> getUserGenericList() {
        return userGenericList;
    }


    public static GenericList<Room> getRoomGenericList() {
        return roomGenericList;
    }

    public static void setRoom(List <Room> roomGenericList) {
        Hotel.roomGenericList.setList((roomGenericList));
    }

    public static GenericList<Reservation> getReservationGenericList() {
        return reservationGenericList;
    }

    public static void setReservationGenericList(List<Reservation> reservationGenericList) {
        Hotel.reservationGenericList.setList(reservationGenericList);
    }

    protected static boolean addUser(User userToAdd)
    {
       return  userGenericList.addToList(userToAdd);
    }
    protected static boolean addReservation(Reservation reservationToAdd)
    {
        return reservationGenericList.addToList(reservationToAdd);
    }
    public static void addRoomToList(Room room){
        roomGenericList.addToList(room);
    }
    protected static Room searchRoomForNumber(int roomNumber){
        return roomGenericList.getList().stream().
                filter(rn -> rn.getRoomNumber() == roomNumber).
                findFirst().
                orElse(null);
    }

    protected static boolean changeStateOfRoomXNumber(int roomNumber, State state)
    {
        roomGenericList.getList().get(roomNumber).setStateRoom(state);
        return true;
    }

    protected static Passenger searchPassengerInList(String dni) throws UserDoesNotExistException {

        return (Passenger) userGenericList.getList().stream().
                filter(user -> user instanceof Passenger).
                filter(user -> ((Passenger) user).getDni().equals(dni)).
                findFirst().
                get();
    }

    protected static List<Reservation> searchReservation(String dniPassenger) throws ReservationNotFoundException {
        return reservationGenericList.getList().stream().
                filter(reservation -> reservation.getDniPassenger().equals(dniPassenger)).
                collect(Collectors.toList());
    }

    protected static void showListOfRoom() {
        roomGenericList.getList().forEach(room -> System.out.println(room));
    }

    protected static void showListOfRoomXState(State state){
        roomGenericList.getList().stream().
                filter(room -> room.getStateRoom().equals(state)).
                forEach(System.out::println);
    }

    protected static List<Reservation> getPassengerReservations(String dni) throws ReservationNotFoundException{
       return reservationGenericList.getList().stream().
               filter(reservation -> reservation.getDniPassenger().equals(dni)).
               filter(reservation -> reservation.getStatus() != Status.CANCELLED).
               collect(Collectors.toList());
    }

    protected static List<Reservation> getStatusReservations(List<Reservation> passengerReservList, Status status) {
        return passengerReservList.stream().
                filter(reservation -> reservation.getStatus().equals(status)).
                collect(Collectors.toList());
    }

    protected static boolean toCancelReservation(Reservation canceledReservation) {
        if (reservationGenericList.getList().size() > 0) {
            for (Reservation reservation : reservationGenericList.getList()) {
                if (reservation.equals(canceledReservation)) {
                    reservation.setStatus(Status.CANCELLED);
                    return true;
                }
            }
        }
        return false;
    }


    protected static void showReservation() throws ReservationNotFoundException {
        if(reservationGenericList.getList().size() > 0) {
            for (Reservation reservation : reservationGenericList.getList()) {
                System.out.println(reservation.toString());
            }
        }else{
            throw new ReservationNotFoundException();
        }
    }

    protected static Reservation searchReservationInList(String dniPassenger) {
        return reservationGenericList.getList().stream().
                filter(reservation -> reservation.getDniPassenger().equals(dniPassenger) && reservation.getStatus() == Status.CONFIRMED).
                findFirst().get();
    }
    protected static boolean coincidenceInFileNumberInList(int newFileNumber)
    {
        for (User userAux: getUserGenericList().getList())
        {
            if (userAux instanceof Receptionist)
            {
                if (newFileNumber==((Receptionist) userAux).getFileNumber())
                {
                    return true;
                }
            }
        }
        return false;
    }
    protected static void showUsers()throws UserDoesNotExistException {
        int i = 0;
        if(userGenericList.getList().size() > 0) {
            for (User userAux : userGenericList.getList()) {
                i++;
                System.out.println(userAux.toString());
            }
            System.out.println("Total users :" + i);
        }else{
            throw new UserDoesNotExistException("There is not user to show");
        }
    }

    protected static void addUsersToList(SaveInfoUsers infoToAdd) {
        for (Admin adminToAdd : infoToAdd.getListAdmin()) {
            userGenericList.addToList(adminToAdd);

        }
        for (Passenger passengerToAdd : infoToAdd.getListPassenger()) {
            userGenericList.addToList(passengerToAdd);

        }
        for (Receptionist receptionistToAdd : infoToAdd.getListRecepcionist()) {
            userGenericList.addToList(receptionistToAdd);

        }

    }

    protected static void addRoomsToList(SaveTypeRoom roomList){
        for(SingleRoom room : roomList.getSingleRoomList()){
            roomGenericList.addToList(room);
        }
        for(DoubleRoom room : roomList.getDoubleRoomList()){
            roomGenericList.addToList(room);
        }
        for(FamilyRoom room : roomList.getFamilyRoomList()){
            roomGenericList.addToList(room);
        }
        for(KingRoom room : roomList.getKingRoomList()){
            roomGenericList.addToList(room);
        }
    }

    protected static void sortReservationByDates(){
        Collections.sort(reservationGenericList.getList());
    }

    protected static int countBookings(){
        int i=0;
        for(Reservation r : reservationGenericList.getList()){
            i++;
        }
        return i;
    }


}
