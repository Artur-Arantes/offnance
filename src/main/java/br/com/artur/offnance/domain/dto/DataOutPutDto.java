package br.com.artur.offnance.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Setter
public class DataOutPutDto {

  @JsonProperty("id")
  private Long id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("status")
  private String status;

  @JsonProperty("user")
  private UserOutPutDto user;

  @JsonProperty("value")
  private BigDecimal value;

  @JsonProperty("id_tags")
  private List<Long> tags;

  @Builder
  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  @EqualsAndHashCode
  @Setter
  public static class UserOutPutDto {

    @JsonProperty("username")
    private String username;
  }

  @Builder
  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  @EqualsAndHashCode
  @Setter
  public static class TagOutPutDto {

    @JsonProperty("name")
    List<String> tagsNames;
    ;
  }

}
