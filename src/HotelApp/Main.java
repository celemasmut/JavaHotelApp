package HotelApp;

import HotelApp.hotel.Hotel;
import HotelApp.hotel.MealPlan;
import HotelApp.hotel.Menu;
import HotelApp.hotel.Reservation;
import HotelApp.hotel.bedrooms.*;
import HotelApp.hotel.users.Admin;
import HotelApp.hotel.users.Passenger;
import HotelApp.hotel.users.Receptionist;

public class Main {
    public static void main(String[] args) {
        Hotel hotelPrueba= new Hotel();


        //seteo hasta que tengamos los archivos las lista se debe levantar desde hotel con el archivo

        SingleRoom sroom1 = new SingleRoom("1", State.FREE,1,500);
        SingleRoom sroom2 = new SingleRoom("2", State.OCCUPIED,1,500);

        DoubleRoom droom = new DoubleRoom("3",State.IN_MAINTENANCE, 2, 750);

        FamilyRoom fmroom = new FamilyRoom("4",State.FREE,3, 850);

        KingRoom knroom = new KingRoom("5",State.FREE,1,1500);


        Hotel.addRoomToList(sroom1);
        Hotel.addRoomToList(sroom2);
        Hotel.addRoomToList(droom);
        Hotel.addRoomToList(fmroom);
        Hotel.addRoomToList(knroom);

        //seteo pasajeros para que no sea engorroso agregar siempre si o si

        Passenger pasajerito1 = new Passenger("prueba","1234hola","kiko","11111","mdp","mdp123");
        Passenger pasajerito2 = new Passenger("test2","00012","tested","123243","mdp","mdq543");

        //seteo dos recepcionistas tambien . Luego los usuarios van a ser levantados desde los archivos y esto del main se elimina.

        Receptionist receptionist1 = new Receptionist("recep1","1234");
        Receptionist receptionist2 = new Receptionist("recep2","0987");

        Admin adminPrueba=new Admin("admin","admin");


        //ahora agrego a la lista

        Hotel.addUserToList(pasajerito1);
        Hotel.addUserToList(pasajerito2);
        Hotel.addUserToList(receptionist1);
        Hotel.addUserToList(receptionist2);
        Hotel.addUserToList(adminPrueba);


        Menu menu = new Menu();
        menu.initiate();


    }

}
