package au.com.cashapp.onlinebank.exception;

import au.com.cashapp.onlinebank.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
        log.error(ex.getMessage());
        ErrorResponse exceptionResponse = new ErrorResponse(ex.getMessage(), ex.getLocalizedMessage(),
                request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidTransactionException.class)
    public final ResponseEntity<ErrorResponse> handleInvalidRequestExceptions(
            InvalidTransactionException ex, WebRequest request) {

        ErrorResponse exceptionResponse = new ErrorResponse(ex.getMessage(), ex.getLocalizedMessage(),
                request.getDescription(false), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidAccountException.class)
    public final ResponseEntity<ErrorResponse> handleResourceNotFoundRequestExceptions(
            InvalidAccountException ex, WebRequest request) {

        ErrorResponse exceptionResponse = new ErrorResponse(ex.getMessage(), ex.getLocalizedMessage(),
                request.getDescription(false), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }
}
