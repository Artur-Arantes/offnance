package br.com.artur.offnance.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import com.sun.istack.NotNull;
import java.util.Collection;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Table(name = "usuario")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
@AttributeOverrides(value = {
    @AttributeOverride(name = "id", column = @Column(name = "id_usu")),
    @AttributeOverride(name = "versao", column = @Column(name = "ver_usu")),
    @AttributeOverride(name = "dataCriacao", column = @Column(name = "dat_usu"))
})
public class Usuario extends EntidadeBase implements UserDetails {

  @OneToOne(fetch = FetchType.LAZY, targetEntity = Pessoa.class)
  @JsonManagedReference
  private  Pessoa pessoa;
  @NotNull
  @NotEmpty
  @Column(name="usu_sen")
  @Size(min =3)
  private String senha;
  @NotNull
  @NotEmpty
  @Size(min=3)
  @Column(name="usu_login")
  private String login;
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name="usuario_permissao", joinColumns = @JoinColumn(name="id_usu", referencedColumnName = "id_usu"),
  inverseJoinColumns = @JoinColumn(name= "id_per", referencedColumnName = "id_per"))
  List<Permissao> permissoes;


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return this.senha;
  }

  @Override
  public String getUsername() {
    return this.login;
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return false;
  }
}