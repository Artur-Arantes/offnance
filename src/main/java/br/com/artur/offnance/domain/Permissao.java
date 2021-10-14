package br.com.artur.offnance.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "permissao")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Builder
public class Permissao implements GrantedAuthority {

  @Id
  @Column(name="id_per")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "nom_per")
  private String nome;

  @Override
  public String getAuthority() {
    return null;
  }
}
