package br.com.artur.offnance.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of="id")
public class DataDto {


  private BigDecimal value;

  private String name;

  private String status;

  @JsonProperty("id_tags")
  private List<Long> idTags;


}
