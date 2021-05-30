package HotelApp.hotel;

import HotelApp.hotel.bedrooms.*;
import HotelApp.hotel.users.Passenger;
import HotelApp.hotel.users.Receptionist;
import HotelApp.hotel.users.User;

import java.io.PrintStream;
import java.time.LocalDate;

import java.util.List;
import java.util.Scanner;

import static HotelApp.hotel.Hotel.*;

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

    private String register(){
        printOut.println("Insert Name: ");
        String name = scan.next();
        printOut.println("Insert DNI :");
        scan.nextLine();
        String dni = scan.next();
        boolean exist = validatePassengerDni(dni);
        if(!exist){
            printOut.println("Insert HomeTown : ");
            scan.nextLine();
            String homeTown = scan.next();
            printOut.println("Insert HomeAddress : ");
            scan.nextLine();
            String homeAddress = scan.next();
            printOut.println("Insert username :");
            scan.nextLine();
            String userName= scan.next();
            printOut.println("Insert password : ");
            scan.nextLine();
            String password = scan.next();

            Hotel.addUserToList(new Passenger(userName,password,name,dni,homeTown,homeAddress));
            printOut.println("New Passenger registered");
            return dni;
        }
        return null;
    }
       ///unicamente lo puede registrar un administrador
    public void registerRecepcionist(){
        printOut.println("Insert file number: ");
        int fileNumber = scan.nextInt();
        boolean exist = validateRecepcionist(fileNumber);
        if(exist){
            System.out.println("The receptionist is already registered");
        }
        else{
            printOut.println("Insert Login name: ");
            String loginName = scan.next();
            printOut.println("Insert password : ");
            scan.nextLine();
            String password = scan.next();
            Receptionist receptionist = new Receptionist(loginName,password);
            receptionist.setFileNumber(fileNumber);
            Hotel.addUserToList(receptionist);
            printOut.println("New Receptionist registered");
        }
    }

    public boolean validateRecepcionist(int fileNumber){
        if(Hotel.getUsersList().size() > 0 ){
            for (User user : Hotel.getUsersList()){
                if(user instanceof Receptionist){
                    if(((Receptionist) user).getFileNumber()==(fileNumber)) {
                        System.out.println("true");
                        return true;
                    }
                }
            }
        }
        System.out.println("false");
        return false;
    }



    private void showLoginMenu(){
        printOut.println("1- Passenger");
        printOut.println("2- Receptionist");
        printOut.println("3- Admin");
        printOut.println("4- Exit");
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
                    printOut.println("Good Bye");
                    break;
            }
        }while (op != 4);
    }

    private String userLogin ()
    {
        String exist=null;
        Scanner scan = new Scanner (System.in);
        String userName;
        String password;

        printOut.println("Insert user name:");
        userName=scan.nextLine();
        userName.toLowerCase();
        printOut.println("Insert password:");
        password=scan.nextLine();
        exist=searchUserInList(userName,password);
        if (exist==null)
        {
            printOut.println("There is no match in user, try again or register");

        }
        return exist;
    }

    private String searchUserInList(String userName,String password)
    {
        for (User aux:Hotel.getUsersList())
        {
            if (aux instanceof Passenger)
            {
                if (userName.equalsIgnoreCase(aux.getLoginName()) && password.equals(aux.getPassword())){
                    return ((Passenger) aux).getDni();
                }

            }
        }
        return null;
    }

    private void seeRoomFree(int option){
        for(Room room : Hotel.getRoomsList()){
            if( room.getStateRoom().equals(State.FREE)){
                if(room instanceof SingleRoom&& option==1)
                    printOut.println(room.toString());
                if(room instanceof DoubleRoom&& option==2)
                    printOut.println(room.toString());
                if(room instanceof FamilyRoom&&option==3)
                    printOut.println(room.toString());
                if(room instanceof KingRoom&&option==4)
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
        printOut.println("Days of Stay:");
        return scan.nextInt();
    }

    private LocalDate setDayOfExit(LocalDate arrival,int daysOfStay){
        return arrival.plusDays(daysOfStay);
    }

    private Reservation toReserveRoom( LocalDate arrival,LocalDate exit,String roomNumberChoosed,MealPlan plan,String dniUser){
        Reservation reservation=null;
        for(Room room : Hotel.getRoomsList()){
            if(room.getRoomNumber().equals(roomNumberChoosed)){
                reservation = new Reservation(room,arrival, exit,plan,dniUser);
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
            reserv.setStatus(Status.CONFIRMED);
            Hotel.addReservation(reserv);
            printOut.println(Hotel.getReservationsList().get(0).toString());//ver la utilidad al ejecutar. si solo muestra la reserva cambiar linea
        }else if(confirm == 2){
            reserv = null;
            printOut.println("The reservation is deleted");
        }
        else{
            printOut.println("Wrong option");
        }
    }

    private void showTypeOfRooms(){
        printOut.println("Insert number of type Room:");
        printOut.println("1_ Single Room");
        printOut.println("2_ Double Room");
        printOut.println("3_ Family Room");
        printOut.println("4_ King Room");
    }

    private boolean showStatusReservation(List<Reservation> confirmedReservation){
       boolean show = false;
       if(confirmedReservation.size() > 0) {
           printOut.println(" Your reservations");
           int i = 1;
           for (Reservation reserv : confirmedReservation) {
               printOut.println(i + " - " + reserv.toString());
           }
           show=true;
       }
       return show;
    }

    private void passengerMenu(){
        printOut.println("1 - To book a room");
        printOut.println("2 - See all your reservations");
        printOut.println("3 - Cancel a reservation");
        printOut.println("4 - Check reservation");
        printOut.println("5 - Add an order");
    }

    private void toBookARoom(String dniUser){
        int optionOfRoom=0;
        int dayOfStay = setDaysOfStay();
        LocalDate arrival = chooseArrivalDate();
        showTypeOfRooms();
        optionOfRoom=scan.nextInt();
        seeRoomFree(optionOfRoom);
        printOut.println("Insert number of room you want to book ");
        String room = scan.next();
        scan.nextLine();
        Reservation newReserv = toReserveRoom(arrival, setDayOfExit(arrival,dayOfStay), room, chooseMealPlan(),dniUser);
        confirmReservation(newReserv, dayOfStay);
    }

    private void toCancelReservation(String dniUser){
        if(showStatusReservation(Hotel.getStatusReservations(Hotel.getPassengerReservations(dniUser),Status.CONFIRMED))) {
            printOut.println("Choose the reservation you want to canceled");
            int i = scan.nextInt();
            Reservation reservationCanceled = Hotel.getStatusReservations(Hotel.getPassengerReservations(dniUser), Status.CONFIRMED).get(i - 1);
            printOut.println(reservationCanceled.toString());
            printOut.println("Confirm to cancel reservation ? \n 1- yes \n 2- No");
            i = scan.nextInt();
            if (i == 1) {
                if (canceledReservation(reservationCanceled)) {
                    printOut.println("Reservation canceled");
                } else {
                    printOut.println("Something went wrong with the cancellation");
                }
            }
        }else{
            printOut.println("There is no reservation to cancel");
        }
    }

    private void checkActiveReservation(String dniUser){
        if(showStatusReservation(Hotel.getStatusReservations(Hotel.getPassengerReservations(dniUser),Status.ACTIVE))) {
            printOut.println("Choose the active reservation you want to check");
            int index = scan.nextInt();
            printOut.println(Hotel.getStatusReservations(Hotel.getPassengerReservations(dniUser), Status.ACTIVE).get(index - 1));
        }
    }

    private void passenger(){
        String dniUser;
        dniUser=userLogin();
        if(dniUser!=null) {
            boolean exit = false;
            do{
                passengerMenu();
                int op = scan.nextInt();
                switch (op){
                    case 1:
                        toBookARoom(dniUser);
                        break;
                    case 2:
                        Hotel.getPassengerReservations(dniUser).forEach(ob -> printOut.println(ob.toString()));
                        break;
                    case 3:
                        toCancelReservation(dniUser);
                        break;
                    case 4:
                        checkActiveReservation(dniUser);
                        break;
                    case 5:
                        break;
                    case 6:
                        exit=true;
                        break;
                }
            }while (!exit);
        }
    }
    private void showRecepcionistMenu()
    {
        printOut.println("1_Check in");
        printOut.println("2_Reservation");
        printOut.println("3_Check out");
        printOut.println("4_Show consumition of room");
        printOut.println("5_Exit");
    }
    private LocalDate chooseDate(){

        return LocalDate.now();
    }
    private void checkIn(){
        int option=0;
        int optionOfRoom=0;
        String numberOfRoom;
        String dniUser;
        boolean result;
        printOut.println("wants to make the entry of a passenger. Enter 1 or if you want to enter a reservation enter 2");
        option=scan.nextInt();
        if (option==1)
        {
            dniUser=register();
            int dayOfStay = setDaysOfStay();
            LocalDate arrival = LocalDate.now();
            showTypeOfRooms();
            optionOfRoom=scan.nextInt();
            seeRoomFree(optionOfRoom);
            printOut.println("Insert number of room you want to reserve ");
            numberOfRoom =scan.next();
            scan.nextLine();
            //reservar
            Reservation newReserv = toReserveRoom(arrival, setDayOfExit(arrival,dayOfStay), numberOfRoom, chooseMealPlan(),dniUser);
            result=changeStateOfRoom(newReserv);


        }else if (option==2){

        }


    }
    private void receptionist(){
        boolean exit=false;
        int option;
        while (!exit)
        {
            showRecepcionistMenu();
            option=scan.nextInt();
            switch (option)
            {
                case 1:
                    checkIn();
                    showListOfRoom();
                    break;
                case 2:
                    ///reservation();
                    break;
                case 3:
                    ///checkOut();
                    break;
                case 4:
                    ///showConsumitionOfRoom();
                    break;
                case 5:
                    leave();
                    break;

            }

        }
    }

    private void admin(){
        boolean exit=false;
        int option;
        while (!exit)
        {
            showAdminMenu();
            option=scan.nextInt();
            switch (option)
            {
                case 1:
                    registerRecepcionist();
                    break;
                case 2:
                    ///reservation();
                    break;
                case 3:
                    ///checkOut();
                    break;
                case 4:
                    ///showConsumitionOfRoom();
                    break;
                case 5:
                    leave();
                    break;

            }

        }
    }

    private void showAdminMenu()
    {
        printOut.println("1_Register receptionist ");
        printOut.println("2_Register passanger");
        printOut.println("3_Check in");
        printOut.println("4_Check out");
        printOut.println("5_Exit");
    }

    private void leave(){

    }
}
