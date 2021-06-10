package HotelApp.model.bedrooms;
import HotelApp.util.State;

public class FamilyRoom extends Room{

    public FamilyRoom(){}

    public FamilyRoom(State stateRoom, int numberOfBeds, double price) {
        super( stateRoom, numberOfBeds, price);
    }


    @Override
    public String toString() {
        return "『 ----------------------------------------" +
                "\n Type: Family Room " +'\n'
                + super.toString()+
                "\n------------------------------------------』";

    }
}

