package br.com.artur.offnance.exceptions;

import lombok.Generated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;


import static java.text.MessageFormat.format;

@RequiredArgsConstructor
@Getter
@Generated
public class TypeNotFoundException extends RuntimeException  {

    public static final String ERROR_MESSAGE = "Erro ao tentar encontrar o type para o usuario {0}, id do Type: {1}";
    private final String username;
    private final long id;

    @SneakyThrows
    @Override
    public String getMessage() {
        return format(ERROR_MESSAGE, username, id);
    }
}
