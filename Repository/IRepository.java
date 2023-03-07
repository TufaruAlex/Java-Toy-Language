package Repository;

import Model.PrgState;

import java.io.IOException;
import java.util.List;

public interface IRepository {

    void logPrgStateExec(PrgState prg) throws IOException;

    void setProgramStates(List<PrgState> prgList);

    List<PrgState> getProgramStates();

    void addProgram(PrgState prg);
}
