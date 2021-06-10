package HotelApp.model.bedrooms;
import HotelApp.util.State;

public class DoubleRoom extends Room{

    public DoubleRoom(){}

    public DoubleRoom(State stateRoom, int numberOfBeds, double price) {
        super(stateRoom, numberOfBeds, price);
        setTypeRoom("Double Room");
    }


/*    @Override
   public String toString() {
        return "『 ----------------------------------------" +
                "\n Type: Double Room" +'\n'
                + super.toString()+
                "\n------------------------------------------』";
    }*/
}