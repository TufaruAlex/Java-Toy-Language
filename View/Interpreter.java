package View;

import Controller.Controller;
import Model.ADTs.*;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.ExecutionException;
import Model.Expressions.*;
import Model.PrgState;
import Model.Statements.*;
import Model.Types.IntType;
import Model.Types.RefType;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;
import Repository.IRepository;
import Repository.Repository;

import java.io.BufferedReader;
import java.util.HashMap;

public class Interpreter {
    public static void main(String[] args) {
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new BoolValue(true))), new PrintStmt(new VarExp("v"))));
        Controller ctr1 = createController(ex1);

        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp(new ValueExp(new IntValue(2)), new ArithExp(new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)), 3), 1)),
                                new CompStmt(new AssignStmt("b", new ArithExp(new VarExp("a"), new ValueExp(new IntValue(1)), 1)), new PrintStmt(new VarExp("b"))))));
        Controller ctr2 = createController(ex2);

        IStmt ex3 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(new OpenRFile(new VarExp("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VarExp("varc")), new CloseRFile(new VarExp("varf"))))))))));
        Controller ctr3 = createController(ex3);
        IStmt ex4 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                new CompStmt(new WriteHeapStmt("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp(new ReadHeapExp(new VarExp("v")), new ValueExp(new IntValue(5)), 1))))));
        Controller ctr4 = createController(ex4);

        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new NewStmt("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a")))))))));
        Controller ctr5 = createController(ex5);

        IStmt ex6 = new CompStmt(new VarDeclStmt("x", new IntType()),
                new CompStmt(new AssignStmt("x", new ValueExp(new IntValue(4))),
                        new CompStmt(new WhileStmt(new RelationalExp(new VarExp("x"), new ValueExp(new IntValue(0)), 5),
                                new CompStmt(new PrintStmt(new VarExp("x")), new AssignStmt("x", new ArithExp(new VarExp("x"), new ValueExp(new IntValue(1)), 2)))), new PrintStmt(new VarExp("x")))));
        Controller ctr6 = createController(ex6);

        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(new NewStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(new ForkStmt(new CompStmt(new WriteHeapStmt("a", new ValueExp(new IntValue(30))),
                                                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                                new PrintStmt(new ReadHeapExp(new VarExp("a"))))))),
                                                new CompStmt(new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new ReadHeapExp(new VarExp("a")))))))));
        Controller ctr7 = createController(ex7);

        TextMenu menu = new TextMenu(new HashMap<>());
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
        menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
        menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
        menu.addCommand(new RunExample("4", ex4.toString(), ctr4));
        menu.addCommand(new RunExample("5", ex5.toString(), ctr5));
        menu.addCommand(new RunExample("6", ex6.toString(), ctr6));
        menu.addCommand(new RunExample("7", ex7.toString(), ctr7));
        menu.show();
    }

    private static Controller createController(IStmt ex) {
        MyIDictionary<String, Type> typeEnv = new MyDictionary<>();
        try {
            ex.typeCheck(typeEnv);
        } catch (EvaluationException | ExecutionException e) {
            System.out.println(e);
        }
        MyIDictionary<String, Value> systemTable = new MyDictionary<>();
        MyIStack<IStmt> exeStack = new MyStack<>();
        MyIList<Value> out = new MyList<>();
        MyIDictionary<String, BufferedReader> fileTable = new MyDictionary<>();
        Heap<Integer, Value> heap = new Heap<>();
        PrgState prg = new PrgState(exeStack, systemTable, out, fileTable, ex, heap);
        IRepository repo = new Repository("log.txt");
        repo.addProgram(prg);
        return new Controller(repo);
    }
}
