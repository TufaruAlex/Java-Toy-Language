package Model.ADTs;

import java.util.List;
import java.util.stream.Stream;

public interface MyIList<T> {
    void add(T v);
    T remove(int position);

    Stream<T> stream();

    List<T> getList();

    MyIList<String> ListToString();
}
