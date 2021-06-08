package HotelApp.exception;

import HotelApp.model.reservation.Reservation;

public class ReservationNotFoundException extends Exception{

    public ReservationNotFoundException(){
        super("The reservation is not found or does not exist");
    }
}
