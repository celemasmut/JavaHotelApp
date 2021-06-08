package HotelApp.exception;

import HotelApp.model.reservation.Reservation;

public class ReservationNotFoundException extends Exception{

    public ReservationNotFoundException(String msg){
        super(msg);
    }
}
