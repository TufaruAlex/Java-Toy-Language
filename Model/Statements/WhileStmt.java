package Model.Statements;

import Model.ADTs.Heap;
import Model.ADTs.MyIDictionary;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.ExecutionException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

import java.io.IOException;

public class WhileStmt implements IStmt{
    Exp condition;
    IStmt statement;

    public WhileStmt(Exp condition, IStmt statement) {
        this.condition = condition;
        this.statement = statement;
    }

    @Override
    public String toString() {
        return "While(" + condition.toString() + ") " + statement.toString();
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws EvaluationException, ExecutionException {
        Type conditionType = condition.typeCheck(typeEnv);
        if (conditionType.equals(new BoolType())){
            statement.typeCheck(typeEnv.copy());
            return typeEnv;
        }
        else throw new ExecutionException("The condition of the while statement is not a bool type");
    }

    @Override
    public PrgState execute(PrgState state) throws IOException, EvaluationException, ExecutionException {
        MyIDictionary<String, Value> tbl = state.getSymTable();
        Heap<Integer, Value> heap = state.getHeap();
        Value eval = condition.eval(tbl, heap);
        if (!eval.getType().equals(new BoolType()))
            throw new ExecutionException("Condition does not return a bool value");
        if (((BoolValue) eval).getVal()){
            state.getExeStack().push(this);
            state.getExeStack().push(statement);
        }
        else state.getExeStack().pop();
        return null;
    }

    @Override
    public IStmt DeepCopy() {
        return new WhileStmt(condition, statement);
    }
}
