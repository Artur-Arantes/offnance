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
@EqualsAndHashCode
@Setter
public class TypeOutputDto {
  @JsonProperty("name")
  private String name;
  @JsonProperty("user")
  private UserOutputDto user;

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
