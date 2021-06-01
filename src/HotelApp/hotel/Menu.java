package HotelApp.hotel;

import HotelApp.hotel.bedrooms.*;
import HotelApp.hotel.data.DataFile;
import HotelApp.hotel.users.Admin;
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
    private DataFile data;

    Scanner scan = new Scanner(System.in);



    public Menu(){
        printOut = System.out;
        data = new DataFile();
        data.hotelToJson(Hotel.getRoomsList(),"rooms.json");
        data.hotelToJson(Hotel.getEmployees().getAdmins(),"admins.json");
        data.hotelToJson(Hotel.getEmployees().getReceptionistList(),"receptionist.json");

    }

    private void showFirstMenu(){
        printOut.println("1- Register as a passenger");
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
                    backUp();
                    break;
                default:
                    printOut.println("Wrong option");
                    break;
            }
        }while (op != 3);
    }

    private boolean validatePassengerDni(String dni){
        if(Hotel.getPassengerList().size() > 0 ){
            for (Passenger user : Hotel.getPassengerList()){
                if(user instanceof Passenger){
                    if( user.getDni().equals(dni)) {
                        return true;
                    }
                }
            }
        }
        return false;
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

            Hotel.addPassengerToList(new Passenger(userName,password,name,dni,homeTown,homeAddress));
            printOut.println("New Passenger registered");
            return dni;
        }
        return null;
    }
       ///unicamente lo puede registrar un administrador
    public void registerRecepcionist(){
        printOut.println("Insert file number: "); // el legajo se debe crear solo.
        int fileNumber = scan.nextInt();
        boolean exist = validateRecepcionist(fileNumber);
        if(exist){
            printOut.println("The receptionist is already registered");
        }
        else{
            printOut.println("Insert Login name: ");
            String loginName = scan.next();
            printOut.println("Insert password : ");
            scan.nextLine();
            String password = scan.next();
            Receptionist receptionist = new Receptionist(loginName,password);
            receptionist.setFileNumber(fileNumber);
            Hotel.addReceptionistToList(receptionist);
            printOut.println("New Receptionist registered");
        }
    }

    public boolean validateRecepcionist(int fileNumber){
        if(Hotel.getEmployees().getReceptionistList().size() > 0 ){
            for (Receptionist user : Hotel.getEmployees().getReceptionistList()){
                if(user instanceof Receptionist){
                    if( user.getFileNumber()==(fileNumber)) {
                        printOut.println("true");
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
        User userToLogin=userLogin();
        if (userToLogin instanceof Passenger)
        {
            passenger(((Passenger) userToLogin));
        }else if (userToLogin instanceof Receptionist)
        {
            receptionist();
        }
        if (userToLogin instanceof Admin)
        {
            admin();
        }

    }

    private User userLogin ()
    {
        User loggedUser;
        Scanner scan = new Scanner (System.in);
        String userName;
        String password;

        printOut.println("Insert user name:");
        userName=scan.nextLine();
        printOut.println("Insert password:");
        password=scan.nextLine();
        loggedUser=searchUserInList(userName,password);
        return loggedUser;
    }

    private User searchUserInList(String userName,String password)
    {
        for (Passenger aux:Hotel.getPassengerList())
        {
            if (userName.equalsIgnoreCase(aux.getLoginName()) && password.equals(aux.getPassword())){
                return (Passenger) aux;
            }
        }
        for (Receptionist aux:Hotel.getEmployees().getReceptionistList())
        {
            if (userName.equalsIgnoreCase(aux.getLoginName()) && password.equals(aux.getPassword())){
                return (Receptionist)aux;
            }
        }
        for (Admin aux:Hotel.getEmployees().getAdmins())
        {
            if (userName.equalsIgnoreCase(aux.getLoginName()) && password.equals(aux.getPassword())){
                return (Admin)aux;
            }
        }
        return null;
    }


    private void passengerMenu(){
        printOut.println("1 - To book a room");
        printOut.println("2 - See all your reservations");
        printOut.println("3 - Check a reservation");
    }




    private void passenger(Passenger passenger){
        if(passenger!=null) {
            boolean exit = false;
            do{
                passengerMenu();
                int op = scan.nextInt();
                switch (op){
                    case 1:
                        toBookARoom(passenger.getDni());
                        break;
                    case 2:
                        Hotel.getPassengerReservations(passenger.getDni()).forEach(ob -> printOut.println(ob.toString()));
                        break;
                    case 3:
                        if(Hotel.getPassengerReservations(passenger.getDni()).size() > 0) {
                            Status status = chooseStatusReservation(showStatusReservation());
                            Reservation reservationChosen = checkStatusReservation(passenger.getDni(),status);
                            if(reservationChosen != null && reservationChosen.getStatus() != Status.CANCELLED) {
                                printOut.println(reservationChosen.toString());
                                passengerReservationChosenMenu(reservationChosen);
                            }
                        }
                        break;
                    case 4:
                        exit=true;
                        break;
                }
            }while (!exit);
        }
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
            printOut.println(reserv);
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

    private boolean showReservationByStatus(List<Reservation> statusTypeReservation){
        if(statusTypeReservation.size() > 0) {
            printOut.println(" Your reservations");
            int i = 1;
            for (Reservation reserv : statusTypeReservation) {

                printOut.println(i + " - " + reserv.toString());
                i++;
            }
            return true;
        }
        return false;
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

    private void confirmCancellation(Reservation reservationChosen){
        if(reservationChosen.getStatus().equals(Status.CONFIRMED)) {
            printOut.println("Confirm to cancel reservation ? \n 1- yes \n 2- No");
            int i = scan.nextInt();
            if (i == 1) {
                if (changeStatusReservation(reservationChosen,Status.CANCELLED)) {
                    printOut.println("Reservation canceled");
                } else {
                    printOut.println("Something went wrong with the cancellation");
                }
            }
        }
    }


    private Reservation checkStatusReservation(String dniUser, Status status){
        if(showReservationByStatus(Hotel.getStatusReservations(Hotel.getPassengerReservations(dniUser),status))) {
            printOut.println("Choose the reservation you want to check");
            int index = scan.nextInt();
            return Hotel.getStatusReservations(Hotel.getPassengerReservations(dniUser), status).get(index - 1);

        }
        return null;
    }


    private int showStatusReservation(){
        int i=1;
        printOut.println("Choose number of status");
        for(Status status : Status.values()){
            printOut.println(i+" -"+status);
            i++;
        }
        int x = scan.nextInt();
        return x;
    }
    private Status chooseStatusReservation(int pos){
        int i=1;
        for(Status status : Status.values()){
            if(pos == i){
                return status;
            }
            i++;

        }
        return null;
    }


    private void showProductToConsume(){
        int i=1;
        for(ProductToConsume prod : ProductToConsume.values()){
            printOut.println(i +"-"+prod + " price: $"+ prod.getPrice());
            i++;
        }
    }

    private int chooseAnItemProduct() {
        showProductToConsume();
        printOut.println("Choose item ");
        int op = scan.nextInt();
        return op;
    }
    private void addAnItemToList(Reservation actualReservation,int index){
        if(actualReservation.getStatus().equals(Status.ACTIVE)) {
            int i = 1;
            for (ProductToConsume prod : ProductToConsume.values()) {
                if (index == i) {
                    actualReservation.getRoomToReserve().addConsumption(prod);
                }
                i++;
            }
        }
    }

    private void showReservationChosenMenu(){
        printOut.println("1 - Cancel a reservation");
        printOut.println("2 - Add an order");
        printOut.println("3 - Check your consumptions");
    }
    private void checkPassengerConsumptions(Reservation reservationChosen){
        if(reservationChosen.getRoomToReserve().getConsumed().size() > 0) {
            double totalPrice =0;
            for (ProductToConsume prod : reservationChosen.getRoomToReserve().getConsumed()) {
                printOut.println(prod +" $"+prod.getPrice());
                totalPrice+=prod.getPrice();
            }
            if(reservationChosen.getStatus().equals(Status.ACTIVE))
            printOut.println("Total price : $"+totalPrice);
        }
    }
     private void passengerReservationChosenMenu(Reservation reservationChosen){
        if(reservationChosen != null){
            boolean exit = false;
            do{
                showReservationChosenMenu();
                int op = scan.nextInt();
                switch (op) {
                    case 1:
                        confirmCancellation(reservationChosen);
                        break;
                    case 2:
                        if(reservationChosen.getStatus().equals(Status.ACTIVE))
                            addAnItemToList(reservationChosen, chooseAnItemProduct());
                        break;
                    case 3:
                        checkPassengerConsumptions(reservationChosen);
                        break;
                    case 4:
                        exit =true;
                        break;
                }

            }while(!exit);
        }
     }


    private LocalDate chooseDate(){

        return LocalDate.now();
    }


    private void showReceptionistMenu()
    {
        printOut.println("1_Check in");
        printOut.println("2_Check out");
        printOut.println("3_Show consumption of room");
        printOut.println("4_Reservation");
        printOut.println("5_Exit");
    }
    private void receptionist(){
        boolean exit=false;
        int option;
        String dniUser;
        String roomNumber;
        while (!exit)
        {
            showReceptionistMenu();
            option=scan.nextInt();
            switch (option)
            {
                case 1:
                    checkIn();
                    showListOfRoom();
                    break;
                case 2:
                    checkOut();
                    showReservation();
                    break;
                case 3:
                    printOut.println("Enter room number:");
                    scan.nextLine();
                    roomNumber=scan.nextLine();
                    showConsumeOfRoom(roomNumber);
                    break;
                case 4:
                    printOut.println("Enter DNI of passenger:");
                    scan.nextLine();
                    dniUser=scan.nextLine();
                    if(Hotel.getPassengerReservations(dniUser).size() > 0) {
                        Status status = chooseStatusReservation(showStatusReservation());
                        Reservation reservationChosen = checkStatusReservation(dniUser,status);
                        if(reservationChosen != null && reservationChosen.getStatus() != Status.CANCELLED) {
                            printOut.println(reservationChosen.toString());
                            passengerReservationChosenMenu(reservationChosen);
                        }
                    }
                    else
                        printOut.println("This passenger does not have a reservation");
                    break;
                case 5:
                    exit=true;
                    break;

            }

        }
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
            Passenger passengerToRoom = searchPassengerInList(dniUser);
            result=changeStateOfRoom(passengerToRoom,newReserv.getRoomToReserve(),State.OCCUPIED);


        }else if (option==2){
            printOut.println("Insert DNI :");
            scan.nextLine();
            dniUser= scan.next();
            Reservation reservToActivate=searchReservationInList(dniUser);
            if (reservToActivate!=null)
            {
                Passenger passengerToRoom=searchPassengerInList(dniUser);
                result=changeStateOfRoom(passengerToRoom,reservToActivate.getRoomToReserve(),State.OCCUPIED);
            }else {
                printOut.println("No hay ninguna coincidencia con el DNI");
            }

        }


    }
    private void checkOut()
    {
        String dniPassenger;
        boolean result;
        printOut.println("Insert DNI :");
        scan.nextLine();
        dniPassenger=scan.next();
        Reservation reservationCheckOut=searchReservation(dniPassenger);
        if (reservationCheckOut!=null)
        {
            Passenger passengerToCheckOut=searchPassengerInList(dniPassenger);
            result=changeStateOfRoom(passengerToCheckOut,reservationCheckOut.getRoomToReserve(),State.FREE);
            result= changeStatusReservation(reservationCheckOut, Status.COMPLETED);


        }
    }
    private void showConsumeOfRoom(String roomNumber)
    {
        double totalPrice=0;
        printOut.println("Product:");
        for (Room roomToConsume:Hotel.getRoomsList())
        {
            if(roomNumber.equalsIgnoreCase(roomToConsume.getRoomNumber())&& roomToConsume.getConsumed()!=null)
            {
                for (ProductToConsume productToConsumeForRoom : roomToConsume.getConsumed())
                {
                printOut.println(productToConsumeForRoom);
                totalPrice=totalPrice+productToConsumeForRoom.getPrice();

                }
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
                    exit=true;
                    break;

            }

        }
    }




}
