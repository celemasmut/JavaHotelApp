package HotelApp.model.bedrooms;

import HotelApp.util.State;

public class KingRoom extends Room {

    public KingRoom(){}

    public KingRoom(State stateRoom, int numberOfBeds, double price) {
        super(stateRoom, numberOfBeds, price);
    }


    @Override
    public String toString() {
        return "『 ----------------------------------------" +
                "\n Type: King Room " +'\n'
                + super.toString()+
                "\n------------------------------------------』";

    }
}