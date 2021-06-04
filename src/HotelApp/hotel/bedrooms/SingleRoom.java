package HotelApp.hotel.bedrooms;


import HotelApp.hotel.users.Passenger;

public class SingleRoom extends Room{

    public SingleRoom(State stateRoom, int numberOfBeds, double price) {
        super( stateRoom, numberOfBeds, price);
    }


    @Override
    public String toString() {
        return super.toString();
    }
}