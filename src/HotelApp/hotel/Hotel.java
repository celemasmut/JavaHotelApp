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
    private static List<User> userList;
    private static List<Room> roomsList;
    private static List<Reservation> reservationsList;
    private GenericList <User> userGenericList;
    private GenericList <Room> roomGenericList;
    private GenericList <Reservation> reservationGenericList;

    public Hotel() {
        this.roomsList = new ArrayList<>();
        this.reservationsList = new ArrayList<>();
        this.userList = new ArrayList<>();

    }

    public static List<Room> getRoomsList() {
        return roomsList;
    }

    public static List<Reservation> getReservationsList() {
        return reservationsList;
    }

    protected static void addReservation(Reservation reservation) {
        reservationsList.add(reservation);
    }

    public static void addRoomToList(Room room) {
        roomsList.add(room);
    }


    public static List<User> getUserList() {
        return userList;
    }


    public static void setRoomsList(List<Room> roomsList) {
        Hotel.roomsList = roomsList;
    }

    public static void setReservationsList(List<Reservation> reservationsList) {
        Hotel.reservationsList = reservationsList;
    }

    protected static boolean addPassenger(Passenger passengerToAdd) {
        return userList.add(passengerToAdd);
    }

    protected static boolean addRecepcionists(Receptionist receptionistToAdd) {
        return userList.add(receptionistToAdd);
    }

    protected static boolean changeStateOfRoom(Passenger passengerToRoom, Room roomToReserve, State state) {
        int posInList;


        posInList = roomsList.indexOf(roomToReserve);
        roomsList.get(posInList).setStateRoom(state);
        roomsList.get(posInList).setOccupant(passengerToRoom);
        return true;


    }

    protected static Passenger searchPassengerInList(String dni) {
        for (User userAux : userList) {
            if (userAux instanceof Passenger) {
                if (((Passenger) userAux).getDni().equals(dni))
                    return (Passenger) userAux;
            }
        }
        return null;
    }

    protected static Reservation searchReservation(String dniPassenger) {
        for (Reservation reservation : reservationsList) {
            if (dniPassenger.equalsIgnoreCase(reservation.getDniPassenger())) {
                return reservation;
            }
        }
        return null;
    }

    protected static void showListOfRoom() {
        for (Room roomAux : roomsList) {
            System.out.println(roomAux.toString());
        }
    }

    protected static List<Reservation> getPassengerReservations(String dni) {
        List<Reservation> passengerReservations = new ArrayList<>();
        if (reservationsList.size() > 0) {
            for (Reservation reserv : reservationsList) {
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
        if (reservationsList.size() > 0) {
            for (Reservation reservation : reservationsList) {
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
            int posInList = reservationsList.indexOf(reservationToDelete);
            reservationsList.get(posInList).setStatus(Status.COMPLETED);
            return true;
        }
        return false;
    }

    protected static void showReservation() {
        for (Reservation reservation : reservationsList) {
            System.out.println(reservation.toString());
        }
    }

    protected static Reservation searchReservationInList(String dniPassenger) {
        for (Reservation reservationAux : reservationsList) {
            if (dniPassenger.equalsIgnoreCase(reservationAux.getDniPassenger())) {
                return reservationAux;
            }
        }
        return null;
    }

    protected static void showUsers() {
        int i = 0;
        for (User userAux : userList) {
            i++;
            System.out.println(userAux.toString());
        }
        System.out.println("Cantidad de usuarios:" + i);
    }

    protected static void addToList(SaveInfoUsers infoToAdd) {
        for (Admin adminToAdd : infoToAdd.getListAdmin()) {
            userList.add(adminToAdd);

        }
        for (Passenger passengerToAdd : infoToAdd.getListPassenger()) {
            userList.add(passengerToAdd);

        }
        for (Receptionist receptionistToAdd : infoToAdd.getListRecepcionist()) {
            userList.add(receptionistToAdd);

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
