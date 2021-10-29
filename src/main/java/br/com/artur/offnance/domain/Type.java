package br.com.artur.offnance.domain;


import br.com.artur.offnance.domain.dto.TypeOutputDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Table(name = "tipo")
@ToString
@Setter
@Builder
@AttributeOverrides(value = {
    @AttributeOverride(name = "id", column = @Column(name = "id_tip")),
    @AttributeOverride(name = "version", column = @Column(name = "ver_tip")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "cre_at_tip")),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "upd_at_tip"))
})
@Entity
@EqualsAndHashCode(of = "id")
@Getter
public class Type extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "id_usu")
  @JsonBackReference
  private User user;

  @Column(name = "nom_tip")
  private String name;

  public TypeOutputDto toOutput() {
    return TypeOutputDto.builder()
        .name(name)
        .user(TypeOutputDto.UserOutputDto.builder().userName(user.getUsername()).build())
        .build();
  }
}
