package HotelApp.model.reservation;

import HotelApp.model.bedrooms.*;
import HotelApp.util.LocalDateDeserializer;
import HotelApp.util.LocalDateSerializer;
import HotelApp.util.MealPlan;
import HotelApp.util.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.awt.event.WindowStateListener;
import java.time.LocalDate;

public class Reservation implements Comparable<Reservation>{

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
    private String showRoom(){
        if(roomToReserve instanceof SingleRoom){
            return ((SingleRoom)roomToReserve).toString();
        }
        if(roomToReserve instanceof DoubleRoom){
            return ((DoubleRoom)roomToReserve).toString();
        }
        if(roomToReserve instanceof FamilyRoom){
            return ((FamilyRoom)roomToReserve).toString();
        }
        if(roomToReserve instanceof KingRoom){
            return ((KingRoom)roomToReserve).toString();
        }
        return null;
    }

    @Override
    public String toString() {
        return "『 ----------------------------------------" +
                "\n Reservation : " +
                "\n dniPassenger ='" + dniPassenger  +
                "\n roomToReserve = \n" + getRoomToReserve().toString() +
                "\n arrivalDay =" + arrivalDay +
                "\n dayOfExit =" + dayOfExit +
                "\n plan =" + plan +
                "\n status =" + status +
                "\n------------------------------------------』";

    }

    @Override
    public int compareTo(Reservation o) {
        if(o.getArrivalDay().isAfter(this.arrivalDay)){
            return -1;
        }
        return 0;
    }
}