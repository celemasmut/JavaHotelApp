package HotelApp.hotel.bedrooms;


import HotelApp.hotel.users.Passenger;

public class KingRoom extends Room {

    public KingRoom(String roomNumber, State stateRoom, int numberOfBeds, double price) {
        super(roomNumber, stateRoom, numberOfBeds, price);
    }

    @Override
    public void showConsumptions() {

    }

    @Override
    public String toString() {
        return super.toString();
    }
}