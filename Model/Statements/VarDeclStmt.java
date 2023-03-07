package Model.Statements;

import Model.ADTs.MyIDictionary;
import Model.Exceptions.ExecutionException;
import Model.PrgState;
import Model.Types.Type;
import Model.Values.Value;

public class VarDeclStmt implements IStmt{
    private String name;
    private Type type;

    public VarDeclStmt(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return type.toString() + " " + name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) {
        typeEnv.add(name,type);
        return typeEnv;
    }

    public PrgState execute(PrgState state) throws ExecutionException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        if (symTbl.isDefined(name)){
            throw new ExecutionException("Variable is already declared.");
        }
        else{
            symTbl.add(name, type.defaultValue());
        }
        return null;
    }

    @Override
    public IStmt DeepCopy() {
        return new VarDeclStmt(name, type);
    }
}
