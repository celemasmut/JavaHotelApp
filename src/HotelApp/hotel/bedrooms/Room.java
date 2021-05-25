package HotelApp.hotel.bedrooms;

import HotelApp.hotel.users.Passenger;

import java.util.ArrayList;
import java.util.List;

public abstract class  Room {
    private Passenger occupant;
    private String roomNumber;
    private State stateRoom;
    private int numberOfBeds;
    private double price;
    private List<ProductToConsume>consumed;


    public Room() { }

    public Room(String roomNumber, State stateRoom, int numberOfBeds, double price) {
        this.roomNumber = roomNumber;
        this.stateRoom = stateRoom;
        this.numberOfBeds = numberOfBeds;
        this.price = price;
    }

    public Passenger getOccupant() {
        return occupant;
    }

    public void setOccupant(Passenger occupant) {
        this.occupant = occupant;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public State getStateRoom() {
        return stateRoom;
    }

    public void setStateRoom(State stateRoom) {
        this.stateRoom = stateRoom;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<ProductToConsume> getConsumed() {
        return consumed;
    }

    public void setConsumed() {
        this.consumed = new ArrayList<>();
    }

    public void addConsumption(ProductToConsume consumption){
        consumed.add(consumption);
    }

    public abstract void showConsumptions ();

}