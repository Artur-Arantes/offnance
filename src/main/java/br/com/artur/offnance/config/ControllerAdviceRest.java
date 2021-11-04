package br.com.artur.offnance.config;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import br.com.artur.offnance.exceptions.PersonNotFoundException;
import br.com.artur.offnance.exceptions.TagNotFoundException;
import br.com.artur.offnance.exceptions.TypeNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerAdviceRest {

  @ExceptionHandler(TypeNotFoundException.class)
  @ResponseStatus(BAD_REQUEST)
  public ResponseEntity<String> resourceConflict(TypeNotFoundException ex) {
    return new ResponseEntity<String>(ex.getMessage(), BAD_REQUEST);
  }

  @ExceptionHandler(TagNotFoundException.class)
  @ResponseStatus(BAD_REQUEST)
  public ResponseEntity<String> resourceConflict(TagNotFoundException ex) {
    return new ResponseEntity<String>(ex.getMessage(), BAD_REQUEST);
  }

  @ExceptionHandler(PersonNotFoundException.class)
  @ResponseStatus(BAD_REQUEST)
  public ResponseEntity<String> resourceConflict(PersonNotFoundException ex) {
    return new ResponseEntity<String>(ex.getMessage(), BAD_REQUEST);
  }
}
