package HotelApp.model.bedrooms;
import HotelApp.util.State;


public class SingleRoom extends Room{

    public SingleRoom(){}

    public SingleRoom(State stateRoom, int numberOfBeds, double price) {
        super( stateRoom, numberOfBeds, price);
        setTypeRoom("SingleRoom");
    }
}