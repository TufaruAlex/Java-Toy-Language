package Model.Expressions;

import Model.ADTs.Heap;
import Model.ADTs.MyIDictionary;
import Model.Types.Type;
import Model.Values.Value;

public class ValueExp implements Exp {
    private Value value;

    public ValueExp(Value value) {
        this.value = value;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ValueExp && ((ValueExp) obj).getValue() == value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public Type typeCheck(MyIDictionary<String, Type> typeEnv) {
        return value.getType();
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, Heap<Integer, Value> heap) {
        return value;
    }

    @Override
    public Exp DeepCopy() {
        return new ValueExp(value.DeepCopy());
    }
}
