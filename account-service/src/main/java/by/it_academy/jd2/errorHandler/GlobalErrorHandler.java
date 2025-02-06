package by.it_academy.jd2.errorHandler;

import by.it_academy.lib.error.ErrorResponse;
import by.it_academy.lib.error.FieldErrorDto;
import by.it_academy.lib.error.StructuredErrorResponse;
import by.it_academy.lib.exception.DataChangedException;
import by.it_academy.lib.exception.PageNotExistsException;
import by.it_academy.lib.exception.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalErrorHandler {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<StructuredErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

        List<FieldErrorDto> fieldErrorDTOS = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError ->
                        new FieldErrorDto(fieldError.getDefaultMessage(), fieldError.getField()))
                .collect(Collectors.toList());

        StructuredErrorResponse structuredErrorResponse = new StructuredErrorResponse(fieldErrorDTOS);

        return new ResponseEntity<>(structuredErrorResponse, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(value = RecordNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRecordNotFound(RecordNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(value = PageNotExistsException.class)
    public ResponseEntity<ErrorResponse> handlePageNotExist(PageNotExistsException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = {ObjectOptimisticLockingFailureException.class, DataChangedException.class})
    public ResponseEntity<ErrorResponse> handleOptimisticLock(RuntimeException e) {
        ErrorResponse errorResponse =
                new ErrorResponse("Данные были изменены. Попробуйте повторить запрос.");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse("Сервер не смог корректно обработать запрос.");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
