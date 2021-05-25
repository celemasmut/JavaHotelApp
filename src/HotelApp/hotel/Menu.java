package HotelApp.hotel;

import HotelApp.hotel.bedrooms.*;
import HotelApp.hotel.users.Passenger;
import HotelApp.hotel.users.User;

import java.io.PrintStream;
import java.time.LocalDate;

import java.util.Scanner;

public class Menu {
    private PrintStream printOut;
    private User user;

    Scanner scan = new Scanner(System.in);


    public Menu(){
        printOut = System.out;
        user = null;
    }

    private void showFirstMenu(){
        printOut.println("1- Register");
        printOut.println("2- Login");
        printOut.println("3- Leave");
    }


    public void initiate(){
        int op;
        do{
            showFirstMenu();
            op= scan.nextInt();
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
        int op;
        do{
            showLoginMenu();
            op = scan.nextInt();
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

    private boolean userLogin ()
    {
        boolean exist;
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
        return exist;
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
                if(room instanceof SingleRoom)
                    printOut.println(room.toString());
                if(room instanceof DoubleRoom)
                    printOut.println(room.toString());
                if(room instanceof FamilyRoom)
                    printOut.println(room.toString());
                if(room instanceof KingRoom)
                    printOut.println(room.toString());
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
        int op= scan.nextInt();
        scan.nextLine();
        int i=1;
        for(MealPlan plan : MealPlan.values()){
            if(op == i){
                return plan;
            }
            i++;
        }
        return null;
    }

    private LocalDate chooseArrivalDate(){
        printOut.println("Choose the arrival date yyyy/MM/dd");
        printOut.println("Year : ");
        int year = scan.nextInt();

        printOut.println("Month : ");
        int month = scan.nextInt();

        printOut.println("Day : ");
        int day = scan.nextInt();


        return LocalDate.of(year,month,day);
    }

    private int setDaysOfStay(){
        return scan.nextInt();
    }

    private LocalDate setDayOfExit(LocalDate arrival,int daysOfStay){
        return arrival.plusDays(daysOfStay);
    }

    private Reservation toReserveRoom( LocalDate arrival,LocalDate exit,String roomNumberChoosed,MealPlan plan){
        Reservation reservation=null;
        for(Room room : Hotel.getRoomsList()){
            if(room.getRoomNumber().equals(roomNumberChoosed)){
                reservation = new Reservation(room,arrival, exit,plan);
            }
        }
        return reservation;
    }

    private void confirmReservation(Reservation reserv, int dayOfStay){
        printOut.println(reserv.toString());
        printOut.println(" Is a total of $"+reserv.totalPriceReservation(dayOfStay));
        printOut.println("Confirm pay : \n 1-Confirm \n 2-No Confirm");
        int confirm = scan.nextInt();
        if(confirm == 1){
            reserv.getRoomToReserve().setStateRoom(State.RESERVED);
            Hotel.addReservation(reserv);
        }else if(confirm == 2){
            reserv = null;
            printOut.println("The reservation is deleted");
        }
        else{
            printOut.println("Wrong option");
        }
    }



    private void passenger(){
        //login
        if(userLogin()) {
            //ver hab dispo
            int dayOfStay = setDaysOfStay();
            LocalDate arrival = chooseArrivalDate();
            seeRoomFree();
            printOut.println("Insert number of room you want to reserve ");
            String room = scan.next();
            scan.nextLine();
            //reservar
            Reservation newReserv = toReserveRoom(arrival, setDayOfExit(arrival,dayOfStay), room, chooseMealPlan());
            confirmReservation(newReserv, dayOfStay);
        }
    }
    private void receptionist(){

    }

    private void admin(){

    }

    private void leave(){

    }
}
