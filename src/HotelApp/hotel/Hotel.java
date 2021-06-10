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

import java.util.ArrayList;
import java.util.List;

public class Hotel {

    private static GenericList <User> userGenericList;
    private static GenericList <Room> roomGenericList;
    private static GenericList <Reservation> reservationGenericList;

    public Hotel() {

        this.userGenericList=new GenericList<>();
        this.roomGenericList=new GenericList<>();
        this.reservationGenericList=new GenericList<>();

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

    protected static boolean changeStateOfRoom(Passenger passengerToRoom, Room roomToReserve, State state) {
        int posInList;

        posInList = roomGenericList.getList().indexOf(roomToReserve);
        roomGenericList.getList().get(posInList).setStateRoom(state);
        roomGenericList.getList().get(posInList).setOccupant(passengerToRoom);
        return true;
    }

    protected static boolean changeStateOfRoomXNumber(int roomNumber, State state)
    {
        roomGenericList.getList().get(roomNumber).setStateRoom(state);
        return true;
    }

    protected static Passenger searchPassengerInList(String dni) throws UserDoesNotExistException {
        for (User userAux : userGenericList.getList()) {
            if (userAux instanceof Passenger) {
                if (((Passenger) userAux).getDni().equals(dni)){
                    return (Passenger) userAux;
                }else{
                    throw new UserDoesNotExistException("The user does not exist, please register");
                }
            }
        }
        return null;
    }

    protected static Reservation searchReservation(String dniPassenger) throws ReservationNotFoundException, UserDoesNotExistException {
        if(reservationGenericList.getList().size() > 0) {
            for (Reservation reservation : reservationGenericList.getList()) {
                if (dniPassenger.equals(reservation.getDniPassenger())) {
                    return reservation;
                }else{
                    throw new UserDoesNotExistException("The passenger does not exist");
                }
            }
        }else{
            throw new ReservationNotFoundException();
        }
        return null;
    }

    protected static void showListOfRoom() {
        for (Room roomAux : roomGenericList.getList()) {
            System.out.println(roomAux.toString());
        }
    }

    protected static void showListOfRoomXState(State state){
        for (Room roomAux : roomGenericList.getList()) {
            if (roomAux.getStateRoom().equals(state)) {
                System.out.println(roomAux);
            }
        }
    }

    protected static List<Reservation> getPassengerReservations(String dni) throws ReservationNotFoundException{
        List<Reservation> passengerReservations = new ArrayList<>();
        if (reservationGenericList.getList().size() > 0) {
            for (Reservation reserv : reservationGenericList.getList()) {
                if (reserv.getDniPassenger().equals(dni)) {
                    if (reserv.getStatus() != Status.CANCELLED)
                        passengerReservations.add(reserv);
                }
            }
        }else{
            throw new ReservationNotFoundException();
        }
        return passengerReservations;
    }

    protected static List<Reservation> getStatusReservations(List<Reservation> passengerReservList, Status status) {
        List<Reservation> statusReservation = new ArrayList<>();
        if (passengerReservList.size() > 0) {
            for (Reservation reserv : passengerReservList) {
                if (reserv.getStatus().equals(status)) {
                    statusReservation.add(reserv);
                }
            }
        }
        return statusReservation;
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

    protected static boolean deleteReservationInList(Reservation reservationToDelete) {
        if (reservationToDelete != null) {
            int posInList = reservationGenericList.getList().indexOf(reservationToDelete);
            reservationGenericList.getList().get(posInList).setStatus(Status.COMPLETED);
            return true;
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
        for (Reservation reservationAux : reservationGenericList.getList()) {
            if (dniPassenger.equalsIgnoreCase(reservationAux.getDniPassenger())) {
                return reservationAux;
            }
        }
        return null;
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


}
