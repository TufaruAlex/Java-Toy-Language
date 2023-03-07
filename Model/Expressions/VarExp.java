package Model.Expressions;

import Model.ADTs.Heap;
import Model.ADTs.MyIDictionary;
import Model.Types.Type;
import Model.Values.Value;

public class VarExp implements Exp {
    private String id;

    public VarExp(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) {
        return typeEnv.lookup(id);
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, Heap<Integer, Value> heap) {
        return tbl.lookup(id);
    }

    @Override
    public Exp DeepCopy() {
        return new VarExp(id);
    }
}
