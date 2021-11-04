package br.com.artur.offnance.domain;

import br.com.artur.offnance.domain.dto.DataOutPutDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "dados")
@AttributeOverrides(value = {
    @AttributeOverride(name = "id", column = @Column(name = "id_dad")),
    @AttributeOverride(name = "version", column = @Column(name = "ver_dad")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "cre_at_dad")),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "upd_at_dad"))
})
@Getter
@Generated
public class Data extends BaseEntity {

  @Column(name = "nom_dad")
  private String name;

  @Column(name = "val_dad")
  private BigDecimal value;

  @Column(name = "sta_dad")
  private String status;

  @LazyCollection(LazyCollectionOption.FALSE)
  @ManyToMany
  @JoinTable(name = "tags_dados", joinColumns = @JoinColumn(name = "id_dad", referencedColumnName = "id_dad"),
      inverseJoinColumns = @JoinColumn(name = "id_tag", referencedColumnName = "id_tag"))
  List<Tag> tags;

  @ManyToOne
  @JoinColumn(name = "id_usu")
  @JsonBackReference
  private User user;

  public DataOutPutDto toOutput() {
    return DataOutPutDto.builder()
        .name(name)
        .id(id)
        .user(DataOutPutDto.UserOutPutDto.builder().username(user.getUsername()).build())
        .build();
  }
}
