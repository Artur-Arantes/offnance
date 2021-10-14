package br.com.artur.offnance.service;

import br.com.artur.offnance.domain.User;
import br.com.artur.offnance.domain.dto.UserDto;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {

  User findByUsername(String user) throws UsernameNotFoundException;

  UserDto findByUsernameDto(String user);

  User getLoggedUsers();
}
