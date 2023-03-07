package Repository;

import Model.PrgState;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{
    private List<PrgState> programStates;
    String logFilePath;

    public List<PrgState> getProgramStates() {
        return programStates;
    }

    @Override
    public void addProgram(PrgState prg) {
        programStates.add(prg);
    }

    public void setProgramStates(List<PrgState> programStates) {
        this.programStates = programStates;
    }

    public Repository(String logFilePath) {
        this.programStates = new ArrayList<>();
        this.logFilePath = logFilePath;
    }

    @Override
    public void logPrgStateExec(PrgState prg) throws IOException {
        File file = new File(logFilePath);
        file.createNewFile();
        PrintWriter logFile =  new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.write(prg.toString());
        logFile.close();
    }
}
