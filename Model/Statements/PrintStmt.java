package Model.Statements;

import Model.ADTs.*;
import Model.Exceptions.EvaluationException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.Types.Type;
import Model.Values.Value;

public class PrintStmt implements IStmt {
    private Exp exp;

    public PrintStmt(Exp exp) {
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
        return "print(" + exp.toString() + ")";
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws EvaluationException {
        exp.typeCheck(typeEnv);
        return typeEnv;
    }

    public PrgState execute(PrgState state) throws EvaluationException {
        MyIStack<IStmt> stk = state.getExeStack();
        Heap<Integer, Value> heap = state.getHeap();
        MyIList<Value> out = state.getOut();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        out.add(exp.eval(symTbl, heap));
        return null;
    }

    @Override
    public IStmt DeepCopy() {
        return new PrintStmt(exp.DeepCopy());
    }
}
