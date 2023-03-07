package View.GUI;

import Controller.Controller;
import Model.Expressions.*;
import Model.Statements.*;
import Model.Types.IntType;
import Model.Types.RefType;
import Model.Types.StringType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;

import java.util.ArrayList;
import java.util.List;

public class Examples {
    private List<IStmt> examples;
    private List<Controller> controllers;

    public Examples() {
        this.examples = new ArrayList<>();
        this.controllers = new ArrayList<>();
        this.fillExamples();
    }

    public List<IStmt> getExamples() {
        return examples;
    }

    void fillExamples() {
        examples.add(new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v")))));
        examples.add(new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp(new ValueExp(new IntValue(2)), new ArithExp(new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)), 3), 1)),
                                new CompStmt(new AssignStmt("b", new ArithExp(new VarExp("a"), new ValueExp(new IntValue(1)), 1)), new PrintStmt(new VarExp("b")))))));
        examples.add(new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(new OpenRFile(new VarExp("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VarExp("varc")), new CloseRFile(new VarExp("varf")))))))))));
        examples.add(new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                new CompStmt(new WriteHeapStmt("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp(new ReadHeapExp(new VarExp("v")), new ValueExp(new IntValue(5)), 1)))))));
        examples.add(new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new NewStmt("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a"))))))))));
        examples.add(new CompStmt(new VarDeclStmt("x", new IntType()),
                new CompStmt(new AssignStmt("x", new ValueExp(new IntValue(4))),
                        new CompStmt(new WhileStmt(new RelationalExp(new VarExp("x"), new ValueExp(new IntValue(0)), 5),
                                new CompStmt(new PrintStmt(new VarExp("x")), new AssignStmt("x", new ArithExp(new VarExp("x"), new ValueExp(new IntValue(1)), 2)))), new PrintStmt(new VarExp("x"))))));
        examples.add(new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(new NewStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(new ForkStmt(new CompStmt(new WriteHeapStmt("a", new ValueExp(new IntValue(30))),
                                                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                                new PrintStmt(new ReadHeapExp(new VarExp("a"))))))),
                                                new CompStmt(new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new ReadHeapExp(new VarExp("a"))))))))));
    }
}