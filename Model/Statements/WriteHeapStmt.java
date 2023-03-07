package Model.Statements;

import Model.ADTs.Heap;
import Model.ADTs.MyIDictionary;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.ExecutionException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.Types.RefType;
import Model.Types.Type;
import Model.Values.RefValue;
import Model.Values.Value;

import java.io.IOException;

public class WriteHeapStmt implements IStmt{
    String varName;
    Exp exp;

    public WriteHeapStmt(String varName, Exp exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "wH(" + varName + ", " + exp.toString() + ")";
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws EvaluationException, ExecutionException {
        Type varType = typeEnv.lookup(varName);
        Type expType = exp.typeCheck(typeEnv);
        if (!varType.equals(new RefType(expType)))
            throw new ExecutionException("The type of the result of the evaluation is not the same as the location type of the reference value");
        return typeEnv;
    }

    @Override
    public PrgState execute(PrgState state) throws IOException, EvaluationException, ExecutionException {
        MyIDictionary<String, Value> tbl = state.getSymTable();
        Heap<Integer, Value> heap = state.getHeap();
        if (!tbl.isDefined(varName))
            throw new ExecutionException("Variable is not defined in the symbols table.");
        Value value = tbl.lookup(varName);
        if (!(value instanceof RefValue))
            throw new ExecutionException("The value is not a reference type.");
        int address = ((RefValue) value).getAddress();
        if (!heap.isDefined(address))
            throw new ExecutionException("The address is not a key in the heap.");
        Value eval = exp.eval(tbl, heap);
        if (!eval.getType().equals(((RefValue)value).getLocationType()))
            throw new ExecutionException("The type of the result of the evaluation is not the same as the location type of the reference value");
        heap.update(address, eval);
        return null;
    }

    @Override
    public IStmt DeepCopy() {
        return new WriteHeapStmt(varName, exp);
    }
}
