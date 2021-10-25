package br.com.artur.offnance.service;

import br.com.artur.offnance.domain.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {

  User findByUsername(String user) throws UsernameNotFoundException;


}
