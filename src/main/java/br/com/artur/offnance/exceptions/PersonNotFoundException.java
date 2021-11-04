package br.com.artur.offnance.exceptions;


import static java.text.MessageFormat.format;

import lombok.Generated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
@Getter
@Generated
public class PersonNotFoundException extends RuntimeException {
  public static final String ERROR_MESSAGE="Errro ao tentar encontrar esta Pessoa ID : {0}";
  private final Long id;

  @SneakyThrows
  @Override
  public String getMessage() {
    return format(ERROR_MESSAGE,id);
  }
}
