package br.com.artur.offnance.domain;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "pessoa")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString(callSuper = true, includeFieldNames = true)
@EqualsAndHashCode(of = "id")
@AttributeOverrides(value = {
    @AttributeOverride(name = "id", column = @Column(name = "id_pes")),
    @AttributeOverride(name = "version", column = @Column(name = "ver_pes")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "cre_at_pes")),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "upd_at_pes"))
})
@Generated
public class Person extends BaseEntity {

  @Column(name = "nom_pes")
  private String name;

  @OneToOne(mappedBy = "person", fetch = FetchType.LAZY, optional = true)
  @JsonManagedReference
  @JoinColumn(name = "id_pes")
  @ToString.Exclude
  private User user;

  @OneToMany
  @JsonManagedReference
  @ToString.Exclude
  private List<Tag> tag;
}
