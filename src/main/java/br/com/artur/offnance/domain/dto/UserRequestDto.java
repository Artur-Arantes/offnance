package br.com.artur.offnance.domain.dto;

import lombok.Builder;

@Builder
public class UserRequestDto {
  private Long id;

  private String login;

  private String password;

  private String name;

}
