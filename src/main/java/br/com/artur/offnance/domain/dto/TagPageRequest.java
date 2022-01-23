package br.com.artur.offnance.domain.dto;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNoneEmpty;

import lombok.Generated;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Getter
@Generated
public class TagPageRequest extends PageRequest {

  private String text;

  private Long id;

  protected TagPageRequest(int page, int size, Sort sort, final String text, final Long id) {
    super(page, size, sort);

  }

  public static TagPageRequest of(final int pageNumber, final int pageSize,
                                  @NonNull final String text, @NonNull final Long id) {
    return new TagPageRequest(pageNumber, pageSize, Sort.unsorted(), text, id);
  }

  public boolean hasText() {
    return nonNull(text) && isNoneEmpty(text);
  }

  public boolean hasId() {
    return nonNull(id);
  }
}