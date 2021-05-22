package HotelApp.hotel.bedrooms;

import HotelApp.hotel.users.Passenger;

public class DoubleRoom extends Room{

    public DoubleRoom(String roomNumber, State stateRoom, int numberOfBeds, double price) {
        super(roomNumber, stateRoom, numberOfBeds, price);
    }

    @Override
    public void showConsumptions() {

    }

    @Override
    public String toString() {
        return "Double{}";
    }
}