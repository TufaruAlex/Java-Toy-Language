package Model.Statements;

import Model.ADTs.Heap;
import Model.ADTs.MyIDictionary;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.ExecutionException;
import Model.Expressions.Exp;
import Model.Expressions.VarExp;
import Model.PrgState;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFile implements IStmt{
    Exp exp;

    public OpenRFile(Exp exp) {
        this.exp = exp;
    }

    public Exp getExp() {
        return exp;
    }

    public void setExp(Exp exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "OpenFile(" + exp.toString() + ")";
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws EvaluationException, ExecutionException {
        Type expType = exp.typeCheck(typeEnv);
        if (expType.equals(new StringType()))
            return typeEnv;
        else throw new ExecutionException("The type of the expression is not a string");
    }

    @Override
    public PrgState execute(PrgState state) throws EvaluationException, ExecutionException {
        MyIDictionary<String, Value> tbl = state.getSymTable();
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
        Heap<Integer, Value> heap = state.getHeap();
        if (!(exp instanceof VarExp))
            throw new ExecutionException("Expression is not a variable expression");
        if (!exp.eval(tbl, heap).getType().equals(new StringType()))
            throw new ExecutionException("The type of the expression is not a string");
        StringValue stringValue = (StringValue) exp.eval(tbl, heap);
        if (fileTable.isDefined(stringValue.getVal()))
            throw new ExecutionException("String value is already a key in the file table");
        try {
            BufferedReader file = new BufferedReader(new FileReader(stringValue.getVal()));
            fileTable.add(stringValue.getVal(), file);
        } catch (FileNotFoundException e) {
            throw new ExecutionException("Error opening the file");
        }
        return null;
    }

    @Override
    public IStmt DeepCopy() {
        return new OpenRFile(exp);
    }
}
