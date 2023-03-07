package Model.Statements;

import Model.ADTs.MyIDictionary;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.ExecutionException;
import Model.PrgState;
import Model.Types.Type;

import java.io.IOException;

public interface IStmt {
    MyIDictionary<String, Type> typeCheck(MyIDictionary<String,Type> typeEnv) throws EvaluationException, ExecutionException;
    PrgState execute(PrgState state) throws IOException, EvaluationException, ExecutionException;

    IStmt DeepCopy();
}
