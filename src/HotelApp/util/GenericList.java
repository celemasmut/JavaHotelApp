package HotelApp.util;

import java.util.ArrayList;
import java.util.List;

public class GenericList<T> {
    private List <T> list=new ArrayList<>();

    public void addToList(T algo)
    {
        list.add(algo);
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

}
