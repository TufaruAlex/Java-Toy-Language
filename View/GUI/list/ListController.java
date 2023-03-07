package View.GUI.list;

import Controller.Controller;
import Model.ADTs.MyDictionary;
import Model.ADTs.MyIDictionary;
import Model.Exceptions.EvaluationException;
import Model.Exceptions.ExecutionException;
import Model.Types.Type;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import Model.PrgState;
import Model.Statements.IStmt;
import Repository.IRepository;
import Repository.Repository;
import View.GUI.Examples;
import View.GUI.program.ProgramController;

public class ListController {
    private ProgramController programController;

    public void setProgramController(ProgramController programController) {
        this.programController = programController;
    }

    @FXML
    private ListView<IStmt> statements;

    @FXML
    private Button displayButton;

    @FXML
    public void initialize() {
        Examples interpreter = new Examples();
        statements.setItems(FXCollections.observableArrayList(interpreter.getExamples()));
        displayButton.setOnAction(actionEvent -> {
            int index = statements.getSelectionModel().getSelectedIndex();
            if (index < 0)
                return;
            PrgState state = new PrgState(interpreter.getExamples().get(index));
            IRepository repository = new Repository("log.txt");
            Controller controller = new Controller(repository);
            controller.getRepo().addProgram(state);
            try{
                MyIDictionary<String, Type> typeEnv = new MyDictionary<>();
                interpreter.getExamples().get(index).typeCheck(typeEnv);
                programController.setController(controller);
            } catch (EvaluationException | ExecutionException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.toString(), ButtonType.OK);
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.showAndWait();
            }
        });
    }
}
