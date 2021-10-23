package br.com.artur.offnance.service.imp;

import br.com.artur.offnance.domain.User;
import br.com.artur.offnance.repositories.UserRepository;
import br.com.artur.offnance.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImp implements UserService {

  @Autowired
  private final UserRepository userRepository;


  @Override
  public User findByUsername(@NonNull final String login) {
    final var user = userRepository.findByUsername(login);
    return user.filter(User::isEnabled).orElseThrow();
  }

}
