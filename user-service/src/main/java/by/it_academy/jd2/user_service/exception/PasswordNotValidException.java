package by.it_academy.jd2.user_service.exception;

public class PasswordNotValidException extends RuntimeException{

    public PasswordNotValidException(){}
    public PasswordNotValidException(String message) {
        super(message);
    }
}
