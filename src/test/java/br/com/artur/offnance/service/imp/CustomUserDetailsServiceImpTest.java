package br.com.artur.offnance.service.imp;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import br.com.artur.offnance.domain.User;
import br.com.artur.offnance.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsServiceImpTest {
  @Mock
  UserService userService;
  @InjectMocks
  CustomUserDetailsServiceImp customUserDetailsServiceImp;
  @Nested
  @DisplayName("Testando o metodo loadByUsername")
  class LoadByUserName{

    @DisplayName("testando o sucesso do metodo")
    @Test
    void success(){
      final var userService = mock(UserService.class);
      final var user = "usuario";
      customUserDetailsServiceImp= new CustomUserDetailsServiceImp(userService);
      final var usu = mock(User.class);
      when(userService.findByUsername(user)).thenReturn(usu);
      assertThat(customUserDetailsServiceImp.loadUserByUsername(user)).
          isNotNull();
    }

    @Test
    @DisplayName("Testando com um usuario que nao existe")
    void user_Not_found(){
      final var userService = mock(UserService.class);
      final var user = "usuario";
      customUserDetailsServiceImp= new CustomUserDetailsServiceImp(userService);
      final var usu = mock(User.class);
      assertThatExceptionOfType(UsernameNotFoundException.class).isThrownBy(()-> customUserDetailsServiceImp.loadUserByUsername(user));

    }
    @Test
    @DisplayName("Testando com entrada nula")
    void entry_null(){
      assertThatExceptionOfType(NullPointerException.class).isThrownBy(()-> customUserDetailsServiceImp.loadUserByUsername(null));
    }
  }
}
