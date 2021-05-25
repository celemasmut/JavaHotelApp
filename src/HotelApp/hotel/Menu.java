package HotelApp.hotel;

import HotelApp.hotel.bedrooms.*;
import HotelApp.hotel.users.Admin;
import HotelApp.hotel.users.Passenger;
import HotelApp.hotel.users.Receptionist;
import HotelApp.hotel.users.User;

import java.io.PrintStream;
import java.util.Scanner;

public class Menu {
    private PrintStream printOut;
    private User user;


    public Menu(){
        printOut = System.out;
        user = null;
    }

    private void showFirstMenu(){
        printOut.println("1- Register");
        printOut.println("2- Login");
        printOut.println("3- Leave");
    }

    private int scanIntData(){
        Scanner scan = new Scanner(System.in);
        int data = scan.nextInt();
        return data;
    }

    public void initiate(){
        int op =0;
        do{
            showFirstMenu();
            op=scanIntData();
            switch (op){
                case 1:
                    register();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    leave();
                    break;
                default:
                    printOut.println("Wrong option");
                    break;
            }
        }while (op != 3);
    }

    private boolean validatePassengerDni(String dni){
        boolean exist= false;
        if(Hotel.getUsersList().size() > 0 ){
            for (User user : Hotel.getUsersList()){
                if(user instanceof Passenger){
                    if(((Passenger) user).getDni().equals(dni)) {
                        exist = true;
                        break;
                    }
                }
            }
        }
        return exist;
    }

    private void register(){
        Scanner scan = new Scanner(System.in);
        printOut.println("Insert Name: ");
        String name = scan.next();
        printOut.println("Insert DNI :");
        String dni = scan.next();
        boolean exist = validatePassengerDni(dni);
        if(!exist){
            printOut.println("Insert HomeTown : ");
            String homeTown = scan.next();
            printOut.println("Insert HomeAddress : ");
            String homeAddress = scan.next();
            printOut.println("Insert username :");
            String userName= scan.next();
            printOut.println("Insert password : ");
            String password = scan.next();

            Hotel.addUserToList(new Passenger(userName,password,name,dni,homeTown,homeAddress));
            printOut.println("New Passenger registered");
        }
    }

    private void showLoginMenu(){
        printOut.println("1- Passenger");
        printOut.println("2- Receptionist");
        printOut.println("3- Admin");
    }

    private void login(){
        int op=0;
        do{
            showLoginMenu();
            op = scanIntData();
            switch (op){
                case 1:
                    passenger();
                    break;
                case 2:
                    receptionist();
                    break;
                case 3:
                    admin();
                    break;
                default:
                    printOut.println("Wrong option");
                    break;
            }
        }while (op != 4);
    }

    private void userLogin ()
    {
        int posInList=0;
        Scanner scan = new Scanner (System.in);
        String userName;
        String password;

        printOut.println("Insert user name:");
        userName=scan.nextLine();
        userName.toLowerCase();
        printOut.println("Insert password:");
        password=scan.nextLine();
        posInList=searchUserInList(userName,password);
        if (posInList>-1)
        {
            if (Hotel.getUsersList().get(posInList) instanceof Passenger)
            {
                Hotel.getUsersList().get(posInList).menu();
            }
            else if (Hotel.getUsersList().get(posInList) instanceof Receptionist)
            {
                Hotel.getUsersList().get(posInList).menu();
            }
            if (Hotel.getUsersList().get(posInList) instanceof Admin)
            {
                Hotel.getUsersList().get(posInList).menu();
            }
        }
        else
            printOut.println("There is no match in user, try again or register");

    }

    private int searchUserInList(String userName,String password)
    {
        int posInList=-1;
        for (User aux:Hotel.getUsersList())
        {
            if (userName.equalsIgnoreCase(aux.getLoginName()) && password.equals(aux.getPassword()))
            {
                posInList=Hotel.getUsersList().indexOf(aux);

            }
        }
        return posInList;
    }

    private void seeRoomFree(){
        for(Room room : Hotel.getRoomsList()){
            if( room.getStateRoom().equals(State.FREE)){
                if(room instanceof SingleRoom){
                    room.toString();
                }
                if(room instanceof DoubleRoom){
                    room.toString();
                }
                if(room instanceof FamilyRoom){
                    room.toString();
                }
                if(room instanceof KingRoom){
                    room.toString();
                }
            }
        }
    }

    private void passenger(){
        //ver hab dispo
        seeRoomFree();
        //reservar

    }
    private void receptionist(){

    }

    private void admin(){

    }

    private void leave(){

    }
}
