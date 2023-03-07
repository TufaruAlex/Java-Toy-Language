package View.GUI.program;

import Controller.Controller;
import Model.ADTs.Heap;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import Model.PrgState;
import Model.ADTs.MyIList;
import Model.Statements.IStmt;
import Model.Values.Value;

import java.util.*;
import java.util.stream.Collectors;

class Pair<T1, T2> {
    T1 first;
    T2 second;

    public Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }
}

public class ProgramController {
    private Controller controller;

    @FXML
    private TableView<Pair<Integer, Value>> heapTable;

    @FXML
    private TableColumn<Pair<Integer, Value>, Integer> addressColumn;

    @FXML
    private TableColumn<Pair<Integer, Value>, String> valueColumn;

    @FXML
    private ListView<String> outputList;

    @FXML
    private ListView<String> fileList;

    @FXML
    private ListView<Integer> programStateList;

    @FXML
    private ListView<String> executionStackList;

    @FXML
    private TableView<Pair<String, Value>> symbolTable;

    @FXML
    private TableColumn<Pair<String, Value>, String> symVariableColumn;

    @FXML
    private TableColumn<Pair<String, Value>, String> symValueColumn;

    @FXML
    private TextField numberOfProgramStates;

    @FXML
    private Button oneStep;

    @FXML
    public void initialize() {
        addressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().first).asObject());
        valueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().second.toString()));
        symVariableColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().first));
        symValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().second.toString()));
        oneStep.setOnAction(actionEvent -> {
            if(controller == null){
                Alert alert = new Alert(Alert.AlertType.ERROR, "The program was not selected", ButtonType.OK);
                alert.showAndWait();
                return;
            }
            boolean programStateLeft = Objects.requireNonNull(getCurrentProgramState()).getExeStack().isEmpty();
            if(programStateLeft){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Nothing left to execute", ButtonType.OK);
                alert.showAndWait();
                return;
            }
            try {
                controller.oneStepAll();
                populate();
            } catch (InterruptedException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
        });
        programStateList.setOnMouseClicked(mouseEvent -> populate());
    }

    private PrgState getCurrentProgramState(){
        if (controller.getRepo().getProgramStates().size() == 0)
            return null;
        int currentId = programStateList.getSelectionModel().getSelectedIndex();
        if (currentId == -1)
            return controller.getRepo().getProgramStates().get(0);
        return controller.getRepo().getProgramStates().get(currentId);
    }

    public void setController(Controller controller) {
        this.controller = controller;
        populate();
    }

    private void populate() {
        populateHeap();
        populateProgramStateIdentifiers();
        populateFileTable();
        populateOutput();
        populateSymbolTable();
        populateExecutionStack();
    }

    private void populateHeap() {
        Heap heap;
        if (controller.getRepo().getProgramStates().size() > 0)
            heap = controller.getRepo().getProgramStates().get(0).getHeap();
        else heap = new Heap();
        List<Pair<Integer, Value>> heapTableList = new ArrayList<>();
        heap.getContent().forEach((key, value) -> heapTableList.add(new Pair<>((Integer) key, (Value) value)));
        heapTable.setItems(FXCollections.observableList(heapTableList));
        heapTable.refresh();
    }

    private void populateProgramStateIdentifiers() {
        List<PrgState> programStates = controller.getRepo().getProgramStates();
        var idList = programStates.stream().map(ps -> ps.id).collect(Collectors.toList());
        programStateList.setItems(FXCollections.observableList(idList));
        numberOfProgramStates.setText("" + programStates.size());
    }

    private void populateFileTable() {
        ArrayList<String> files;
        if (controller.getRepo().getProgramStates().size() > 0)
            files = new ArrayList<>(controller.getRepo().getProgramStates().get(0).getFileTable().getContent().keySet());
        else files = new ArrayList<>();
        fileList.setItems(FXCollections.observableArrayList(files));
    }

    private void populateOutput() {
        MyIList<String> output;
        if (controller.getRepo().getProgramStates().size() > 0)
            output = controller.getRepo().getProgramStates().get(0).getOut().ListToString();
        else output = new Model.ADTs.MyList<>();
        outputList.setItems(FXCollections.observableList(output.getList()));
        outputList.refresh();
    }

    private void populateSymbolTable() {
        PrgState state = getCurrentProgramState();
        List<Pair<String, Value>> symbolTableList = new ArrayList<>();
        if (state != null)
            for (Map.Entry<String, Value> entry : state.getSymTable().getContent().entrySet())
                symbolTableList.add(new Pair<>(entry.getKey(), entry.getValue()));
        symbolTable.setItems(FXCollections.observableList(symbolTableList));
        symbolTable.refresh();
    }

    private void populateExecutionStack() {
        PrgState state = getCurrentProgramState();
        List<String> executionStackListAsString = new ArrayList<>();
        if (state != null)
            for(IStmt s : state.getExeStack().getStack()){
                executionStackListAsString.add(s.toString());
            }
        executionStackList.setItems(FXCollections.observableList(executionStackListAsString));
        executionStackList.refresh();
    }
}
