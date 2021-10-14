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
import lombok.ToString;
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
    @AttributeOverride(name = "version", column = @Column(name = "ver_usu")),
    @AttributeOverride(name = "createdAt", column = @Column(name = "cre_at_usu")),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "upd_at_usu"))
})
@ToString
public class User extends BaseEntity implements UserDetails {

  @OneToOne(fetch = FetchType.EAGER, targetEntity = Person.class)
  @JsonManagedReference
  @JoinColumn(name="id_pes")
  private Person person;

  @NotNull
  @NotEmpty
  @Column(name="sen_usu")
  @Size(min =3)
  @Getter(onMethod = @__(@Override))
  @Setter
  private String password;

  @NotNull
  @NotEmpty
  @Size(min=3)
  @Column(name="log_usu")
  @Getter(onMethod = @__(@Override))
  private String username;
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name="usuario_permissao", joinColumns = @JoinColumn(name="id_usu", referencedColumnName = "id_usu"),
  inverseJoinColumns = @JoinColumn(name= "id_per", referencedColumnName = "id_per"))
  List<Permission> permissions;

  @Column(name="acc_non_exp_usu")
  @Getter(onMethod = @__(@Override))
  private boolean accountNonExpired;

  @Column(name = "cre_non_exp_usu")
  @Getter(onMethod = @__(@Override))
  private boolean credentialsNonExpired;

  @Column(name = "ena_usu")
  @Getter(onMethod = @__(@Override))
  private boolean enabled;

  @Column(name = "acc_non_loc_usu")
  @Getter(onMethod = @__(@Override))
  private boolean accountNonLocked;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.permissions;
  }



}