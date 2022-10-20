package pl.generators.winningnumbers.infrastructure.endpoint;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import pl.generators.winningnumbers.logic.ResourceNotFoundException;

@ControllerAdvice
public class RestResponseEntityControllerAdvice {

    @ExceptionHandler({ResourceNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleNotFoundException(Exception exception, WebRequest request){

        return new ResponseEntity<>("Resource Not Found", new HttpHeaders(), HttpStatus.BAD_REQUEST);

    }
}
