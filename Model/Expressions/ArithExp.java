package Model.Expressions;

import Model.ADTs.Heap;
import Model.ADTs.MyIDictionary;
import Model.Exceptions.EvaluationException;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

public class ArithExp implements Exp {
    private Exp e1;
    private Exp e2;
    private int op; // 1-plus, 2-minus, 3-multiplication, 4-divide

    public ArithExp(Exp e1, Exp e2, int op) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    public Exp getE1() {
        return e1;
    }

    public Exp getE2() {
        return e2;
    }

    public int getOp() {
        return op;
    }

    public void setE1(Exp e1) {
        this.e1 = e1;
    }

    public void setE2(Exp e2) {
        this.e2 = e2;
    }

    public void setOp(int op) {
        this.op = op;
    }

    private String interpretOperation() {
        if (op == 1)
            return "+";
        else if (op == 2) {
            return "-";
        } else if (op == 3) {
            return "*";
        } else {
            return "/";
        }
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
            } else throw new EvaluationException("Second operand is not an integer");
        } else throw new EvaluationException("First operand is not an integer");
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, Heap<Integer, Value> heap) throws EvaluationException {
        Value v1, v2;
        v1 = e1.eval(tbl, heap);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(tbl, heap);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                if (op == 1) return new IntValue(n1 + n2);
                if (op == 2) return new IntValue(n1 - n2);
                if (op == 3) return new IntValue(n1 * n2);
                if (op == 4) {
                    if (n2 == 0) throw new EvaluationException("Division by 0.");
                    else return new IntValue(n1 / n2);
                }
            } else throw new EvaluationException("Second operand is not a number.");
        } else throw new EvaluationException("First operand is not a number.");
        return null;
    }

    @Override
    public Exp DeepCopy() {
        return new ArithExp(e1.DeepCopy(), e2.DeepCopy(), op);
    }
}
