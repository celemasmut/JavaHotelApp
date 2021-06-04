package HotelApp.model.bedrooms.users;


public class Receptionist extends User{
    int fileNumber;
    public Receptionist ()
    {}
    public Receptionist(String loginName, String password)
    {
        super(loginName, password);
        fileNumber = getId();
    }


    public int getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(int fileNumber) {
        this.fileNumber = fileNumber;
    }

    @Override
    public String toString() {
        return "Receptionist{" +
                "user= '" +this.getLoginName() + '\''+
                ", fileNumber= '" + fileNumber +'\''+
                '}';
    }
}
