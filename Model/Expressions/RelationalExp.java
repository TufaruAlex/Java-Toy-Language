package Model.Expressions;

import Model.ADTs.Heap;
import Model.ADTs.MyIDictionary;
import Model.Exceptions.EvaluationException;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.Value;

import java.util.Objects;

public class RelationalExp implements Exp {
    Exp exp1;
    Exp exp2;
    int op; // 1:<, 2:<=, 3:==, 4:!=, 5:>, 6:>=

    public RelationalExp(Exp exp1, Exp exp2, int op) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.op = op;
    }

    public Exp getExp1() {
        return exp1;
    }

    public void setExp1(Exp exp1) {
        this.exp1 = exp1;
    }

    public Exp getExp2() {
        return exp2;
    }

    public void setExp2(Exp exp2) {
        this.exp2 = exp2;
    }

    public int getOp() {
        return op;
    }

    public void setOp(int op) {
        this.op = op;
    }

    private String interpretOperation() {
        if (op == 1)
            return "<";
        else if (op == 2)
            return "<=";
        else if (op == 3) {
            return "==";
        } else if (op == 4)
            return "!=";
        else if (op == 5)
            return ">";
        else return ">=";
    }

    @Override
    public String toString() {
        return exp1 + " " + interpretOperation() + " " + exp2;
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws EvaluationException {
        Type type1, type2;
        type1 = exp1.typeCheck(typeEnv);
        type2 = exp2.typeCheck(typeEnv);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new BoolType();
            } else throw new EvaluationException("Second operand is not an integer");
        } else throw new EvaluationException("First operand is not an integer");
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, Heap<Integer, Value> heap) throws EvaluationException {
        Value nr1 = exp1.eval(tbl, heap);
        if (nr1.getType().equals(new IntType())) {
            Value nr2 = exp2.eval(tbl, heap);
            if (nr2.getType().equals(new IntType())) {
                IntValue b1 = (IntValue) nr1;
                IntValue b2 = (IntValue) nr2;
                Integer v1 = b1.getVal();
                Integer v2 = b2.getVal();
                if (op == 1) return new BoolValue(v1 < v2);
                if (op == 2) return new BoolValue(v1 <= v2);
                if (op == 3) return new BoolValue(Objects.equals(v1, v2));
                if (op == 4) return new BoolValue(!Objects.equals(v1, v2));
                if (op == 5) return new BoolValue(v1 > v2);
                if (op == 6) return new BoolValue(v1 >= v2);
            } else throw new EvaluationException("Operand 2 is not a number.");
        } else throw new EvaluationException("Operand 1 is not a number.");
        return null;
    }

    @Override
    public Exp DeepCopy() {
        return new RelationalExp(exp1.DeepCopy(), exp2.DeepCopy(), op);
    }
}
