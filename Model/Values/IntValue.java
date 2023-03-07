package Model.Values;

import Model.Types.IntType;
import Model.Types.Type;

public class IntValue implements Value {
    private Integer val;

    public IntValue(Integer val) {
        this.val = val;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof IntValue;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public Integer getVal() {
        return val;
    }

    @Override
    public String toString() {
        return val.toString();
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public Value DeepCopy() {
        return new IntValue(val);
    }
}
