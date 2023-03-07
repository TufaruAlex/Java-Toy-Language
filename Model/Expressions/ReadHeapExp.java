package Model.Expressions;

import Model.ADTs.Heap;
import Model.ADTs.MyIDictionary;
import Model.Exceptions.EvaluationException;
import Model.Types.RefType;
import Model.Types.Type;
import Model.Values.RefValue;
import Model.Values.Value;

public class ReadHeapExp implements Exp{
    Exp exp;

    public ReadHeapExp(Exp exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "rH(" + exp.toString() + ")";
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws EvaluationException {
        Type typ=exp.typeCheck(typeEnv);
        if (typ instanceof RefType refType) {
            return refType.getInner();
        } else
            throw new EvaluationException("The rH argument is not a reference type");
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, Heap<Integer, Value> heap) throws EvaluationException {
        Value evaluation = exp.eval(tbl, heap);
        if (!(evaluation instanceof RefValue))
            throw new EvaluationException("Value resulted from the expression is not a reference type.");
        int address = ((RefValue) evaluation).getAddress();
        if (!heap.isDefined(address))
            throw new EvaluationException("The address is not a key in the heap table.");
        return heap.lookup(((RefValue) evaluation).getAddress());
    }

    @Override
    public Exp DeepCopy() {
        return new ReadHeapExp(exp);
    }
}
