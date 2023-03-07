package Model.Values;

import Model.Types.StringType;
import Model.Types.Type;

public class StringValue implements Value{
    private String val;

    public StringValue(String val) {
        this.val = val;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof StringValue;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return val;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public Value DeepCopy() {
        return new StringValue(val);
    }
}
