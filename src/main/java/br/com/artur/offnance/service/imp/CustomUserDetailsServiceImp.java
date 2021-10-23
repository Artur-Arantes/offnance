package br.com.artur.offnance.service.imp;

import br.com.artur.offnance.service.UserService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImp implements UserDetailsService {

  @Autowired
  private final UserService userService;


  public UserDetails loadUserByUsername(final String user) throws UsernameNotFoundException {
    log.info("Iniciando a pesquisa: " + user);
    final var usu = userService.findByUsername(user);
    if (Objects.isNull(usu)) {
      throw new UsernameNotFoundException(String.format("Nenhum usuário foi encontrado com" +
          " estas credenciais: '%s'.", user));
    } else {
      return usu;
    }
  }


}
