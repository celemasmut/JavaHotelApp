package HotelApp.hotel;

import HotelApp.datafile.DataFile;
import HotelApp.datafile.SaveInfoUsers;
import HotelApp.exception.*;
import HotelApp.model.bedrooms.*;
import HotelApp.model.reservation.Reservation;
import HotelApp.model.users.Admin;
import HotelApp.model.users.Passenger;
import HotelApp.model.users.Receptionist;
import HotelApp.model.users.User;
import HotelApp.util.MealPlan;
import HotelApp.util.ProductToConsume;
import HotelApp.util.State;
import HotelApp.util.Status;

import java.io.PrintStream;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


import static HotelApp.hotel.Hotel.*;

public class Menu {
    private PrintStream printOut;

    Scanner scan = new Scanner(System.in);


    public Menu(){
        SaveInfoUsers saveinfo = new SaveInfoUsers();
        DataFile dataFile= new DataFile();

        ///saveinfo.addUsers(getUserList());
        setRoom(dataFile.readRoomJson("files/room.json"));

        ///writeInfo(saveinfo,"files/users.json");
        saveinfo=dataFile.readInfo("files/users.json");
        addToList(saveinfo);

        try {
            showUsers();
        } catch (UserDoesNotExistException e) {
            e.printStackTrace();
        }
        showListOfRoom();
        printOut = System.out;
    }

    private int toCaptureInt(int options){
        int input =-1;
        do{
            try{
                input= scan.nextInt();
                if(input <= 0 || input > options){
                    printOut.println("Wrong option");
                    input=-1;
                }
            }catch (InputMismatchException e){
                printOut.println("Insert an optional number");
                scan.next();
                input=-1;
            }
        }while (input == -1);
        return input;
    }

    private int showFirstMenu(){
        printOut.println("1- Register");
        printOut.println("2- Login");
        printOut.println("3- Leave");
        return 3;
    }


