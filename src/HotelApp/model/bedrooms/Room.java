package HotelApp.model.bedrooms;



import HotelApp.model.users.Passenger;
import HotelApp.util.State;
import HotelApp.util.ProductToConsume;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class  Room  {
    private Passenger occupant;
    private int roomNumber;
    private State stateRoom;
    private int numberOfBeds;
    private double price;
    private List<ProductToConsume>consumed =new ArrayList<>();
    private static int count = 1;
    private String typeRoom;

    public Room() { }

    public Room( State stateRoom, int numberOfBeds, double price) {
        this.roomNumber = count++;
        this.stateRoom = stateRoom;
        this.numberOfBeds = numberOfBeds;
        this.price = price;
    }

    public String getTypeRoom() {
        return typeRoom;
    }

    public void setTypeRoom(String typeRoom) {
        this.typeRoom = typeRoom;
    }
    public Passenger getOccupant() {
        return occupant;
    }

    public void setOccupant(Passenger occupant) {
        this.occupant = occupant;
    }

    public int getRoomNumber() {
        return roomNumber;
    }


    public State getStateRoom() {
        return stateRoom;
    }

    public void setStateRoom(State stateRoom) {
        this.stateRoom = stateRoom;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<ProductToConsume> getConsumed() {
        return consumed;
    }


    public void addConsumption(ProductToConsume consumption){
        consumed.add(consumption);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;
        Room room = (Room) o;
        return roomNumber == room.roomNumber && numberOfBeds == room.numberOfBeds && Double.compare(room.price, price) == 0 && Objects.equals(occupant, room.occupant) && stateRoom == room.stateRoom && Objects.equals(consumed, room.consumed) && Objects.equals(typeRoom, room.typeRoom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(occupant, roomNumber, stateRoom, numberOfBeds, price, consumed, typeRoom);
    }

    @Override
    public String toString() {
        return "『 ----------------------------------------" +
                "\n Type: " + typeRoom +
                "\n occupant=" + occupant +
                "\n roomNumber='" + roomNumber  +
                "\n stateRoom=" + stateRoom +
                "\n numberOfBeds=" + numberOfBeds +
                "\n price=" + price +
                "\n consumed=" + consumed+
                "\n------------------------------------------』";

    }

}