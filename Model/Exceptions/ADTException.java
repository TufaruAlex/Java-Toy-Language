package Model.Exceptions;

public class ADTException extends Exception {
    String err;

    @Override
    public String toString() {
        return err;
    }

    public ADTException(String err) {
        this.err = err;
    }
}
