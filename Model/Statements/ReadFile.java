package Model.Statements;

import Model.ADTs.Heap;
import Model.ADTs.MyIDictionary;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.ExecutionException;
import Model.Expressions.Exp;
import Model.Expressions.VarExp;
import Model.PrgState;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStmt {
    Exp exp;
    String varName;

    public ReadFile(Exp exp, String varName) {
        this.exp = exp;
        this.varName = varName;
    }

    public Exp getExp() {
        return exp;
    }

    public void setExp(Exp exp) {
        this.exp = exp;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    @Override
    public String toString() {
        return "ReadFile(" + exp.toString() + ", " + varName + ')';
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws EvaluationException, ExecutionException {
        Type varType = typeEnv.lookup(varName);
        Type expType = exp.typeCheck(typeEnv);
        if (varType.equals(new IntType()))
            if (expType.equals(new StringType()))
                return typeEnv;
            else throw new ExecutionException("The type of the expression is not a string");
        else throw new ExecutionException("Variable is not an int");
    }

    @Override
    public PrgState execute(PrgState state) throws IOException, EvaluationException, ExecutionException {
        MyIDictionary<String, Value> tbl = state.getSymTable();
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
        Heap<Integer, Value> heap = state.getHeap();
        if (!tbl.isDefined(varName))
            throw new ExecutionException("Variable is not defined");
        if (!tbl.lookup(varName).getType().equals(new IntType()))
            throw new ExecutionException("Variable is not an int");
        if (!(exp instanceof VarExp))
            throw new ExecutionException("Expression is not a value expression");
        if (!exp.eval(tbl, heap).getType().equals(new StringType()))
            throw new ExecutionException("The type of the expression is not a string");
        StringValue stringValue = (StringValue) exp.eval(tbl, heap);
        if (!fileTable.isDefined(stringValue.getVal()))
            throw new ExecutionException("File is not in the table");
        BufferedReader file = fileTable.lookup(stringValue.getVal());
        String line = file.readLine();
        int value;
        if (line.isEmpty())
            value = 0;
        else value = Integer.parseInt(line);
        tbl.update(varName, new IntValue(value));
        return null;
    }

    @Override
    public IStmt DeepCopy() {
        return new ReadFile(exp, varName);
    }
}
