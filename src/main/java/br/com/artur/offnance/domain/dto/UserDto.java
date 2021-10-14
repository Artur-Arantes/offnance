package br.com.artur.offnance.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserDto {
  private Long id;

  private String name;

  private String user;

}
