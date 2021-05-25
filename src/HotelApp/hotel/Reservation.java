package HotelApp.hotel;

import HotelApp.hotel.bedrooms.Room;

import java.time.LocalDate;

public class Reservation {
    private int id;
    private Room roomToReserve;
    private LocalDate arrivalDay;
    private LocalDate dayOfExit;
    private MealPlan plan;

    public Reservation(Room roomToReserve, LocalDate arrivalDay, LocalDate dayOfExit, MealPlan plan) {
        this.roomToReserve = roomToReserve;
        this.arrivalDay = arrivalDay;
        this.dayOfExit = dayOfExit;
        this.plan = plan;
    }

    public void setPlan(MealPlan plan) {
        this.plan = plan;
    }

    public int getId() {
        return id;
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
}