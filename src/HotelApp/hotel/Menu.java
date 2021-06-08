package HotelApp.hotel;

import HotelApp.datafile.DataFile;
import HotelApp.datafile.SaveInfoUsers;
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
import java.time.LocalDate;
import java.util.ArrayList;
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

        showUsers();
        showListOfRoom();
        printOut = System.out;
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
                default:
                    printOut.println("Wrong option");
                    break;
            }
        }while (op != 3);
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

            Hotel.addUser(new Passenger(userName,password,name,dni,homeTown,homeAddress));
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

    public Receptionist findReceptionist(int fileNumber){
        Receptionist find = null;
        if(Hotel.getUserGenericList().getList().size() > 0 ){
            for (User user : Hotel.getUserGenericList().getList()){
                if (user instanceof Receptionist)
                {
                    if(((Receptionist) user).getFileNumber()==(fileNumber)) {
                        int pos = findReceptionistInlist(fileNumber);
                        find =(Receptionist) getUserGenericList().getList().get(pos);
                        return find;
                    }
                }
            }
        }
        return find;
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

    public Passenger findPassanger(String dniUser){
        Passenger find = null;
        if(Hotel.getUserGenericList().getList().size() > 0 ){
            for (User user : Hotel.getUserGenericList().getList()){
                if (user instanceof Passenger)
                {
                    if (((Passenger) user).getDni().equals(dniUser)) {
                        int pos = findPassengerInlist(dniUser);
                        find =(Passenger) getUserGenericList().getList().get(pos);
                        return find;
                    }
                }
            }
        }
        return find;
    }

    private int findPassengerInlist(String dniUser){
    int pos = -1;
    ///primero hago una lista de personas
    List<Passenger> passengerList = new ArrayList<>();
        for (User user : Hotel.getUserGenericList().getList()){
            if (user instanceof Passenger) {
               passengerList.add((Passenger)user);
                }
            }

        for(int i = 0; i < passengerList.size() && pos==-1;i++){
            if(passengerList.get(i).getDni().equals(dniUser)){
                pos = i;
                }
            }
    return pos;
    }

    private boolean validatePassengerDni(String dniUser) {
        if (Hotel.getUserGenericList().getList().size() > 0) {
            for (User user : Hotel.getUserGenericList().getList()) {
                if (user instanceof Passenger)
                {
                    if (((Passenger) user).getDni().equals(dniUser)) {
                        return true;
                    }
                }
            }
        }
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
            passenger(((Passenger) userToLogin).getDni());
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
        String exist=null;
        Scanner scan = new Scanner (System.in);
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

    private void passengerMenu(){
        printOut.println("1 - To book a room");
        printOut.println("2 - See all your reservations");
        printOut.println("3 - Check a reservation");
    }

    private void showListReservation(String dniUser){
        Hotel.getPassengerReservations(dniUser).forEach(ob -> printOut.println(ob.toString()));
    }




    private void passenger(String dniUser){
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
                        showListReservation(dniUser);
                        break;
                    case 3:
                        if(Hotel.getPassengerReservations(dniUser).size() > 0) {
                            Status status = chooseStatusReservation(showStatusReservation());
                            Reservation reservationChosen = checkStatusReservation(dniUser,status);
                            if(reservationChosen != null && reservationChosen.getStatus() != Status.CANCELLED) {
                                printOut.println(reservationChosen.toString());
                                reservationChosenMenu(reservationChosen);
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
        int confirm = scan.nextInt();
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
        int room = scan.nextInt();
        scan.nextLine();
        Reservation newReserv = toReserveRoom(arrival, setDayOfExit(arrival,dayOfStay), room, chooseMealPlan(),dniUser);
        confirmReservation(newReserv, dayOfStay);
    }

    private void confirmCancellation(Reservation reservationChosen){
        if(reservationChosen.getStatus().equals(Status.CONFIRMED)) {
            printOut.println("Confirm to cancel reservation ? \n 1- yes \n 2- No");
            int i = scan.nextInt();
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
     private void reservationChosenMenu(Reservation reservationChosen){
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


    private void showRecepcionistMenu()
    {
        printOut.println("1_Check in");
        printOut.println("2_Check out");
        printOut.println("3_Show consumition of room");
        printOut.println("4_Reservation");
        printOut.println("5_Exit");
    }
    private void receptionist(){
        boolean exit=false;
        int option;
        String dniUser;
        int roomNumber;
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
                    checkOut();
                    showReservation();
                    break;
                case 3:
                    printOut.println("Enter room number:");
                    scan.nextLine();
                    roomNumber=scan.nextInt();
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
                            reservationChosenMenu(reservationChosen);
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
        int numberOfRoom;
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
            numberOfRoom =scan.nextInt();
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
            result=deleteReservationInList(reservationCheckOut);


        }
    }
    private void showConsumeOfRoom(int roomNumber)
    {
        double totalPrice=0;
        printOut.println("Product:");
        for (Room roomToConsume:Hotel.getRoomGenericList().getList())
        {
            if(roomNumber == roomToConsume.getRoomNumber() && roomToConsume.getConsumed()!=null)
            {
                for (ProductToConsume productToConsumeForRoom : roomToConsume.getConsumed())
                {
                printOut.println(productToConsumeForRoom);
                totalPrice=totalPrice+productToConsumeForRoom.getPrice();

                }
            }
        }
    }

    private void showAdminMenu() {
        printOut.println("1_Options of Register");
        printOut.println("2_Functions of Receptionist");
        printOut.println("3_Options of rooms");
        printOut.println("4_Options of Reservation");
        printOut.println("5_Delete users");
        printOut.println("6_View user lists");
        printOut.println("7_BackUp");
        printOut.println("8_Exit");
    }

    private void admin() {
        boolean exit = false;
        int option;
        while (!exit) {
            showAdminMenu();
            option = scan.nextInt();
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
                    //backUp();
                    break;
                case 7:
                    //backUp();
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
            showRegisterAllMenu();
            option = scan.nextInt();
            switch (option) {
                case 1:
                    registerRecepcionist();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    exit = true;
                    break;
            }

        }
    }
    private void showRegisterAllMenu() {
        printOut.println("1_Register receptionist ");
        printOut.println("2_Register passanger");
        printOut.println("3_Exit");
    }

    private void optionsRooms(){
        boolean exit = false;
        int option;
        while (!exit) {
            showAdminRoomMenu();
            option = scan.nextInt();
            switch (option) {
                case 1:
                    showListOfRoomXState();
                    ///mostrar lista de habitaciones
                    break;
                case 2:
                    int numberRoom;
                    printOut.println("Enter room number");
                    numberRoom = scan.nextInt();
                    changeStateRoom(numberRoom);
                    //cambiar el estado de la habitacion
                    break;
                case 3:
                    exit = true;
                    break;
            }

        }
    }

    private void showAdminRoomMenu() {
        printOut.println("1_Show room list");
        printOut.println("2_Change state of room");
        printOut.println("3_Exit");
    }

    private void changeStateRoom(int numberRoom){
        printOut.println("choose a state");
        printOut.println("1-Free");
        printOut.println("2-Reserved");
        printOut.println("3-Occupied");
        printOut.println("4-Cleaning");
        printOut.println("5-In maintenance");
        int numberState = scan.nextInt();
        switch (numberState){
            case 1:
                changeStateOfRoomXNumber(numberRoom, State.FREE);
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
        while (!exit) {
            showAdminReservationMenu();
            option = scan.nextInt();
            switch (option) {
                case 1:
                    showListReservation(dniUser);
                    ///mostrar lista de reservas de un cliente y su estado
                    break;
                case 2:
                    printOut.println("Enter Reservation number");
                    int reservationNumber = scan.nextInt();
                    changeStatusOfReserve(dniUser, reservationNumber);
                    //cambiar el estado de una reserva
                    break;
                case 3:
                    exit = true;
                    break;
            }

        }
    }

    private void showAdminReservationMenu() {
        printOut.println("1_Show reservation list");
        printOut.println("2_Change state of a reservation");
        printOut.println("3_Exit");
    }

    private void changeStatusOfReserve(String dniUser, int reservationNumber){
        printOut.println("choose a status");
        printOut.println("1-Confirmed");
        printOut.println("2-Cancelled");
        printOut.println("3-Completed");
        printOut.println("4-Active");
        int numberStatus = scan.nextInt();
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
            showAdminReservationMenu();
            option = scan.nextInt();
            switch (option) {
                case 1:
                    printOut.println("Enter passenger dni");
                    String dniUser = scan.toString();
                    Passenger find = findPassanger(dniUser);
                    if(find != null){
                        find.deleteLogic();
                    }
                    ///eliminar passenger
                    break;
                case 2:
                    printOut.println("Enter receptionist file number");
                    int fileNumber = scan.nextInt();
                    Receptionist findReceptionist = findReceptionist(fileNumber);
                    if(findReceptionist != null){
                        findReceptionist.deleteLogic();
                    }
                    else{
                        printOut.println("file number failed");
                    }

                    //eliminar Recepcionista
                    break;
                case 3:
                    showListUsersDeleted();
                    ///mostrar lista de usuarios eliminados
                    break;
                case 4:
                    exit = true;
                    break;
            }

        }
    }

    private void showListUsersDeleted(){
        for(User user: Hotel.getUserGenericList().getList()){
            if(user instanceof Passenger || user instanceof Receptionist && user.getState() == 0){//significa que esta eliminado
                printOut.println(user);
            }
        }
    }


}
