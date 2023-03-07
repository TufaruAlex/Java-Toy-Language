package Model;

import Model.ADTs.*;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.ExecutionException;
import Model.Statements.IStmt;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class PrgState {
    MyIStack<IStmt> exeStack;
    MyIDictionary<String, Value> symTable;
    MyIList<Value> out;
    IStmt originalProgram;
    MyIDictionary<String, BufferedReader> fileTable;
    Heap<Integer, Value> heap;
    public Integer id;
    static Integer pastId = 0;

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symTbl, MyIList<Value> out,
                    MyIDictionary<String, BufferedReader> fileTable, IStmt prg, Heap<Integer, Value> heap) {
        this.exeStack = stk;
        this.symTable = symTbl;
        this.out = out;
        this.originalProgram = prg.DeepCopy();
        this.fileTable = fileTable;
        stk.push(prg);
        this.heap = heap;
        this.id = manageId();
    }

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symTbl, MyIList<Value> out,
                    MyIDictionary<String, BufferedReader> fileTable, Heap<Integer, Value> heap) {
        this.exeStack = stk;
        this.symTable = symTbl;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.id = manageId();
    }

    public PrgState(IStmt stmt) {
        this.exeStack = new MyStack<>();
        this.symTable = new MyDictionary<>();
        this.out = new MyList<>();
        this.fileTable = new MyDictionary<>();
        this.heap = new Heap<>();
        exeStack.push(stmt);
        this.id = manageId();
    }

    @Override
    public String toString() {
        return "Id: " + id + "\n" +
                "ExeStack:\n" + exeStack.toString() +
                "SymTable:\n" + symTable.toString() +
                "Out:\n" + out.toString() +
                "FileTable:\n" + fileTable.toString() +
                "Heap:\n" + heap.toString() + "\n";
    }

    private static synchronized Integer manageId() {
        pastId++;
        return pastId;
    }

    public Heap<Integer, Value> getHeap() {
        return heap;
    }

    public MyIDictionary<String, BufferedReader> getFileTable() {
        return fileTable;
    }

    public void setFileTable(MyIDictionary<String, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public void setExeStack(MyIStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public MyIDictionary<String, Value> getSymTable() {
        return symTable;
    }

    public void setSymTable(MyIDictionary<String, Value> symTable) {
        this.symTable = symTable;
    }

    public MyIList<Value> getOut() {
        return out;
    }

    public void setOut(MyIList<Value> out) {
        this.out = out;
    }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    public void setOriginalProgram(IStmt originalProgram) {
        this.originalProgram = originalProgram;
    }

    public Boolean isNotCompleted() {
        return !this.exeStack.isEmpty();
    }

    public PrgState oneStep() throws IOException, ExecutionException, EvaluationException {
        if (exeStack.isEmpty()) throw new ExecutionException("Execution stack is empty");
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }
}
