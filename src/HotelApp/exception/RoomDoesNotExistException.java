package HotelApp.exception;

public class RoomDoesNotExistException extends Exception{

    public RoomDoesNotExistException(String msg){
        super(msg);
    }
}
