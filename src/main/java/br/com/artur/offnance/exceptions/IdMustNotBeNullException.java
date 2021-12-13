package br.com.artur.offnance.exceptions;

import static java.text.MessageFormat.format;

import lombok.Generated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
@Getter
@Generated
public class IdMustNotBeNullException extends RuntimeException {

  public static final String ERROR_MESSAGE = "Id nao pode ser nulo";

  @SneakyThrows
  @Override
  public String getMessage() {
    return format(ERROR_MESSAGE);
  }
}
