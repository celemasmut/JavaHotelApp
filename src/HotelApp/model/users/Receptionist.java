package HotelApp.model.users;


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
        return "『 ----------------------------------------" +
                "\n Receptionist :" +this.getLoginName() +
                "\n fileNumber= '" + fileNumber +
                "\n------------------------------------------』";
    }
}
