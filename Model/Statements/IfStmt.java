package Model.Statements;

import Model.ADTs.Heap;
import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIStack;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.ExecutionException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

import java.util.Objects;

public class IfStmt implements IStmt {
    private Exp exp;
    private IStmt thenS;
    private IStmt elseS;

    public Exp getExp() {
        return exp;
    }

    public void setExp(Exp exp) {
        this.exp = exp;
    }

    public IStmt getThenS() {
        return thenS;
    }

    public void setThenS(IStmt thenS) {
        this.thenS = thenS;
    }

    public IStmt getElseS() {
        return elseS;
    }

    public void setElseS(IStmt elseS) {
        this.elseS = elseS;
    }

    public IfStmt(Exp e, IStmt t, IStmt el) {
        exp = e;
        thenS = t;
        elseS = el;
    }

    @Override
    public String toString() {
        return "(IF(" + exp.toString() + ") THEN(" + thenS.toString()
                + ")ELSE(" + elseS.toString() + "))";
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws EvaluationException, ExecutionException {
        Type typExp=exp.typeCheck(typeEnv);
        if (typExp.equals(new BoolType())) {
            thenS.typeCheck(typeEnv.copy());
            elseS.typeCheck(typeEnv.copy());
            return typeEnv;
        }
        else
            throw new ExecutionException("The condition of IF has not the type bool");
    }

    @Override
    public PrgState execute(PrgState state) throws EvaluationException, ExecutionException {
        MyIStack<IStmt> stk = state.getExeStack();
        Heap<Integer, Value> heap = state.getHeap();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        Value cond = exp.eval(symTbl, heap);
        if (!Objects.equals(cond.getType(), new BoolType())) {
            throw new ExecutionException("Conditional expression is not a boolean.");
        } else {
            if (((BoolValue) cond).getVal()) {
                stk.push(thenS);
            } else {
                stk.push(elseS);
            }
        }
        return null;
    }

    @Override
    public IStmt DeepCopy() {
        return new IfStmt(exp.DeepCopy(), thenS.DeepCopy(), elseS.DeepCopy());
    }
}
