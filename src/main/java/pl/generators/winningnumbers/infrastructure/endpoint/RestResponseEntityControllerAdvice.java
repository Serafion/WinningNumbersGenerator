package pl.generators.winningnumbers.infrastructure.endpoint;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import pl.generators.winningnumbers.logic.DateBeforeDateOfDrawException;
import pl.generators.winningnumbers.logic.ResourceNotFoundException;

@ControllerAdvice
public class RestResponseEntityControllerAdvice {

    @ExceptionHandler({ResourceNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleNotFoundException(ResourceNotFoundException exception, WebRequest request){

        return new ResponseEntity<>("Resource Not Found", new HttpHeaders(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler({DateBeforeDateOfDrawException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Object> handleNotFoundException(DateBeforeDateOfDrawException exception, WebRequest request){

        return new ResponseEntity<>("Date was before date of draw", new HttpHeaders(), HttpStatus.FORBIDDEN);

    }
}
