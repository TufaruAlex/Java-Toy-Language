package Model.Types;

import Model.Values.BoolValue;
import Model.Values.Value;

public class BoolType implements Type{
    public boolean equals(Object another){
        return another instanceof BoolType;
    }

    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public Value defaultValue() {
        return new BoolValue(false);
    }
}
