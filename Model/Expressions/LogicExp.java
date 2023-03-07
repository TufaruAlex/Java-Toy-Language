package Model.Expressions;

import Model.ADTs.Heap;
import Model.ADTs.MyIDictionary;
import Model.Exceptions.EvaluationException;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

public class LogicExp implements Exp {
    private Exp e1;
    private Exp e2;
    private int op;

    public LogicExp(Exp e1, Exp e2, int op) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    public Exp getE1() {
        return e1;
    }

    public void setE1(Exp e1) {
        this.e1 = e1;
    }

    public Exp getE2() {
        return e2;
    }

    public void setE2(Exp e2) {
        this.e2 = e2;
    }

    public int getOp() {
        return op;
    }

    public void setOp(int op) {
        this.op = op;
    }

    private String interpretOperation() {
        if (op == 1)
            return "and";
        else return "or";
    }

    @Override
    public String toString() {
        return e1 + " " + interpretOperation() + " " + e2;
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws EvaluationException {
        Type type1, type2;
        type1 = e1.typeCheck(typeEnv);
        type2 = e2.typeCheck(typeEnv);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new IntType();
            } else throw new EvaluationException("Second operand is not a boolean");
        } else throw new EvaluationException("First operand is not a boolean");
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, Heap<Integer, Value> heap) throws EvaluationException {
        Value nr1 = e1.eval(tbl, heap);
        if (nr1.getType().equals(new BoolType())) {
            Value nr2 = e2.eval(tbl, heap);
            if (nr2.getType().equals(new BoolType())) {
                BoolValue b1 = (BoolValue) nr1;
                BoolValue b2 = (BoolValue) nr2;
                Boolean v1 = b1.getVal();
                Boolean v2 = b2.getVal();
                if (op == 1) return new BoolValue(v1 && v2);
                if (op == 2) return new BoolValue(v1 || v2);
            } else throw new EvaluationException("Operand 2 is not a boolean.");
        } else throw new EvaluationException("Operand 1 is not a boolean.");
        return null;
    }

    @Override
    public Exp DeepCopy() {
        return new LogicExp(e1.DeepCopy(), e2.DeepCopy(), op);
    }
}
