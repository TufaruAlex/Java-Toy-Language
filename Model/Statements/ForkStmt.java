package Model.Statements;

import Model.ADTs.MyDictionary;
import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIStack;
import Model.ADTs.MyStack;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.ExecutionException;
import Model.PrgState;
import Model.Types.Type;
import Model.Values.Value;

import java.io.IOException;

public class ForkStmt implements IStmt {
    IStmt statement;

    public ForkStmt(IStmt statement) {
        this.statement = statement;
    }

    @Override
    public String toString() {
        return "Fork(" + statement.toString() + ")";
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws EvaluationException, ExecutionException {
        statement.typeCheck(typeEnv.copy());
        return typeEnv;
    }

    @Override
    public PrgState execute(PrgState state) throws IOException {
        MyIStack<IStmt> newExeStack = new MyStack<>();
        newExeStack.push(statement.DeepCopy());
        MyIDictionary<String, Value> symTableCopy = new MyDictionary<>();
        for (String key : state.getSymTable().getContent().keySet()){
            symTableCopy.add(key, state.getSymTable().lookup(key).DeepCopy());
        }
        return new PrgState(newExeStack, symTableCopy, state.getOut(), state.getFileTable(), state.getHeap());
    }

    @Override
    public IStmt DeepCopy() {
        return new ForkStmt(statement.DeepCopy());
    }
}
