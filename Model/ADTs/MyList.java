package Model.ADTs;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MyList<T> implements MyIList<T> {
    List<T> list;

    public MyList() {
        this.list = new ArrayList<T>();
    }

    @Override
    public String toString() {
        StringBuilder listString = new StringBuilder();
        for (T t : list) {
            if(t != null){
                listString.append(t.toString());
                listString.append("\n");
            }
        }
        return listString.toString();
    }

    public List<T> getList() {
        return list;
    }

    @Override
    public MyIList<String> ListToString() {
        MyIList<String> result = new MyList<>();
        for (T elem : list){
            String stringElem = elem.toString();
            result.add(stringElem);
        }
        return result;
    }

    @Override
    public void add(T v) {
        list.add(v);
    }

    @Override
    public T remove(int position) {
        return list.remove(position);
    }

    @Override
    public Stream<T> stream() {
        return list.stream();
    }
}
