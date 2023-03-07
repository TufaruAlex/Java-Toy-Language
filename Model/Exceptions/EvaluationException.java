package Model.Exceptions;

public class EvaluationException extends Exception {
    String err;

    @Override
    public String toString() {
        return err;
    }

    public EvaluationException(String err) {
        this.err = err;
    }
}
