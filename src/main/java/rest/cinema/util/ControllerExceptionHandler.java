package rest.cinema.util;


import rest.cinema.exception.ErrorMessage;
import rest.cinema.exception.IncorrectPasswordException;
import rest.cinema.exception.SeatAlreadyPurchasedException;
import rest.cinema.exception.WrongTokenException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", status.value());
        body.put("timestamp", LocalDateTime.now());
        body.put("exception", ex.getClass());
        body.put("error","The number of a row or a column is out of bounds!");

        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler({WrongTokenException.class, SeatAlreadyPurchasedException.class})
    public ResponseEntity<ErrorMessage> handleSeatsException(
            RuntimeException e, WebRequest request ) {

        ErrorMessage body = generateErrorMessage(HttpStatus.BAD_REQUEST, e, request);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<ErrorMessage> handleIncorrectPassword(
            IncorrectPasswordException e, WebRequest request ) {

        ErrorMessage body = generateErrorMessage(HttpStatus.UNAUTHORIZED, e, request);

        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    private ErrorMessage generateErrorMessage(HttpStatus status, RuntimeException e, WebRequest request) {
        return new ErrorMessage(
                status.value(),
                LocalDateTime.now(),
                e.getMessage(),
                request.getDescription(false)
        );
    }
}
