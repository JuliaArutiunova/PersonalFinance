package by.it_academy.lib.exception;

public class DataChangedException extends RuntimeException{
    public DataChangedException() {
    }

    public DataChangedException(String message) {
        super(message);
    }
}
