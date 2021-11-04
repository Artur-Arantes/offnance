package br.com.artur.offnance.exceptions;

import static java.text.MessageFormat.format;

import br.com.artur.offnance.domain.dto.DataDto;
import java.util.List;
import lombok.Generated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;


@RequiredArgsConstructor
@Getter
@Generated
public class TagNotFoundException extends RuntimeException {
  private final static String ERROR_MESSAGE = "Erro ao tentar encontrar  tags com ids {0} ," +
      " para o dados de nome {1}";
  private final List<Long> id;
  private final DataDto dataDto;

  @SneakyThrows
  @Override
  public String getMessage() {
    return format(ERROR_MESSAGE, id, dataDto.getName());
  }
}
