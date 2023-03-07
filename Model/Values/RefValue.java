package Model.Values;

import Model.Types.RefType;
import Model.Types.Type;

public class RefValue implements Value {
    int address;
    Type locationType;

    public RefValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    @Override
    public String toString() {
        return "(" + address + ", " + locationType.toString() + ")";
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof RefValue;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    public int getAddress() {
        return address;
    }

    public Type getLocationType() {
        return locationType;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public void setLocationType(Type locationType) {
        this.locationType = locationType;
    }

    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    @Override
    public Value DeepCopy() {
        return new RefValue(address, locationType);
    }
}
