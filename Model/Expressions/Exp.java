package Model.Expressions;

import Model.ADTs.Heap;
import Model.ADTs.MyIDictionary;
import Model.Exceptions.EvaluationException;
import Model.Types.Type;
import Model.Values.Value;

public interface Exp {
    Type typeCheck(MyIDictionary<String,Type> typeEnv) throws EvaluationException;
    Value eval(MyIDictionary<String, Value> tbl, Heap<Integer, Value> heap) throws EvaluationException;
    Exp DeepCopy();
}
