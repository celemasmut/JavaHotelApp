package HotelApp.datafile;

import HotelApp.model.users.Admin;
import HotelApp.model.users.Passenger;
import HotelApp.model.users.Receptionist;

import java.util.ArrayList;
import java.util.List;

public class SaveInfoUsers {
    private List<Passenger> listPassenger;
    private List <Admin> listAdmin;
    private List <Receptionist> listRecepcionist;

    public SaveInfoUsers() {
        this.listPassenger = new ArrayList<>();
        this.listAdmin = new ArrayList<>();
        this.listRecepcionist = new ArrayList<>();
    }

    public List<Passenger> getListPassenger() {
        return listPassenger;
    }

    public void setListPassenger(List<Passenger> listPassenger) {
        this.listPassenger = listPassenger;
    }

    public List<Admin> getListAdmin() {
        return listAdmin;
    }

    public void setListAdmin(List<Admin> listAdmin) {
        this.listAdmin = listAdmin;
    }

    public List<Receptionist> getListRecepcionist() {
        return listRecepcionist;
    }

    public void setListRecepcionist(List<Receptionist> listRecepcionist) {
        this.listRecepcionist = listRecepcionist;
    }

    public void addAdmins(List<Admin> usersToAdd)
    {
        for (Admin user:usersToAdd)
        {
            listAdmin.add(user);
        }
    }
    public void addPassenger(List<Passenger> usersToAdd)
    {
        for (Passenger user:usersToAdd)
        {
            listPassenger.add(user);
        }
    }
    public void addRecepcionist(List<Receptionist> usersToAdd)
    {
        for (Receptionist user:usersToAdd)
        {
            listRecepcionist.add(user);
        }
    }
}
