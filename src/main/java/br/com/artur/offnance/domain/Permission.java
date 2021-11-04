package br.com.artur.offnance.domain;

import br.com.artur.offnance.enums.EnumUserPermission;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "permissao")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(callSuper = true, includeFieldNames = true)
@Getter
@Builder
@AttributeOverrides(value = {
    @AttributeOverride(name = "id", column = @Column(name = "id_per")),
    @AttributeOverride(name = "version", column = @Column(name = "ver_per")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "cre_at_per")),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "upd_at_per"))
})
@Generated
public class Permission extends BaseEntity implements GrantedAuthority {

  @Id
  @Column(name = "id_per")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Convert(converter = EnumUserPermission.Converter.class)
  @Column(name = "nom_per")
  @Getter(onMethod = @__(@JsonIgnore))
  EnumUserPermission permission;

  @Override
  public String getAuthority() {
    return permission.getRole();
  }
}
