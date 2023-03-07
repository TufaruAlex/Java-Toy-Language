package Model.Exceptions;

public class ExecutionException extends Exception {
    String err;

    @Override
    public String toString() {
        return err;
    }

    public ExecutionException(String err) {
        this.err = err;
    }
}
