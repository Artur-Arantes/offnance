package br.com.artur.offnance.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TagDto {

  private BigDecimal percentage;

  private String name;

  @JsonProperty("id_person")
  private Long idPerson;

  @JsonProperty("id_type")
  private Long idType;

}
