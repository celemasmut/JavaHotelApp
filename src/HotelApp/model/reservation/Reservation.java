package HotelApp.model.reservation;

import HotelApp.model.bedrooms.Room;
import HotelApp.util.LocalDateDeserializer;
import HotelApp.util.LocalDateSerializer;
import HotelApp.util.MealPlan;
import HotelApp.util.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;

public class Reservation {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate arrivalDay;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dayOfExit;

    private String dniPassenger;
    private Room roomToReserve;
    private MealPlan plan;
    private Status status;

    public Reservation(){}

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
        return "『 ----------------------------------------" +
                "\n Reservation : " +
                "\n dniPassenger='" + dniPassenger  +
                "\n roomToReserve=" + roomToReserve +
                "\n arrivalDay=" + arrivalDay +
                "\n dayOfExit=" + dayOfExit +
                "\n plan=" + plan +
                "\n status=" + status +
                "------------------------------------------』";

    }
}