package HotelApp.util;

import java.util.ArrayList;
import java.util.List;

public class GenericList<T> {
    private List <T> list;

    public GenericList()
    {
        list=new ArrayList<>();
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public boolean addToList(T algo)
    {
       return list.add(algo);
    }

    public void deleteFromList(T toDelete)
    {
        list.remove(toDelete);
    }

    public void showList(String name)
    {
        for (T aux:list)
        {
            System.out.println(aux.toString());
        }
    }

}
