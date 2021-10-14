package br.com.artur.offnance.enums;

import static java.util.Arrays.stream;

import javax.persistence.AttributeConverter;

public abstract class AbstractEnumConverter<T extends Enum<T> & PersistableEnum<E>, E>
    implements AttributeConverter<T, E> {

  private final Class<T> clazz;

  public AbstractEnumConverter(final Class<T> clazz) {
    this.clazz = clazz;
  }

  @Override
  public E convertToDatabaseColumn(final T attribute) {
    return attribute != null ? attribute.getId() : null;
  }

  @Override
  public T convertToEntityAttribute(final E dbValue) {
    return stream(clazz.getEnumConstants())
        .filter(item -> item.getId().equals(dbValue))
        .findFirst()
        .orElseThrow();
  }
}
