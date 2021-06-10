package HotelApp.datafile;

import HotelApp.model.bedrooms.*;

import java.util.ArrayList;
import java.util.List;

public class SaveTypeRoom {
    private List<SingleRoom> singleRoomList;
    private List<DoubleRoom> doubleRoomList;
    private List<FamilyRoom> familyRoomList;
    private List<KingRoom> kingRoomList;

    public SaveTypeRoom() {
        this.singleRoomList = new ArrayList<>();
        this.doubleRoomList = new ArrayList<>();
        this.familyRoomList = new ArrayList<>();
        this.kingRoomList = new ArrayList<>();
    }

    public List<SingleRoom> getSingleRoomList() {
        return singleRoomList;
    }

    public void setSingleRoomList(List<SingleRoom> singleRoomList) {
        this.singleRoomList = singleRoomList;
    }

    public List<DoubleRoom> getDoubleRoomList() {
        return doubleRoomList;
    }

    public void setDoubleRoomList(List<DoubleRoom> doubleRoomList) {
        this.doubleRoomList = doubleRoomList;
    }

    public List<FamilyRoom> getFamilyRoomList() {
        return familyRoomList;
    }

    public void setFamilyRoomList(List<FamilyRoom> familyRoomList) {
        this.familyRoomList = familyRoomList;
    }

    public List<KingRoom> getKingRoomList() {
        return kingRoomList;
    }

    public void setKingRoomList(List<KingRoom> kingRoomList) {
        this.kingRoomList = kingRoomList;
    }

    public void addRoomsToList(List<Room> roomList){
        for(Room room : roomList){
            if(room instanceof SingleRoom){
                singleRoomList.add((SingleRoom) room);
            }
            if(room instanceof DoubleRoom){
                doubleRoomList.add((DoubleRoom) room);
            }
            if(room instanceof FamilyRoom){
                familyRoomList.add((FamilyRoom) room);
            }
            if(room instanceof KingRoom){
                kingRoomList.add((KingRoom) room);
            }

        }
    }
}
