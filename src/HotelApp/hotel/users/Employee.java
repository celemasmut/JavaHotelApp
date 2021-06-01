package HotelApp.hotel.users;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    List<Receptionist> receptionistList ;
    List<Admin> admins ;

    public Employee() {
        this.receptionistList = new ArrayList<>();
        this.admins = new ArrayList<>();
    }

    public List<Receptionist> getReceptionistList() {
        return receptionistList;
    }

    public void addReceptionistList(Receptionist receptionistList) {
        this.receptionistList.add(receptionistList);
    }

    public List<Admin> getAdmins() {
        return admins;
    }

    public void addAdmins(Admin admin) {
        this.admins.add(admin);
    }

    public void setReceptionistList(List<Receptionist> receptionistList) {
        this.receptionistList = receptionistList;
    }

    public void setAdmins(List<Admin> admins) {
        this.admins = admins;
    }
}
