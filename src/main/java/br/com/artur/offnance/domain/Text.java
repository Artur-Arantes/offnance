package br.com.artur.offnance.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "texto")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@AttributeOverrides(value = {
    @AttributeOverride(name = "id", column = @Column(name = "id_tex")),
    @AttributeOverride(name = "version", column = @Column(name = "ver_tex")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "cre_at_tex")),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "upd_at_tex"))
})
@ToString(callSuper = true, includeFieldNames = true)
@Generated
public class Text extends BaseEntity {

  @Column(name = "tex_tex")
  private String text;

  @ManyToMany(fetch = FetchType.LAZY)
  @ToString.Exclude
  @JoinTable(name = "tag_texto", joinColumns = @JoinColumn(name = "id_tex", referencedColumnName = "id_tex"),
      inverseJoinColumns = @JoinColumn(name = "id_tag", referencedColumnName = "id_tag"))
  private List<Tag> tags;

}