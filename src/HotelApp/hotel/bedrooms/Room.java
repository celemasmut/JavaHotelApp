package HotelApp.hotel.bedrooms;

import HotelApp.hotel.users.Passenger;

public abstract class  Room {
    private Passenger occupant;
    ///Enum clasificacion
    private String roomNumber;
    private State stateRoom;
    private int numberOfBeds;
    private double price;
    ///enum de consumos con lista


    public Room(Passenger occupant, String roomNumber, int numberOfBeds, double price) {
        this.occupant = occupant;
        this.roomNumber = roomNumber;
        this.numberOfBeds = numberOfBeds;
        this.price = price;
    }
    public abstract void showConsumptions ();
}