package br.com.artur.offnance.config;

import br.com.artur.offnance.exceptions.TypeNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class ControllerAdviceRest {
    @ExceptionHandler(TypeNotFoundException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<String> resourceConflict(TypeNotFoundException ex) {
        return new ResponseEntity<String>(ex.getMessage(), BAD_REQUEST);
    }
}
