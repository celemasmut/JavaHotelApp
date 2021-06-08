package HotelApp.hotel;

import HotelApp.datafile.SaveInfoUsers;
import HotelApp.model.bedrooms.Room;
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

    public static void setUserGenericList(GenericList<User> userGenericList) {
        Hotel.userGenericList = userGenericList;
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

    public static void setReservationGenericList(GenericList<Reservation> reservationGenericList) {
        Hotel.reservationGenericList = reservationGenericList;
    }

    protected static boolean addUser(User userToAdd)
    {
       return  userGenericList.addToList(userToAdd);
    }
    protected static boolean addReservation(Reservation reservationToAdd)
    {
        return reservationGenericList.addToList(reservationToAdd);
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

    protected static Passenger searchPassengerInList(String dni) {
        for (User userAux : userGenericList.getList()) {
            if (userAux instanceof Passenger) {
                if (((Passenger) userAux).getDni().equals(dni))
                    return (Passenger) userAux;
            }
        }
        return null;
    }

    protected static Reservation searchReservation(String dniPassenger) {
        for (Reservation reservation : reservationGenericList.getList()) {
            if (dniPassenger.equalsIgnoreCase(reservation.getDniPassenger())) {
                return reservation;
            }
        }
        return null;
    }

    protected static void showListOfRoom() {
        for (Room roomAux : roomGenericList.getList()) {
            System.out.println(roomAux.toString());
        }
    }

    protected static void showListOfRoomXState() {
        for (Room roomAux : roomGenericList.getList()) {
            if (roomAux.getStateRoom().equals(State.FREE)) {
                System.out.println("Free:");
                System.out.println(roomAux.toString());
            }
        }

        for (Room roomAux : roomGenericList.getList()) {
            if (roomAux.getStateRoom().equals(State.RESERVED)) {
                System.out.println(roomAux.toString());
            }
        }

        for (Room roomAux : roomGenericList.getList()) {
            if(roomAux.getStateRoom().equals(State.OCCUPIED)){
                System.out.println(roomAux.toString());
            }
        }

        for (Room roomAux : roomGenericList.getList()) {
            if(roomAux.getStateRoom().equals(State.CLEANING)){
                System.out.println(roomAux.toString());
            }
        }

        for (Room roomAux : roomGenericList.getList()) {
            if(roomAux.getStateRoom().equals(State.IN_MAINTENANCE)){
                System.out.println(roomAux.toString());
                System.out.println(roomAux.toString());

            }
        }


    }

    protected static List<Reservation> getPassengerReservations(String dni) {
        List<Reservation> passengerReservations = new ArrayList<>();
        if (reservationGenericList.getList().size() > 0) {
            for (Reservation reserv : reservationGenericList.getList()) {
                if (reserv.getDniPassenger().equals(dni)) {
                    if (reserv.getStatus() != Status.CANCELLED)
                        passengerReservations.add(reserv);
                }
            }
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

    protected static void showReservation() {
        for (Reservation reservation : reservationGenericList.getList()) {
            System.out.println(reservation.toString());
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

    protected static void showUsers() {
        int i = 0;
        for (User userAux : userGenericList.getList()) {
            i++;
            System.out.println(userAux.toString());
        }
        System.out.println("Cantidad de usuarios:" + i);
    }

    protected static void addToList(SaveInfoUsers infoToAdd) {
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

    /*@Override
    public boolean addToList(User userToAdd) {
        return userList.add(userToAdd);
    }

    @Override
    public boolean deleteFromList(User user) {
        return userList.remove(user);
    }

    @Override
    public int searchInList(String name) {
        for (User userAux : userList) {
            if (name.equals(userAux.getLoginName())) {
                return userList.indexOf(userAux);
            }
        }
        return -1;
    }

    @Override
    public void showList(List<User> listUsers) {
        for (User userAux : listUsers) {
            if (userAux instanceof Admin) {
                userAux.toString();
            } else if (userAux instanceof Passenger) {
                userAux.toString();
            }
            if (userAux instanceof Receptionist) {
                userAux.toString();
            }
        }
    }*/


}
