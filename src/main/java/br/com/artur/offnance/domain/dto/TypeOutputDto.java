package br.com.artur.offnance.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class TypeOutputDto {
  @JsonProperty("name")
  private String name;
  @JsonProperty("user")
  private UserOutputDto user;

  @JsonProperty("id")
  private Long id;

  @Builder
  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  @EqualsAndHashCode
  @Setter
  public static class UserOutputDto {

    @JsonProperty("username")
    private String userName;

  }
}
