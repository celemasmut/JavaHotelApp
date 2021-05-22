package HotelApp.hotel.bedrooms;

import HotelApp.hotel.users.Passenger;

public class FamilyRoom extends Room{

    public FamilyRoom(String roomNumber, State stateRoom, int numberOfBeds, double price) {
        super(roomNumber, stateRoom, numberOfBeds, price);
    }

    @Override
    public void showConsumptions() {

    }

    @Override
    public String toString() {
        return "Family{}";
    }
}

