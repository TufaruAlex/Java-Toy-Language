package Model.Statements;

import Model.ADTs.Heap;
import Model.ADTs.MyIDictionary;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.ExecutionException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.Types.Type;
import Model.Values.Value;

public class AssignStmt implements IStmt {
    private String id;
    private Exp exp;

    public AssignStmt(String id, Exp exp) {
        this.id = id;
        this.exp = exp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Exp getExp() {
        return exp;
    }

    public void setExp(Exp exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return id + " = " + exp.toString();
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws EvaluationException, ExecutionException {
        Type typeVar = typeEnv.lookup(id);
        Type typExp = exp.typeCheck(typeEnv);
        if (typeVar.equals(typExp))
            return typeEnv;
        else
            throw new ExecutionException("Assignment: right hand side and left hand side have different types");
    }

    @Override
    public PrgState execute(PrgState state) throws EvaluationException, ExecutionException {
        Heap<Integer, Value> heap = state.getHeap();
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        if (symTbl.isDefined(id)) {
            Value val = exp.eval(symTbl, heap);
            Type typId = (symTbl.lookup(id)).getType();
            if ((val.getType()).equals(typId))
                symTbl.update(id, val);
            else
                throw new ExecutionException("declared type of variable " + id + " and type of the assigned expression do not match");
        } else throw new ExecutionException("the used variable " + id + " was not declared before");
        return null;
    }

    @Override
    public IStmt DeepCopy() {
        return new AssignStmt(id, exp.DeepCopy());
    }
}
