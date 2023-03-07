package Model.ADTs;

import java.util.Stack;

public interface MyIStack <T>{
    T pop();
    void push(T v);

    T peek();

    boolean isEmpty();

    Stack<T> getStack();
}
