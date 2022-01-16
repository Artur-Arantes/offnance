package br.com.artur.offnance.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

}
