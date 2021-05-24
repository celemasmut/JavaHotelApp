package HotelApp.hotel.users;

import HotelApp.hotel.Register;

import java.util.Scanner;


public class Admin extends User implements Register {
    public Admin(String loginName, String password) {
        super(loginName, password);
    }

    @Override
    public void menu() {
        System.out.println("Estas Logeado");
    }

    @Override
    public void userRegistration() {
   /*     int answer=0;
        String inputName;
        String inputDni;
        String inputHometown;
        String inputHomeAdress;
        String inputLoginName;
        String inputPassword;



        Scanner keyboard= new Scanner(System.in);
        System.out.println("Enter 1 if you want to create a passenger or 2 if you want to create a receptionist");
        answer=keyboard.nextInt();
        if (answer==1)
        {
            System.out.println("Enter passenger name:");
            inputName=keyboard.nextLine();
            System.out.println("Enter passenger DNI:");
            inputDni=keyboard.nextLine();
            System.out.println("Enter passenger hometown:");
            inputHometown=keyboard.nextLine();
            System.out.println("Enter passenger home adress");
            inputHomeAdress=keyboard.nextLine();
            System.out.println("Enter login name for passenger:");
            inputLoginName=keyboard.nextLine();
            System.out.println("Enter password for passenger:");
            inputPassword=keyboard.nextLine();
            Passenger pasajeroToAdd= new Passenger(inputLoginName,inputPassword,inputName,inputDni,inputHometown,inputHomeAdress);
            if(existenceInTheList(pasajeroToAdd))
            {
                System.out.println("Uno de los de los datos fue mal ingresado");
            }else{
                addUserToList(pasajeroToAdd);
            }
        }else{
            System.out.println("Enter login name for passenger:");
            inputLoginName=keyboard.nextLine();
            System.out.println("Enter password for passenger:");
            inputPassword=keyboard.nextLine();
            Receptionist receptionistToAdd= new Receptionist(inputLoginName,inputPassword);
            if (existenceInTheList(receptionistToAdd))
            {
                System.out.println("Uno de los de los datos fue mal ingresado");
            }
            else{
                addUserToList(receptionistToAdd);
            }*/
    }
}

