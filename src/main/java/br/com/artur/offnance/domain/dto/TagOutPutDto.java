package br.com.artur.offnance.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
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
public class TagOutPutDto {

  @JsonProperty("id")
  private Long id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("percentage")
  private BigDecimal percentage;

  @JsonProperty("user")
  private UserOutPutDto user;


  @JsonProperty("type")
  private TypeOutputDto type;

  @JsonProperty("person")
  private PersonOutPutDto person;

  @Builder
  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  @EqualsAndHashCode
  @Setter
  public static class TypeOutputDto {

    @JsonProperty("name")
    private String name;

  }

  @Builder
  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  @EqualsAndHashCode
  @Setter
  public static class PersonOutPutDto {

    @JsonProperty("name")
    private String name;
  }

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
}
