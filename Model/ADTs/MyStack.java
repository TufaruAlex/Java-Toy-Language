package Model.ADTs;

import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {
    Stack<T> stack;

    public MyStack() {
        this.stack = new Stack<T>();
    }

    @Override
    public String toString() {
        if (!stack.isEmpty()){
            int index = stack.indexOf(stack.peek());
            StringBuilder stackString = new StringBuilder();
            while (index >= 0){
                stackString.append(stack.get(index).toString());
                stackString.append("\n");
                index--;
            }
            return stackString.toString();
        }
        else return "\n";
    }

    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public T peek() {
        return stack.peek();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public Stack<T> getStack() {
        return stack;
    }
}
