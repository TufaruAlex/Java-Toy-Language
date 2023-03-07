package Model.Statements;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIStack;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.ExecutionException;
import Model.PrgState;
import Model.Types.Type;

public class CompStmt implements IStmt {
    private IStmt first;
    private IStmt snd;

    public CompStmt(IStmt first, IStmt snd) {
        this.first = first;
        this.snd = snd;
    }

    public IStmt getFirst() {
        return first;
    }

    public void setFirst(IStmt first) {
        this.first = first;
    }

    public IStmt getSnd() {
        return snd;
    }

    public void setSnd(IStmt snd) {
        this.snd = snd;
    }

    @Override
    public String toString() {
        return "(" + first.toString() + ";" + snd.toString() + ")";
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws EvaluationException, ExecutionException {
        return snd.typeCheck(first.typeCheck(typeEnv));
    }

    public PrgState execute(PrgState state) {
        MyIStack<IStmt> stk = state.getExeStack();
        stk.push(snd);
        stk.push(first);
        return null;
    }

    @Override
    public IStmt DeepCopy() {
        return new CompStmt(first.DeepCopy(), snd.DeepCopy());
    }
}
