package HotelApp.exception;

public class RoomDoesNotExistException extends Exception{

    public RoomDoesNotExistException(){
        super("The room does not exist");
    }
}
