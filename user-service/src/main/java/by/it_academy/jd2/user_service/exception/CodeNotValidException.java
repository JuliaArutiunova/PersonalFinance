package by.it_academy.jd2.user_service.exception;

public class CodeNotValidException extends RuntimeException {
    public CodeNotValidException(){}
    public CodeNotValidException(String message) {
        super(message);
    }

}
