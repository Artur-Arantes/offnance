package br.com.artur.offnance.domain;


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pessoa")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
@AttributeOverrides(value = {
    @AttributeOverride(name = "id", column = @Column(name = "id_pes")),
    @AttributeOverride(name = "versao", column = @Column(name = "ver_pes")),
    @AttributeOverride(name = "dataCriacao", column = @Column(name = "dat_pes"))
})
public class Pessoa extends EntidadeBase{

  @Column(name="nome_pes")
  private String nome;


}
