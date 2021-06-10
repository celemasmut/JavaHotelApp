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
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


import static HotelApp.hotel.Hotel.*;

public class Menu {
    private PrintStream printOut;
    DataFile dataFile= new DataFile();


    Scanner scan = new Scanner(System.in);


    public Menu(){
        SaveInfoUsers saveinfo = new SaveInfoUsers();
        saveinfo=dataFile.readInfo("files/user.json");
        addUsersToList(saveinfo);
        //setRoom(dataFile.readRoomJson("files/room.json"));
        showListOfRoom();
        printOut = System.out;
    }

    private void saveInfo(){
        SaveInfoUsers saveinfo = new SaveInfoUsers();
        saveinfo.addUsers(getUserGenericList().getList());
       // dataFile.writeInfo(saveinfo,"files/user.json");
        dataFile.writeJsonBookings(getReservationGenericList().getList(),"files/booking.json");
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


    public void initiate() {
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
                    printOut.println("Good Bye !! See you soon!!");
                    saveInfo();
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
    public void registerReceptionist(){
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

    public User findReceptionist(int fileNumber)throws UserDoesNotExistException{
        if(Hotel.getUserGenericList().getList().size() > 0 ){
            for (User user : Hotel.getUserGenericList().getList()){
                if (user instanceof Receptionist)
                {
                    if(((Receptionist) user).getFileNumber()==(fileNumber)) {
                        return user;
                    }
                }
            }
        }else{
            throw new UserDoesNotExistException("The receptionist does not exist");
        }
        return null;
    }

    private int findReceptionistInlist(int fileNumber){
        int pos = -1;
        ///primero hago una lista de personas
        List<Receptionist> receptionistList = new ArrayList<>();
        for (User user : Hotel.getUserGenericList().getList()){
            if (user instanceof Receptionist) {
                receptionistList.add((Receptionist) user);
            }
        }

        for(int i = 0; i < receptionistList.size() && pos==-1;i++){
            if(receptionistList.get(i).getFileNumber() == fileNumber ){
                pos = i;
            }
        }
        return pos;
    }

    private int showLoginMenu(){
        printOut.println("1- Passenger");
        printOut.println("2- Receptionist");
        printOut.println("3- Admin");
        printOut.println("4- Exit");
        return 4;
    }

    private void login() {
        User userToLogin= null;
        try {
            userToLogin = userLogin();
        } catch (UserDoesNotExistException e) {
            e.printStackTrace();
        }
        if(userToLogin.getState()==1){//si esta activo
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
        else{
            printOut.println("This user was removed");
        }
    }

    private User userLogin () throws UserDoesNotExistException
    {
        String userName;
        String password;
        printOut.println("Insert user name:");
        userName=scan.next();
        scan.nextLine();
        printOut.println("Insert password:");
        password=scan.next();
        if(getUserGenericList().getList() != null) {
            for (User aux : getUserGenericList().getList()) {
                if (userName.equals(aux.getLoginName()) && password.equals(aux.getPassword())) {
                    return aux;
                }
            }
        }else{
            throw new UserDoesNotExistException("The user does not exist, please first go to register");
        }
        return null;
    }

    private int passengerMenu(){
        printOut.println("1 - To book a room");
        printOut.println("2 - See all your reservations");
        printOut.println("3 - Check a reservation");
        printOut.println("4 - exit");

        return 4;
    }

    private void showListReservation(String dniUser){
        try {
            Hotel.getPassengerReservations(dniUser).forEach(ob -> printOut.println(ob.toString()));
        }catch (UserDoesNotExistException e){
            e.printStackTrace();
        }catch (ReservationNotFoundException e){
            e.printStackTrace();
        }
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
                       showListReservation(passenger.getDni());
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
    private List<Room> getAvailableRooms(LocalDate arrival, LocalDate leave){
        List<Room> availableRoomsList = new ArrayList<>();
        for(Room room : Hotel.getRoomGenericList().getList()){
            if(isRoomAvailable(room,arrival,leave)){
                availableRoomsList.add(room);
            }
        }
        return availableRoomsList;
    }
    private boolean isRoomAvailable(Room room,LocalDate arrival, LocalDate leave) {
        if (room != null) {
            if (Hotel.getReservationGenericList().getList().size() > 0) {
                for (Reservation booking : Hotel.getReservationGenericList().getList()) {
                    if (room.equals(booking.getRoomToReserve())) {
                        if (booking.getArrivalDay().isBefore(leave)) {
                            return false;
                        }
                        if(booking.getDayOfExit().isAfter(arrival) && booking.getDayOfExit().isBefore(leave)){
                            return false;
                        }
                        if((booking.getArrivalDay().isEqual(arrival) && booking.getDayOfExit().isEqual(leave))){
                            return false;
                        }
                    }
                }
            }
            if(room.getStateRoom() == State.AVAILABLE)
                return true;
        }
        return false;
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

    private int setInt(){
        int i=0;
        do {
            try {
                i = scan.nextInt();
            } catch (InputMismatchException e) {
                printOut.println("Error in the input, try again , set a number");
                i = 0;
                scan.next();
            }
        }while (i==0);
        return i;
    }

    private LocalDate chooseArrivalDate(){
        printOut.println("Choose the arrival date yyyy/MM/dd");
        int month=0;
        int year=0;
        int day=0;
        boolean flag= true;
        do {
            printOut.println("Year : ");
            year= setInt();
            flag = true;
            if (year < 2021 || year > 2024) {
                printOut.println("not acceptable year");
                flag = false;
            }
            if (flag) {
                printOut.println("Month : ");
                month = setInt();
                flag = true;
                if (month < 1 || month > 12) {
                    printOut.println("not acceptable month");
                    flag = false;
                    }
                }
                if (flag) {
                    printOut.println("Day : ");
                    day = setInt();
                    flag = true;
                    if (day < 1 || day > 30) {
                        printOut.println("not acceptable day");
                        flag = false;
                        }
                    }
                }while (!flag);
        return LocalDate.of(year,month,day);
    }

    private int setDaysOfStay(){
        int days=0;
        while(days == 0) {
            try {
                printOut.println("Days of Stay:");
                days = scan.nextInt();
                if (days < 1 || days > 30) {
                    printOut.println("not acceptable count of day");
                    days=0;
                }
            } catch (InputMismatchException e) {
                days = 0;
                scan.next();
            }
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

    private void showAvailableRooms(List<Room> r){
        r.forEach(room -> printOut.println(room.toString()));
    }

    private void toBookARoom(String dniUser){
        int dayOfStay = setDaysOfStay();
        LocalDate arrival = chooseArrivalDate();
        LocalDate dayOfLeave = setDayOfExit(arrival,dayOfStay);
        showAvailableRooms(getAvailableRooms(arrival,dayOfLeave));
        printOut.println("Insert number of room you want to book ");
        int room = scan.nextInt();
        scan.nextLine();
        Reservation newReserv = toReserveRoom(arrival, dayOfLeave, room, chooseMealPlan(),dniUser);
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
            LocalDate dayOfLeave = setDayOfExit(arrival,dayOfStay);
            showAvailableRooms(getAvailableRooms(arrival,dayOfLeave));
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
            Reservation newReserv = toReserveRoom(arrival, dayOfLeave, numberOfRoom, chooseMealPlan(),dniUser);
            Passenger passengerToRoom = null;
            try {
                passengerToRoom = searchPassengerInList(dniUser);
            } catch (UserDoesNotExistException e) {
                e.printStackTrace();
            }
            result=changeStateOfRoom(passengerToRoom,newReserv.getRoomToReserve(),State.OCCUPIED);


        }else if (option==2){
            printOut.println("Insert DNI :");
            scan.nextLine();
            dniUser= scan.next();
            Reservation reservToActivate=searchReservationInList(dniUser);
            if (reservToActivate!=null)
            {
                Passenger passengerToRoom= null;
                try {
                    passengerToRoom = searchPassengerInList(dniUser);
                } catch (UserDoesNotExistException e) {
                    e.printStackTrace();
                }
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
            Passenger passengerToCheckOut= null;
            try {
                passengerToCheckOut = searchPassengerInList(dniPassenger);
            } catch (UserDoesNotExistException e) {
                e.printStackTrace();
            }
            result=changeStateOfRoom(passengerToCheckOut,reservationCheckOut.getRoomToReserve(),State.AVAILABLE);
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

    private int showAdminMenu() {
        printOut.println("1_Options of Register");
        printOut.println("2_Functions of Receptionist");
        printOut.println("3_Options of rooms");
        printOut.println("4_Options of Reservation");
        printOut.println("5_Delete users");
        printOut.println("6_View user lists");
        printOut.println("7_BackUp");
        printOut.println("8_Exit");
        return 8;
    }

    private void admin() {
        boolean exit = false;
        int option;
        while (!exit) {
            option = toCaptureInt(showAdminMenu());
            switch (option) {
                case 1:
                    registerAll();
                    break;
                case 2:
                    receptionist();
                    break;
                case 3:
                    optionsRooms();
                    break;
                case 4:
                    optionsOfReservas();
                    break;
                case 5:
                    deleteUsers();
                    break;
                case 6:
                    try {
                        showUsers();
                    } catch (UserDoesNotExistException e) {
                        e.printStackTrace();
                    }
                    break;
                case 7:
                    backUp();
                    break;
                case 8:
                    exit = true;
                    break;

            }
        }
    }


    public void registerAll(){
        boolean exit = false;
        int option;
        while (!exit) {
            option = toCaptureInt(showRegisterAllMenu());
            switch (option) {
                case 1:
                    registerReceptionist();
                    break;
                case 2:
                    try {
                        register();
                    }catch (UserAlreadyExistException e){
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    exit = true;
                    break;
            }

        }
    }
    private int showRegisterAllMenu() {
        printOut.println("1_Register receptionist ");
        printOut.println("2_Register passenger");
        printOut.println("3_Exit");
        return 3;
    }

    private void optionsRooms(){
        boolean exit = false;
        int option;
        while (!exit) {
            option = toCaptureInt(showAdminRoomMenu());
            switch (option) {
                case 1:
                    showListOfRoomXState(State.AVAILABLE);
                    showListOfRoomXState(State.CLEANING);
                    showListOfRoomXState(State.OCCUPIED);
                    showListOfRoomXState(State.IN_MAINTENANCE);
                    showListOfRoomXState(State.RESERVED);
                    ///mostrar lista de habitaciones
                    break;
                case 2:
                    int numberRoom;
                    printOut.println("Enter room number");
                    numberRoom = setInt();
                    changeStateRoom(numberRoom);
                    //cambiar el estado de la habitacion
                    break;
                case 3:
                    exit = true;
                    break;
            }

        }
    }

    private int showAdminRoomMenu() {
        printOut.println("1_Show room list");
        printOut.println("2_Change state of room");
        printOut.println("3_Exit");
        return 3;
    }

    private void changeStateRoom(int numberRoom){
        printOut.println("choose a state");
        printOut.println("1-Available");
        printOut.println("2-Reserved");
        printOut.println("3-Occupied");
        printOut.println("4-Cleaning");
        printOut.println("5-In maintenance");
        int numberState = toCaptureInt(5);
        switch (numberState){
            case 1:
                changeStateOfRoomXNumber(numberRoom, State.AVAILABLE);
                break;
            case 2:
                changeStateOfRoomXNumber(numberRoom, State.RESERVED);
                break;
            case 3:
                changeStateOfRoomXNumber(numberRoom, State.OCCUPIED);
                break;
            case 4:
                changeStateOfRoomXNumber(numberRoom, State.CLEANING);
                break;
            case 5:
                changeStateOfRoomXNumber(numberRoom, State.IN_MAINTENANCE);
                break;
        }
    }

    private void optionsOfReservas(){
        boolean exit = false;
        int option;
        printOut.println("Enter passenger dni");
        String dniUser = scan.toString();
        try {
            if(searchPassengerInList(dniUser) != null) {
                while (!exit) {
                    option = toCaptureInt(showAdminReservationMenu());
                    switch (option) {
                        case 1:
                            showListReservation(dniUser);
                            break;
                        case 2:
                            printOut.println("Enter Reservation number");
                            int reservationNumber = setInt();
                            changeStatusOfReserve(reservationNumber);
                            //cambiar el estado de una reserva
                            break;
                        case 3:
                            exit = true;
                            break;
                    }

                }
            }
        } catch (UserDoesNotExistException e) {
            e.printStackTrace();
        }
    }

    private int showAdminReservationMenu() {
        printOut.println("1_Show reservation list");
        printOut.println("2_Change state of a reservation");
        printOut.println("3_Exit");
        return 3;
    }

    private void changeStatusOfReserve(int reservationNumber){
        printOut.println("choose a status");
        printOut.println("1-Confirmed");
        printOut.println("2-Cancelled");
        printOut.println("3-Completed");
        printOut.println("4-Active");
        int numberStatus = toCaptureInt(4);
        switch (numberStatus){
            case 1:
                statusOfReserveChange(reservationNumber, Status.CONFIRMED);
                break;
            case 2:
                statusOfReserveChange(reservationNumber, Status.CANCELLED);
                break;
            case 3:
                statusOfReserveChange(reservationNumber, Status.COMPLETED);
                break;
            case 4:
                statusOfReserveChange(reservationNumber, Status.ACTIVE);
                break;
        }
    }

    protected static boolean statusOfReserveChange(int reservationNumber, Status status)
    {
        getReservationGenericList().getList().get(reservationNumber).setStatus(status);
        return true;
    }

    private void deleteUsers(){
        boolean exit = false;
        int option;
        while (!exit) {
            option = toCaptureInt(showAdminDeletMenu());
            switch (option) {
                case 1:
                    printOut.println("Enter passenger dni");
                    String dniUser = scan.next();
                    Passenger find = null;
                    try {
                        find = searchPassengerInList(dniUser);
                    } catch (UserDoesNotExistException e) {
                        e.printStackTrace();
                    }
                    if(find != null){
                        find.deleteLogic();
                    }
                    break;
                case 2:
                    printOut.println("Enter receptionist file number");
                    int fileNumber = setInt();
                    Receptionist findReceptionist = null;
                    try {
                        findReceptionist = (Receptionist) findReceptionist(fileNumber);
                    } catch (UserDoesNotExistException e) {
                        e.printStackTrace();
                    }
                    if(findReceptionist != null){
                        findReceptionist.deleteLogic();
                    }
                    break;
                case 3:
                    showListUsersDeleted();
                    break;
                case 4:
                    exit = true;
                    break;
            }

        }
    }

    private int showAdminDeletMenu() {
        printOut.println("1_Delete Passenger");
        printOut.println("2_Delete Receptionist");
        printOut.println("3_Show Delete Users");
        printOut.println("4_Exit");
        return 4;
    }

    private void showListUsersDeleted(){
        for(User user: Hotel.getUserGenericList().getList()){
            if(user instanceof Passenger || user instanceof Receptionist){//significa que esta eliminado
                if(user.getState() == 0){
                    printOut.println(user);
                }
            }
        }
    }

    private void backUp(){
        SaveInfoUsers saveinfo;
        DataFile data= new DataFile();

        printOut.println("creating backup ...");
        saveinfo=data.readInfo("files/users.json");
        data.writeInfo(saveinfo, "files/backUpUsers.json ");
        data.writeJsonBookings(Hotel.getReservationGenericList().getList(),"files/backUpBooking.json");
       // data.writeJsonRooms(Hotel.getRoomGenericList().getList(),"files/backUpRoom.json");
        printOut.println("finalized");

    }
}
