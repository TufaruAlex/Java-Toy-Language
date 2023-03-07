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

public class NewStmt implements IStmt {
    String varName;
    Exp exp;

    public NewStmt(String varName, Exp exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "New(" + varName + ", " + exp.toString() + ")";
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws EvaluationException, ExecutionException {
        Type typeVar = typeEnv.lookup(varName);
        Type typExp = exp.typeCheck(typeEnv);
        if (typeVar.equals(new RefType(typExp)))
            return typeEnv;
        else
            throw new ExecutionException("NEW stmt: right hand side and left hand side have different types ");
    }

    @Override
    public PrgState execute(PrgState state) throws IOException, EvaluationException, ExecutionException {
        MyIDictionary<String, Value> tbl = state.getSymTable();
        Heap<Integer, Value> heap = state.getHeap();
        if (!tbl.isDefined(varName))
            throw new ExecutionException("Variable is not defined.");
        Value val = tbl.lookup(varName);
        if (!(val.getType() instanceof RefType))
            throw new ExecutionException("Value is not of reference type.");
        Value evaluation = exp.eval(tbl, heap);
        if (!evaluation.getType().equals(((RefValue) val).getLocationType()))
            throw new ExecutionException("The type of the value resulting from the expression and the location type are not the same.");
        heap.add(evaluation);
        tbl.update(varName, new RefValue(heap.getFreeLocation() - 1, ((RefValue) val).getLocationType()));
        return null;
    }

    @Override
    public IStmt DeepCopy() {
        return new NewStmt(varName, exp);
    }
}
