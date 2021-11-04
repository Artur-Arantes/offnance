package br.com.artur.offnance.domain;

import br.com.artur.offnance.domain.dto.TagOutPutDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.math.BigDecimal;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;
import lombok.ToString;

@Entity
@EqualsAndHashCode(of = "id")
@Builder
@Getter
@ToString(callSuper = true, includeFieldNames = true)
@Table(name = "tags")
@AttributeOverrides(value = {
    @AttributeOverride(name = "id", column = @Column(name = "id_tag")),
    @AttributeOverride(name = "version", column = @Column(name = "ver_tag")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "cre_at_tag")),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "upd_at_tag"))
})
@Generated
public class Tag extends BaseEntity {


  @ManyToOne
  @JoinColumn(name = "id_usu")
  @JsonBackReference
  @ToString.Exclude
  private User user;

  @JoinColumn(name = "id_pes")
  @ManyToOne
  @JsonBackReference
  @ToString.Exclude
  private Person person;

  @Column(name = "nom_tag")
  private String name;

  @ManyToOne
  @JoinColumn(name = "id_tip")
  @JsonBackReference
  @ToString.Exclude
  private Type type;

  @Column(name = "per_tag")
  private BigDecimal percentage;

  public TagOutPutDto toOutPutDto() {

    final var tagOutPutDto = TagOutPutDto.builder()
        .user(TagOutPutDto.UserOutPutDto.builder().username(user.getUsername()).build())
        .person(TagOutPutDto.PersonOutPutDto.builder().name(person.getName()).build())
        .type(TagOutPutDto.TypeOutPutDto.builder().name(type.getName()).build())
        .id(getId())
        .percentage(getPercentage())
        .build();
    return tagOutPutDto;
  }
}
