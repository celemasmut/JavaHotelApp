package HotelApp.hotel;

import HotelApp.datafile.DataFile;
import HotelApp.datafile.SaveInfoUsers;
import HotelApp.datafile.SaveTypeRoom;
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
    private final PrintStream printOut;
    DataFile dataFile= new DataFile();


    Scanner scan = new Scanner(System.in);


    public Menu() {
        SaveInfoUsers saveinfo ;
        saveinfo=dataFile.readInfo("files/user.json");
        addUsersToList(saveinfo);
        if(getUserGenericList().getList() == null){
            addUser(new Admin("admin2","admin2"));
        }
        SaveTypeRoom saveRooms;
        saveRooms = dataFile.readRoomJson("files/room.json");
        addRoomsToList(saveRooms);
        setReservationGenericList(dataFile.readReservationJson("files/booking.json"));
        printOut = System.out;
    }

    private void saveInfo(){
        SaveInfoUsers saveinfo = new SaveInfoUsers();
        saveinfo.addUsers(getUserGenericList().getList());
        dataFile.writeInfo(saveinfo,"files/user.json");
        SaveTypeRoom saveRooms = new SaveTypeRoom();
        saveRooms.addRoomsToList(Hotel.getRoomGenericList().getList());
        dataFile.writeJsonRooms(saveRooms,"files/room.json");
        dataFile.writeJsonBookings(getReservationGenericList().getList(),"files/booking.json");
    }

    private int toCaptureInt(int options){
        int input;
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
                    try {
                        login();
                    } catch (UserDoesNotExistException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    saveInfo();
                    printOut.println("Good Bye !! See you soon!!");
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

    private Passenger register() throws UserAlreadyExistException {
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
            Passenger newPassenger=new Passenger(userName,password,name,dni,homeTown,homeAddress);
            Hotel.addUser(newPassenger);
            printOut.println("New Passenger registered");
            return newPassenger;
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

    private void login() throws UserDoesNotExistException {
        User userToLogin;
        userToLogin = userLogin();
        if(userToLogin != null) {
            if (userToLogin.getState() == 1) {
                if (userToLogin instanceof Passenger) {
                    passenger((Passenger) userToLogin);
                } else if (userToLogin instanceof Receptionist) {
                    receptionist();
                }
                if (userToLogin instanceof Admin) {
                    admin();
                }
            }
        }else{
            throw new UserDoesNotExistException();
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
        }else {
            throw new UserDoesNotExistException();
        }
        return null;
    }

    private int passengerMenu(){
        printOut.println("1 - To book a room");
        printOut.println("2 - See all your reservations");
        printOut.println("3 - Check a reservation");
        printOut.println("4 - Edit Profile");
        printOut.println("5 - exit");

        return 5;
    }

    private void showListReservation(String dniUser){
        try {
            Hotel.getPassengerReservations(dniUser).forEach(ob -> printOut.println(ob.toString()));
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
                                    printOut.println(reservationChosen);
                                    reservationChosenMenu(reservationChosen);
                                }
                            }
                        }  catch (ReservationNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 4:
                        passenger=editProfileOfPassenger(passenger);

                        break;
                    case 5:
                        sortReservationByDates();
                        exit=true;
                        break;
                }
                saveInfo();
            }while (!exit);
        }
    }
    private Passenger editProfileOfPassenger(Passenger passengerToEdit)
    {
        boolean exit =false;
        int posInList;
        String aux;
        posInList=Hotel.getUserGenericList().getList().indexOf(passengerToEdit);
        do {
            int op = toCaptureInt(showMenuEditProfileOfPassenger());
            switch (op){
                case 1:
                    printOut.println("Enter new name for you account.");
                    scan.nextLine();
                    aux=scan.next();
                    ((Passenger) Hotel.getUserGenericList().getList().get(posInList)).setName(aux);
                    passengerToEdit.setName(aux);
                    break;
                case 2:
                    printOut.println("Enter new DNI for you account");
                    scan.nextLine();
                    aux=scan.next();
                    ((Passenger) Hotel.getUserGenericList().getList().get(posInList)).setDni(aux);
                    passengerToEdit.setDni(aux);
                    break;
                case 3:
                    printOut.println("Enter new hometown:");
                    scan.nextLine();
                    aux=scan.next();
                    ((Passenger) Hotel.getUserGenericList().getList().get(posInList)).setHometown(aux);
                    passengerToEdit.setHometown(aux);
                    break;
                case 4:
                    printOut.println("Enter new home adress");
                    scan.nextLine();
                    aux=scan.next();
                    ((Passenger) Hotel.getUserGenericList().getList().get(posInList)).setHomeAddress(aux);
                    passengerToEdit.setHometown(aux);
                    break;
                case 5:
                    printOut.println("Enter new Login Name.");
                    scan.nextLine();
                    aux=scan.next();
                    Hotel.getUserGenericList().getList().get(posInList).setLoginName(aux);
                    passengerToEdit.setLoginName(aux);
                    break;
                case 6:
                    printOut.println("Enter new Password.");
                    scan.nextLine();
                    aux=scan.next();
                    printOut.println("Enter password again.");
                    scan.nextLine();
                    String aux2=scan.next();
                    if (aux.equals(aux2))
                    {
                        Hotel.getUserGenericList().getList().get(posInList).setPassword(aux);
                        passengerToEdit.setPassword(aux);
                    }
                    else
                    {
                        printOut.println("Passwords do not match");
                    }
                    break;
                case 7:
                    exit=true;
                    break;
            }
        }while(!exit);
        printOut.println(( Hotel.getUserGenericList().getList().get(posInList)).toString());
        return passengerToEdit;
    }

    private int showMenuEditProfileOfPassenger(){
        printOut.println("1- Edit Name");
        printOut.println("2- Edit DNI");
        printOut.println("3- Edit Hometown");
        printOut.println("4- Edit Homeaddress");
        printOut.println("5- Edit Login Name");
        printOut.println("6- Edit Password");
        printOut.println("7- Exit");

        return 7;
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
                        if ( booking.getArrivalDay().isBefore(arrival) && booking.getDayOfExit().isAfter(leave)) {
                            return false;
                        }
                        if(booking.getDayOfExit().isBefore(arrival) || booking.getDayOfExit().isEqual(arrival)){
                            return true;
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
            return room.getStateRoom() == State.AVAILABLE;
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
        int i;
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
        int year;
        int day=0;
        boolean flag;
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
            e.printStackTrace();
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
        int i;
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

    private int confirmAvailableRoomChoosen(List<Room> availableRooms){
       int roomNumber;
        do {
             roomNumber = setInt();
            for (Room room : availableRooms) {
                if (roomNumber == room.getRoomNumber()) {
                    return roomNumber;
                }
            }
            printOut.println("That room number does not exist or is not available");
            roomNumber=0;
        }while (roomNumber == 0);
        return roomNumber;
    }

    private void toBookARoom(String dniUser){
        int dayOfStay = setDaysOfStay();
        LocalDate arrival = chooseArrivalDate();
        LocalDate dayOfLeave = setDayOfExit(arrival,dayOfStay);
        List<Room> availableRooms;
        availableRooms = getAvailableRooms(arrival,dayOfLeave);
        showAvailableRooms(availableRooms);
        printOut.println("Insert number of room you want to book ");
        int room = confirmAvailableRoomChoosen(availableRooms);
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
        }
        if(cant > 0) {
            printOut.println("Choose the reservation you want to check");
            int index = toCaptureInt(cant);
            try {
                return Hotel.getStatusReservations(Hotel.getPassengerReservations(dniUser), status).get(index - 1);
            }  catch (ReservationNotFoundException e) {
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
        return toCaptureInt(i);
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
        return toCaptureInt(cant);
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
        printOut.println("4 - exit");

        return  4;
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
        printOut.println("3_Show consumption of passenger");
        printOut.println("4_Reservation");
        printOut.println("5_Exit");
        return 5;
    }
    private void receptionist(){
        boolean exit=false;
        int option;
        String dniUser;
        while (!exit)
        {
            option=toCaptureInt(showRecepcionistMenu());
            switch (option)
            {
                case 1:
                    checkIn();
                    break;
                case 2:
                    checkOut();
                    break;
                case 3:
                    printOut.println("Enter DNI of passenger:");
                    scan.nextLine();
                    dniUser=scan.nextLine();
                    try {
                        if(Hotel.getPassengerReservations(dniUser).size() > 0) {
                            Status status = chooseStatusReservation(showStatusReservation());
                            Reservation reservationChosen = getBookingConsumptions(dniUser,status);
                            showConsumptionPassenger(reservationChosen);
                            }
                    }  catch (ReservationNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    printOut.println("Enter DNI of passenger:");
                    scan.nextLine();
                    dniUser=scan.nextLine();
                    try {
                        if (Hotel.getPassengerReservations(dniUser).size() > 0) {
                            Status status = chooseStatusReservation(showStatusReservation());
                            Reservation reservationChosen = checkStatusReservation(dniUser, status);
                            if (reservationChosen != null && reservationChosen.getStatus() != Status.CANCELLED) {
                                printOut.println(reservationChosen);
                                reservationChosenMenu(reservationChosen);
                            }
                        }
                    }catch (ReservationNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case 5:
                    sortReservationByDates();
                    saveInfo();
                    exit=true;
                    break;

            }
            saveInfo();
        }
    }

    private void showConsumptionPassenger(Reservation reservationChosen){
        if(reservationChosen != null && reservationChosen.getStatus() != Status.CANCELLED) {
            double totalPrice =0;
            for(ProductToConsume product : reservationChosen.getRoomToReserve().getConsumed()){
                totalPrice+= product.getPrice();
            }
            printOut.println("Product : \n "+ reservationChosen.getRoomToReserve().getConsumed());
            printOut.println("Total price of consumptions: $"+totalPrice);
        }
    }

    private int showCantReservationByStatus(List<Reservation> statusTypeReservation)throws ReservationNotFoundException{
        int i;
        if(statusTypeReservation.size() > 0) {
            printOut.println(" Your reservations");
            i = 1;
            for (Reservation reserv : statusTypeReservation) {

                printOut.println(i + " - \n" + "Room : " + reserv.getRoomToReserve().getRoomNumber());
                i++;
            }
        }else{
            throw new ReservationNotFoundException();
        }
        return i;
    }

    private Reservation getBookingConsumptions(String dni, Status status){
        int cant = 0;
        try {
            cant = showCantReservationByStatus(Hotel.getStatusReservations(Hotel.getPassengerReservations(dni),status));
        } catch (ReservationNotFoundException e) {
            e.printStackTrace();
        }
        if(cant > 0) {
            printOut.println("Choose the reservation you want to check the consumptions");
            int index = toCaptureInt(cant);
            try {
                return Hotel.getStatusReservations(Hotel.getPassengerReservations(dni), status).get(index - 1);
            }  catch (ReservationNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;

    }
    private void checkIn(){
        int option;
        int numberOfRoom;
        Passenger p = new Passenger();
        printOut.println("wants to make the entry of a passenger. Enter 1 or if you want to enter a reservation enter 2");
        option=toCaptureInt(2);
        if (option==1)
        {
            try {
                p=register();
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
                    numberOfRoom = setInt();
                } catch (InputMismatchException e) {
                    numberOfRoom = 0;
                }
            }while (numberOfRoom == 0);
            scan.nextLine();
            //reservar
            Reservation newReserv = toReserveRoom(arrival, dayOfLeave, numberOfRoom, chooseMealPlan(),p.getDni());
            newReserv.getRoomToReserve().setStateRoom(State.OCCUPIED);
            newReserv.getRoomToReserve().setOccupant(p);
            newReserv.setStatus(Status.ACTIVE);
            addReservation(newReserv);

        }else if (option==2){
            printOut.println("Insert DNI :");
            scan.nextLine();
            String dni= scan.next();
            try {
                p=searchPassengerInList(dni);
            } catch (UserDoesNotExistException e) {
                e.printStackTrace();
            }
            Reservation reservToActivate=searchReservationInList(dni);
            if (reservToActivate!=null && reservToActivate.getStatus() == Status.CONFIRMED)
            {
                reservToActivate.getRoomToReserve().setStateRoom(State.OCCUPIED);
                reservToActivate.getRoomToReserve().setOccupant(p);
                reservToActivate.setStatus(Status.ACTIVE);
                printOut.println("Check in on reservation "+ reservToActivate);
            }else {
                printOut.println("There is no coincidence with DNI");
            }
        }
    }
    private void checkOut()
    {
        String dniPassenger;
        printOut.println("Insert DNI :");
        scan.nextLine();
        dniPassenger=scan.next();
        Reservation reservationChosen = checkStatusReservation(dniPassenger,Status.ACTIVE);
        if (reservationChosen!=null && reservationChosen.getStatus() == Status.ACTIVE)
        {
            showConsumptionPassenger(reservationChosen);
            reservationChosen.setStatus(Status.COMPLETED);
            printOut.println("Check out of passenger : "+ reservationChosen.getRoomToReserve().getOccupant().toString());
            printOut.println(reservationChosen);
        }else{
            printOut.println( "The is not reservation to do check out");
        }
    }

    private int showAdminMenu() {
        printOut.println("1_Options of Register");
        printOut.println("2_Functions of Receptionist");
        printOut.println("3_Options of rooms");
        printOut.println("4_Options of Reservation");
        printOut.println("5_Delete users");
        printOut.println("6_View user lists");
        printOut.println("7_Edit Passenger");
        printOut.println("8_Edit Receptionist");
        printOut.println("9_Back Up");
        printOut.println("10_Exit");

        return 10;
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
                    printOut.println("Enter DNI of passenger to edit");
                    scan.nextLine();
                    String aux= scan.next();
                    Passenger passengerAux= null;
                    try {
                        passengerAux = searchPassengerInList(aux);
                        if (passengerAux != null){
                            editProfileOfPassenger(passengerAux);
                        }
                    } catch (UserDoesNotExistException e) {
                        e.printStackTrace();
                    }
                    break;
                case 8:
                    editReceptionist();
                    break;
                case 9:
                    backUp();
                    break;
                case 10:
                    exit = true;
                    break;
            }
            saveInfo();
        }
    }
    private void editReceptionist()
    {
        String aux;
        boolean answer;
        boolean exit= false;
        printOut.println("Insert the receptionist file number you want to edit ");
        int fileNumber = toCaptureInt(2);
        Receptionist receptionistToEdit = null;
        try {
            receptionistToEdit = searchReceptionist(fileNumber);
        } catch (UserDoesNotExistException e) {
            e.printStackTrace();
        }
        int posInList;
        posInList=Hotel.getUserGenericList().getList().indexOf(receptionistToEdit);
        do {
           int option = toCaptureInt(showMenuEditRecepcionist());
            switch (option)
            {
                case 1:
                    printOut.println("Enter new login name.");
                    scan.nextLine();
                    aux=scan.next();
                    Hotel.getUserGenericList().getList().get(posInList).setLoginName(aux);
                    receptionistToEdit.setLoginName(aux);
                    break;
                case 2:
                    printOut.println("Enter new Password.");
                    scan.nextLine();
                    aux=scan.next();
                    printOut.println("Enter password again.");
                    scan.nextLine();
                    String aux2=scan.next();
                    if (aux.equals(aux2))
                    {
                        Hotel.getUserGenericList().getList().get(posInList).setPassword(aux);
                        receptionistToEdit.setPassword(aux);
                    }
                    else
                    {
                        printOut.println("Passwords do not match");
                    }
                    break;
                case 3:
                    printOut.println("Enter new file number");
                    scan.nextLine();
                    int auxOfFileNumber=scan.nextInt();
                    answer=coincidenceInFileNumberInList(auxOfFileNumber);
                    if (!answer)
                    {
                        ((Receptionist)Hotel.getUserGenericList().getList().get(posInList)).setFileNumber(auxOfFileNumber);
                        receptionistToEdit.setFileNumber(auxOfFileNumber);
                    }
                    else
                    {
                        printOut.println("The file number already exists ");
                    }
                    break;
                case 4:
                    exit=true;
                    break;

            }
        }while (!exit);
    }
    private int showMenuEditRecepcionist()
    {
        printOut.println("1- Edit login name.");
        printOut.println("2- Edit password.");
        printOut.println("3- Edit fileNumber.");
        printOut.println("4- Exit.");
        return 4;
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
        String userDni;
        userDni= scan.next();
        scan.nextLine();
        try {
            if(searchPassengerInList(userDni) != null) {
                while (!exit) {
                    option = toCaptureInt(showAdminReservationMenu());
                    switch (option) {
                        case 1:
                            showListReservation(userDni);
                            break;
                        case 2:
                            printOut.println("Enter Reservation number");
                            int reservationNumber = toCaptureInt(countBookings());
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
                        findReceptionist = searchReceptionist(fileNumber);
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
        SaveInfoUsers saveinfo= new SaveInfoUsers();
        DataFile data= new DataFile();
        printOut.println("creating backup ...");
        saveinfo.addUsers(getUserGenericList().getList());
        data.writeInfo(saveinfo, "files/backUpUsers.json ");
        data.writeJsonBookings(Hotel.getReservationGenericList().getList(),"files/backUpBooking.json");
        SaveTypeRoom saveRooms = new SaveTypeRoom();
        saveRooms.addRoomsToList(Hotel.getRoomGenericList().getList());
        dataFile.writeJsonRooms(saveRooms,"files/backUpRoom.json");
        printOut.println("finalized");

    }
}
