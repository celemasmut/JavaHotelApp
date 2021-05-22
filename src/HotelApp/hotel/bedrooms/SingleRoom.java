package HotelApp.hotel.bedrooms;


import HotelApp.hotel.users.Passenger;

public class SingleRoom extends Room{
    public SingleRoom(Passenger occupant, String roomNumber, int numberOfBeds, double price) {
        super(occupant, roomNumber, numberOfBeds, price);
    }

    @Override
    public void showConsumptions() {

    }

    @Override
    public String toString() {
        return "Single{}";
    }
}