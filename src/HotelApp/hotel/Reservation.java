package HotelApp.hotel;

import HotelApp.hotel.bedrooms.Room;

import java.time.LocalDate;
import java.util.UUID;

public class Reservation {
    private String dniPassenger;
    private Room roomToReserve;
    private LocalDate arrivalDay;
    private LocalDate dayOfExit;
    private MealPlan plan;

    public Reservation(Room roomToReserve, LocalDate arrivalDay, LocalDate dayOfExit, MealPlan plan,String dniPassenger) {
        this.dniPassenger=dniPassenger;
        this.roomToReserve = roomToReserve;
        this.arrivalDay = arrivalDay;
        this.dayOfExit = dayOfExit;
        this.plan = plan;
    }

    public String getDniPassenger() {
        return dniPassenger;
    }

    public void setPlan(MealPlan plan) {
        this.plan = plan;
    }

    public void setDniPassenger(String dniPassenger) {
        this.dniPassenger = dniPassenger;
    }

    public Room getRoomToReserve() {
        return roomToReserve;
    }

    public MealPlan getPlan() {
        return plan;
    }

    public LocalDate getArrivalDay() {
        return arrivalDay;
    }

    public void setArrivalDay(LocalDate arrivalDay) {
        this.arrivalDay = arrivalDay;
    }

    public LocalDate getDayOfExit() {
        return dayOfExit;
    }

    public void setDayOfExit(LocalDate dayOfExit) {
        this.dayOfExit = dayOfExit;
    }


    public void setRoomToReserve(Room roomToReserve) {
        this.roomToReserve = roomToReserve;
    }

    public double totalPriceReservation(int daysOfStay){
        double totalPriceReservation=0;
        if(roomToReserve != null){
            totalPriceReservation = (roomToReserve.getPrice() * daysOfStay) + (plan.getPrice()*daysOfStay);
        }
        return totalPriceReservation;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "dniPassenger='" + dniPassenger + '\'' +
                ", roomToReserve=" + roomToReserve +
                ", arrivalDay=" + arrivalDay +
                ", dayOfExit=" + dayOfExit +
                ", plan=" + plan +
                '}';
    }
}