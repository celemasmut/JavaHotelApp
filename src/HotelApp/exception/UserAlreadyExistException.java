package HotelApp.exception;

public class UserAlreadyExistException extends Exception{

    public UserAlreadyExistException(){
        super("The Passenger already exist, no need to register");
    }
}
