package br.com.artur.offnance.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class TypeDto {

  private final String name;

  private final Long idUser;



}
