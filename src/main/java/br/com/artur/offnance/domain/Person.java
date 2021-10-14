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

@Entity
@Table(name = "pessoa")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode(of = "id")
@AttributeOverrides(value = {
    @AttributeOverride(name = "id", column = @Column(name = "id_pes")),
    @AttributeOverride(name = "version", column = @Column(name = "ver_pes")),
    @AttributeOverride(name = "createdAT", column = @Column(name = "dat_pes")),
    @AttributeOverride(name = "updateAt", column = @Column(name = "upd_pes"))
})
public class Person extends BaseEntity {

  @Column(name="nom_pes")
  private String name;
}
