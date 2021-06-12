package HotelApp.exception;

public class UserDoesNotExistException extends Exception{

    public UserDoesNotExistException(){
        super("The user does not exist, try again or register");
    }
}