    public void initiate(){
        int op;
        do{
            op= toCaptureInt(showFirstMenu());
            switch (op){
                case 1:
                    try {
                        register();
                    } catch (UserAlreadyExistException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    login();
                    break;
                case 3:
                default:
                    printOut.println("Wrong option");
                    break;
            }
        }while (op != 3);
    }

    private boolean validatePassengerDni(String dni) {
        if (Hotel.getUserGenericList().getList().size() > 0) {
            for (User user : Hotel.getUserGenericList().getList()) {
                if (user instanceof Passenger)
                {
                if (((Passenger) user).getDni().equals(dni)) {
                    return true;
                }
                }
            }
        }
        return false;
    }

    private String register() throws UserAlreadyExistException {
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

            Hotel.addUser(new Passenger(userName,password,name,dni,homeTown,homeAddress));
            printOut.println("New Passenger registered");
            return dni;
        }else{
            throw new UserAlreadyExistException();
        }
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
            Hotel.addUser(receptionist);
            printOut.println("New Receptionist registered");
        }
    }

    public boolean validateRecepcionist(int fileNumber){
        if(Hotel.getUserGenericList().getList().size() > 0 ){
            for (User user : Hotel.getUserGenericList().getList()){
                if (user instanceof Receptionist)
                {
                    if(((Receptionist) user).getFileNumber()==(fileNumber)) {
                        System.out.println("true");
                        return true;
                    }
                }
            }
            }
        return false;
    }



    private int showLoginMenu(){
        printOut.println("1- Passenger");
        printOut.println("2- Receptionist");
        printOut.println("3- Admin");
        printOut.println("4- Exit");
        return 4;
    }

    private void login(){
        User userToLogin=userLogin();
        if (userToLogin instanceof Passenger)
        {
            passenger((Passenger) userToLogin);
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
        String userName;
        String password;

        printOut.println("Insert user name:");
        userName=scan.nextLine();
        printOut.println("Insert password:");
        password=scan.nextLine();
        for (User aux: getUserGenericList().getList()) {
        if (userName.equals(aux.getLoginName()) && password.equals(aux.getPassword()))
        {
            return aux;
        }
        }
        return null;
    }

    private int passengerMenu(){
        printOut.println("1 - To book a room");
        printOut.println("2 - See all your reservations");
        printOut.println("3 - Check a reservation");
        return 3;
    }




    private void passenger(Passenger passenger){
        if(passenger!=null) {
            boolean exit = false;
            do{
                int op = toCaptureInt(passengerMenu());
                switch (op){
                    case 1:
                        toBookARoom(passenger.getDni());
                        break;
                    case 2:
                        try {
                            Hotel.getPassengerReservations(passenger.getDni()).forEach(ob -> printOut.println(ob.toString()));
                        } catch (UserDoesNotExistException e) {
                            e.printStackTrace();
                        } catch (ReservationNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3:
                        try {
                            if(Hotel.getPassengerReservations(passenger.getDni()).size() > 0) {
                                Status status = chooseStatusReservation(showStatusReservation());
                                Reservation reservationChosen = checkStatusReservation(passenger.getDni(),status);
                                if(reservationChosen != null && reservationChosen.getStatus() != Status.CANCELLED) {
                                    printOut.println(reservationChosen.toString());
                                    reservationChosenMenu(reservationChosen);
                                }
                            }
                        } catch (UserDoesNotExistException e) {
                            e.printStackTrace();
                        } catch (ReservationNotFoundException e) {
                            e.printStackTrace();
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
        for(Room room : Hotel.getRoomGenericList().getList()){
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

    private int showMealPlan(){
        int i=1;
        for(MealPlan mealPlan : MealPlan.values()){
            printOut.println(i +" - " + mealPlan +" " + mealPlan.getDescription());
            i++;
        }
        return i;
    }

    private MealPlan chooseMealPlan(){
        int op= toCaptureInt(showMealPlan());
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
        int month=0;
        int year=0;
        int day=0;
        boolean flag= true;
        do {
            printOut.println("Year : ");
            try {
                year = scan.nextInt();
                if (year < 2021 || year > 2024) {
                    printOut.println("not acceptable year");
                    flag = false;
                }
            } catch (InputMismatchException e) {
                year = 0;
            }
            if (flag) {
                try {
                    printOut.println("Month : ");
                    month = scan.nextInt();
                    if (month < 1 || month > 12) {
                        printOut.println("not acceptable month");
                        flag = false;
                    }
                } catch (InputMismatchException e) {
                    month = 0;
                }
                if (flag) {
                    try {
                        printOut.println("Day : ");
                        day = scan.nextInt();
                        if (day < 1 || day > 30) {
                            printOut.println("not acceptable day");
                            flag = false;
                        }
                    } catch (InputMismatchException e) {
                        month = 0;
                    }
                }
            }
        }while (!flag);

        return LocalDate.of(year,month,day);
    }

    private int setDaysOfStay(){
        printOut.println("Days of Stay:");
        int days=0;
        try{
            days=scan.nextInt();
            if(days < 1 || days > 30){
                printOut.println("not acceptable count of day");
            }
        }catch (InputMismatchException e){
            days=0;
        }
        return days;
    }

    private LocalDate setDayOfExit(LocalDate arrival,int daysOfStay){
        LocalDate exitDay= null;
        try {
            exitDay = arrival.plusDays(daysOfStay);
        }catch (DateTimeException e){

        }
        return exitDay;
    }

    private Reservation toReserveRoom( LocalDate arrival,LocalDate exit,int roomNumberChoosed,MealPlan plan,String dniUser){
        Reservation reservation=null;
        for(Room room : Hotel.getRoomGenericList().getList()){
            if(room.getRoomNumber() == roomNumberChoosed){
                reservation = new Reservation(room,arrival, exit,plan,dniUser);
            }
        }
        return reservation;
    }

    private void confirmReservation(Reservation reserv, int dayOfStay){
        printOut.println(reserv.toString());
        printOut.println(" Is a total of $"+reserv.totalPriceReservation(dayOfStay));
        printOut.println("Confirm pay : \n 1-Confirm \n 2-No Confirm");
        int confirm = toCaptureInt(2);
        if(confirm == 1){
            reserv.getRoomToReserve().setStateRoom(State.RESERVED);
            reserv.setStatus(Status.CONFIRMED);
            Hotel.addReservation(reserv);
            printOut.println(Hotel.getReservationGenericList().getList().get(0).toString());//ver la utilidad al ejecutar. si solo muestra la reserva cambiar linea
        }else if(confirm == 2){
            reserv = null;
            printOut.println("The reservation is deleted");
        }
        else{
            printOut.println("Wrong option");
        }
    }

    private int showTypeOfRooms(){
        printOut.println("Insert number of type Room:");
        printOut.println("1_ Single Room");
        printOut.println("2_ Double Room");
        printOut.println("3_ Family Room");
        printOut.println("4_ King Room");
        return 4;
    }

    private int showReservationByStatus(List<Reservation> statusTypeReservation)throws ReservationNotFoundException{
        int i=0;
        if(statusTypeReservation.size() > 0) {
            printOut.println(" Your reservations");
            i = 1;
            for (Reservation reserv : statusTypeReservation) {

                printOut.println(i + " - " + reserv.toString());
                i++;
            }
        }else{
            throw new ReservationNotFoundException();
        }
        return i;
    }

    private void toBookARoom(String dniUser){
        int optionOfRoom=0;
        int dayOfStay = setDaysOfStay();
        LocalDate arrival = chooseArrivalDate();
        optionOfRoom=toCaptureInt(showTypeOfRooms());
        seeRoomFree(optionOfRoom);
        printOut.println("Insert number of room you want to book ");
        int room = scan.nextInt();
        scan.nextLine();
        Reservation newReserv = toReserveRoom(arrival, setDayOfExit(arrival,dayOfStay), room, chooseMealPlan(),dniUser);
        confirmReservation(newReserv, dayOfStay);
    }

    private void confirmCancellation(Reservation reservationChosen){
        if(reservationChosen.getStatus().equals(Status.CONFIRMED)) {
            printOut.println("Confirm to cancel reservation ? \n 1- yes \n 2- No");
            int i = toCaptureInt(2);
            if (i == 1) {
                if (toCancelReservation(reservationChosen)) {
                    printOut.println("Reservation canceled");
                } else {
                    printOut.println("Something went wrong with the cancellation");
                }
            }
        }
    }


    private Reservation checkStatusReservation(String dniUser, Status status){
        int cant = 0;
        try {
            cant = showReservationByStatus(Hotel.getStatusReservations(Hotel.getPassengerReservations(dniUser),status));
        } catch (ReservationNotFoundException e) {
            e.printStackTrace();
        } catch (UserDoesNotExistException e) {
            e.printStackTrace();
        }
        if(cant > 0) {
            printOut.println("Choose the reservation you want to check");
            int index = toCaptureInt(cant);
            try {
                return Hotel.getStatusReservations(Hotel.getPassengerReservations(dniUser), status).get(index - 1);
            } catch (UserDoesNotExistException e) {
                e.printStackTrace();
            } catch (ReservationNotFoundException e) {
                e.printStackTrace();
            }
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
        int x = toCaptureInt(i);
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


    private int showProductToConsume(){
        int i=1;
        for(ProductToConsume prod : ProductToConsume.values()){
            printOut.println(i +"-"+prod + " price: $"+ prod.getPrice());
            i++;
        }
        return i;
    }

    private int chooseAnItemProduct() {
        int cant = showProductToConsume();
        printOut.println("Choose item ");
        int op = toCaptureInt(cant);
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

    private int showReservationChosenMenu(){
        printOut.println("1 - Cancel a reservation");
        printOut.println("2 - Add an order");
        printOut.println("3 - Check your consumptions");
        return  3;
    }
    private void checkPassengerConsumptions(Reservation reservationChosen) throws ProductNotFoundException{
        if(reservationChosen.getRoomToReserve().getConsumed().size() > 0) {
            double totalPrice =0;
            for (ProductToConsume prod : reservationChosen.getRoomToReserve().getConsumed()) {
                printOut.println(prod +" $"+prod.getPrice());
                totalPrice+=prod.getPrice();
            }
            if(reservationChosen.getStatus().equals(Status.ACTIVE))
            printOut.println("Total price : $"+totalPrice);
        }else{
            throw new ProductNotFoundException();
        }
    }
     private void reservationChosenMenu(Reservation reservationChosen){
        if(reservationChosen != null){
            boolean exit = false;
            do{
                int op = toCaptureInt(showReservationChosenMenu());
                switch (op) {
                    case 1:
                        confirmCancellation(reservationChosen);
                        break;
                    case 2:
                        if(reservationChosen.getStatus().equals(Status.ACTIVE))
                            addAnItemToList(reservationChosen, chooseAnItemProduct());
                        break;
                    case 3:
                        try {
                            checkPassengerConsumptions(reservationChosen);
                        } catch (ProductNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 4:
                        exit =true;
                        break;
                }

            }while(!exit);
        }
     }


    private int showRecepcionistMenu()
    {
        printOut.println("1_Check in");
        printOut.println("2_Check out");
        printOut.println("3_Show consumption of room");
        printOut.println("4_Reservation");
        printOut.println("5_Exit");
        return 5;
    }
    private void receptionist(){
        boolean exit=false;
        int option;
        String dniUser;
        int roomNumber=0;
        while (!exit)
        {
            option=toCaptureInt(showRecepcionistMenu());
            switch (option)
            {
                case 1:
                    checkIn();
                    showListOfRoom();
                    break;
                case 2:
                    checkOut();
                    try {
                        showReservation();
                    } catch (ReservationNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    printOut.println("Enter room number:");
                    scan.nextLine();
                    do {
                        try {
                            roomNumber = scan.nextInt();
                            showConsumeOfRoom(roomNumber);
                            break;
                        } catch (InputMismatchException e) {
                            roomNumber = 0;
                        } catch (RoomDoesNotExistException e) {
                            e.printStackTrace();
                            roomNumber = 0;
                        } catch (ReservationNotFoundException e) {
                            e.printStackTrace();
                        } catch (ProductNotFoundException e) {
                            e.printStackTrace();
                        }
                    }while (roomNumber == 0);
                case 4:
                    printOut.println("Enter DNI of passenger:");
                    scan.nextLine();
                    dniUser=scan.nextLine();
                    try {
                        if(Hotel.getPassengerReservations(dniUser).size() > 0) {
                            Status status = chooseStatusReservation(showStatusReservation());
                            Reservation reservationChosen = checkStatusReservation(dniUser,status);
                            if(reservationChosen != null && reservationChosen.getStatus() != Status.CANCELLED) {
                                printOut.println(reservationChosen.toString());
                                reservationChosenMenu(reservationChosen);
                            }
                        }
                    } catch (UserDoesNotExistException e) {
                        e.printStackTrace();
                    } catch (ReservationNotFoundException e) {
                        e.printStackTrace();
                    }
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
        int numberOfRoom;
        String dniUser="0";
        boolean result;
        printOut.println("wants to make the entry of a passenger. Enter 1 or if you want to enter a reservation enter 2");
        option=toCaptureInt(2);
        if (option==1)
        {
            try {
                dniUser=register();
            } catch (UserAlreadyExistException e) {
                e.printStackTrace();
            }
            int dayOfStay = setDaysOfStay();
            LocalDate arrival = LocalDate.now();
            optionOfRoom=toCaptureInt(showTypeOfRooms());
            seeRoomFree(optionOfRoom);
            printOut.println("Insert number of room you want to reserve ");
            do {
                try {
                    numberOfRoom = scan.nextInt();
                } catch (InputMismatchException e) {
                    numberOfRoom = 0;
                }
            }while (numberOfRoom == 0);
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
                printOut.println("There is no coincidence with DNI");
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
        Reservation reservationCheckOut= null;
        try {
            reservationCheckOut = searchReservation(dniPassenger);
        } catch (ReservationNotFoundException e) {
            e.printStackTrace();
        } catch (UserDoesNotExistException e) {
            e.printStackTrace();
        }
        if (reservationCheckOut!=null)
        {
            Passenger passengerToCheckOut=searchPassengerInList(dniPassenger);
            result=changeStateOfRoom(passengerToCheckOut,reservationCheckOut.getRoomToReserve(),State.FREE);
            result=deleteReservationInList(reservationCheckOut);


        }
    }
    private void showConsumeOfRoom(int roomNumber) throws ReservationNotFoundException, ProductNotFoundException, RoomDoesNotExistException
    {
        double totalPrice=0;
        printOut.println("Product:");
        if(Hotel.getRoomGenericList().getList().size() > 0) {
            for (Room roomToConsume : Hotel.getRoomGenericList().getList()) {
                if (roomNumber == roomToConsume.getRoomNumber()) {
                    if (roomToConsume.getConsumed() != null) {
                        for (ProductToConsume productToConsumeForRoom : roomToConsume.getConsumed()) {
                            printOut.println(productToConsumeForRoom);
                            totalPrice = totalPrice + productToConsumeForRoom.getPrice();

                        }
                    } else {
                        throw new RoomDoesNotExistException();
                    }
                }else{
                    throw new ProductNotFoundException();
                }
            }
        }else{
            throw new ReservationNotFoundException();
        }
    }
    private int showAdminMenu()
    {
        printOut.println("1_Register receptionist ");
        printOut.println("2_Register passanger");
        printOut.println("3_Check in");
        printOut.println("4_Check out");
        printOut.println("5_Exit");
        return 5;
    }
    private void admin(){
        boolean exit=false;
        int option;
        while (!exit)
        {
            option=toCaptureInt(showAdminMenu());
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
