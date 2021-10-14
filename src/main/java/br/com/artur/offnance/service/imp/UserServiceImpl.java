package br.com.artur.offnance.service.imp;

import br.com.artur.offnance.domain.User;
import br.com.artur.offnance.domain.dto.UserDto;
import br.com.artur.offnance.repositories.UserRepository;
import br.com.artur.offnance.service.UserService;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private final UserRepository userRepository;


  @Override
  public User findByUsername(@NonNull final String user) throws UsernameNotFoundException {
    Optional<User> u = userRepository.findByUsername(user);
    return u.filter(usr -> usr.isEnabled())
        .orElseThrow(() -> new RuntimeException());
  }

  @Override
  public UserDto findByUsernameDto(@NonNull final String user) {
    Optional<User> u = userRepository.findByUsername(user);
    return u.filter(usr -> usr.isEnabled())
        .map(usr -> UserDto.builder().id(usr.getId()).user(usr.getUsername())
            .name(usr.getPerson().getName()).build())
        .orElseThrow(() -> new RuntimeException());
  }


  @Override
  public User getLoggedUsers() {
    log.info("getUsuarioLogado - Busca do Usuário no Contexto");
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof UserDetails) {
      log.info("getUsuarioLogado - Retorna detalhes do usuário");
      final String userName = ((UserDetails) principal).getUsername();
      log.info("getUsuarioLogado - Busca usuário no repositório");
      Optional<User> usuario = userRepository.findByUsername(userName);
      if (usuario.isPresent()) {
        return usuario.get();
      }
    }
    log.error("getUsuarioLogado - Usuário Não Encontrado: Erro Inesperado");
    throw new RuntimeException();

  }

}
