package HotelApp.hotel.bedrooms;

import HotelApp.hotel.users.Passenger;

public class DoubleRoom extends Room{

    public DoubleRoom(String roomNumber, State stateRoom, int numberOfBeds, double price) {
        super(roomNumber, stateRoom, numberOfBeds, price);
    }


    @Override
    public String toString() {
        return super.toString();
    }
}