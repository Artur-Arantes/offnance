package br.com.artur.offnance.domain;

import br.com.artur.offnance.domain.dto.TagOutPutDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TagPagedList extends PageImpl implements Serializable {
  static final long serialVersionUID = 1114715135625836949L;

  public TagPagedList(List content, Pageable pageable,
                      long total) {
    super(content, pageable, total);
  }

  public TagPagedList(List content) {
    super(content);
  }

  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  public TagPagedList(@JsonProperty("content") List<TagOutPutDto> content,
                      @JsonProperty("number") int number,
                      @JsonProperty("size") int size,
                      @JsonProperty("totalElements") Long totalElements,
                      @JsonProperty("pageable") JsonNode pageable,
                      @JsonProperty("last") boolean last,
                      @JsonProperty("totalPages") int totalPages,
                      @JsonProperty("sort") JsonNode sort,
                      @JsonProperty("first") boolean first,
                      @JsonProperty("numberOfElements") int numberOfElements) {
    super(content, PageRequest.of(number, size), totalElements);
  }

  public static TagPagedList fromPageable(Page<TagOutPutDto> pageable) {
    return new TagPagedList(pageable.getContent(), pageable.getPageable(),
        pageable.getTotalElements());
  }
}
