package HotelApp.hotel;

import HotelApp.hotel.Hotel;
import HotelApp.hotel.Menu;
import HotelApp.datafile.DataFile;

public class Main {
    public static void main(String[] args) {
        Hotel hotelPrueba= new Hotel();


        //seteo hasta que tengamos los archivos las lista se debe levantar desde hotel con el archivo

   /*     SingleRoom sroom1 = new SingleRoom( State.FREE,1,500);
        SingleRoom sroom2 = new SingleRoom( State.OCCUPIED,1,500);

        DoubleRoom droom = new DoubleRoom(State.IN_MAINTENANCE, 2, 750);

        FamilyRoom fmroom = new FamilyRoom(State.FREE,3, 850);

        KingRoom knroom = new KingRoom(State.FREE,1,1500);


        Hotel.addRoomToList(sroom1);
        Hotel.addRoomToList(sroom2);
        Hotel.addRoomToList(droom);
        Hotel.addRoomToList(fmroom);
        Hotel.addRoomToList(knroom);
        DataFile.writeJsonRooms(Hotel.getRoomsList(),"room.json");*/

        //seteo pasajeros para que no sea engorroso agregar siempre si o si

     /*   Passenger pasajerito1 = new Passenger("prueba","1234hola","kiko","11111","mdp","mdp123");
        Passenger pasajerito2 = new Passenger("test2","00012","tested","123243","mdp","mdq543");

        //seteo dos recepcionistas tambien . Luego los usuarios van a ser levantados desde los archivos y esto del main se elimina.

        Receptionist receptionist1 = new Receptionist("recep1","1234");
        Receptionist receptionist2 = new Receptionist("recep2","0987");

        Admin adminPrueba=new Admin("admin","admin");


        //ahora agrego a la lista

        Hotel.addPassenger(pasajerito1);
        Hotel.addPassenger(pasajerito2);
        Hotel.addRecepcionists(receptionist1);
        Hotel.addRecepcionists(receptionist2);
        Hotel.addAdmin(adminPrueba);*/

        hotelPrueba.setRoomsList(DataFile.readRoomJson("files/room.json"));

        Menu menu = new Menu();
        menu.initiate();
        DataFile.writeJsonBookings(Hotel.getReservationsList(),"booking.json");


    }

}
