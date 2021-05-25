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
        boolean exist=false;
        Scanner scan = new Scanner (System.in);
        String userName;
        String password;

        printOut.println("Insert user name:");
        userName=scan.nextLine();
        userName.toLowerCase();
        printOut.println("Insert password:");
        password=scan.nextLine();
        exist=searchUserInList(userName,password);
        if (!exist)
        {
            printOut.println("There is no match in user, try again or register");

        }
    }

    private boolean searchUserInList(String userName,String password)
    {
        boolean exist = false;
        for (User aux:Hotel.getUsersList())
        {
            if (userName.equalsIgnoreCase(aux.getLoginName()) && password.equals(aux.getPassword()))
            {
                exist=true;
                break;
            }
        }
        return exist;
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

    private void showMealPlan(){
        int i=1;
        for(MealPlan mealPlan : MealPlan.values()){
            printOut.println(i +" - " + mealPlan +" " + mealPlan.getDescription());
            i++;
        }
    }

    private MealPlan chooseMealPlan(){
        showMealPlan();
        int op= scanIntData();
        int i=1;
        for(MealPlan plan : MealPlan.values()){
            if(op == i){
                return plan;
            }
        }
        return null;
    }

  /*  private boolean toReserveRoom(String roomNumberChoosed){
        boolean reserved= false;
        for(Room room : Hotel.getRoomsList()){
            if(room.getRoomNumber().equals(roomNumberChoosed)){
                Reservation reservation = new Reservation(room);
            }
        }
        return reserved;
    }*/




    private void passenger(){
        //ver hab dispo
        seeRoomFree();
        chooseMealPlan();//usar para passar por param.
        //reservar

    }
    private void receptionist(){

    }

    private void admin(){

    }

    private void leave(){

    }
}
