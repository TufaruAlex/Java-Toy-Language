package Model.Statements;

import Model.ADTs.MyIDictionary;
import Model.PrgState;
import Model.Types.Type;

public class NopStmt implements IStmt{

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) {
        return typeEnv;
    }

    public PrgState execute(PrgState state) {
        return null;
    }

    @Override
    public IStmt DeepCopy() {
        return new NopStmt();
    }
}
