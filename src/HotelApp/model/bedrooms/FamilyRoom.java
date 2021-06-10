package HotelApp.model.bedrooms;
import HotelApp.util.State;

public class FamilyRoom extends Room{

    public FamilyRoom(){}

    public FamilyRoom(State stateRoom, int numberOfBeds, double price) {
        super( stateRoom, numberOfBeds, price);
        setTypeRoom("Family Room");
    }


}

