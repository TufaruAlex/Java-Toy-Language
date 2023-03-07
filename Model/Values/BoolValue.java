package Model.Values;

import Model.Types.BoolType;
import Model.Types.Type;

public class BoolValue implements Value {
    private Boolean val;

    public BoolValue(Boolean val) {
        this.val = val;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BoolValue;
    }

    public void setVal(boolean val) {
        this.val = val;
    }

    public Boolean getVal() {
        return val;
    }

    @Override
    public String toString() {
        return val.toString();
    }

    public Type getType() {
        return new BoolType();
    }

    @Override
    public Value DeepCopy() {
        return new BoolValue(val);
    }
}
