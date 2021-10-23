package br.com.artur.offnance.service.imp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import br.com.artur.offnance.domain.User;
import br.com.artur.offnance.repositories.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceImpTest {
  @Mock
  UserRepository userRepository;

  @InjectMocks
  UserServiceImp userServiceImp;

  @Nested
  @DisplayName("Testando o metodo findByUsername")
  class FindByUsername {

    @Test
    @DisplayName("Testando o sucesso do metodo findByUsername")
    void findByUsername_success() {
      final var user = mock(User.class);
      when(user.isEnabled()).thenReturn(true);
      final var login = "anyone";
      when(userRepository.findByUsername(login)).thenReturn(Optional.of(user));
      assertThat(userServiceImp.findByUsername(login)).isNotNull().isEqualTo(user);
    }

    @DisplayName("Entrada nula")
    @Test
    void null_entry() {
      assertThatExceptionOfType(NullPointerException.class).isThrownBy(
          () -> userServiceImp.findByUsername(null));
    }

    @DisplayName("user not enable")
    @Test
    void user_not_enable() {
      final var user = mock(User.class);
      final var login = "anyone";
      when(userRepository.findByUsername(login)).thenReturn(Optional.of(user));
      when(user.isEnabled()).thenReturn(false);
      assertThatExceptionOfType(RuntimeException.class).isThrownBy(
          () -> userServiceImp.findByUsername(login));
    }
  }


}
