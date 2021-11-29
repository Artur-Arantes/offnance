package br.com.artur.offnance.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import br.com.artur.offnance.domain.User;
import br.com.artur.offnance.domain.dto.TypeDto;
import br.com.artur.offnance.domain.dto.TypeOutputDto;
import br.com.artur.offnance.service.TypeService;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TypeControllerTest {

  @Mock
  private TypeService typeService;

  @InjectMocks
  TypeController controller;

  @BeforeEach
  public void setUp() {

  }

  @Nested
  @DisplayName("testando metodo Post create")
  class create {


    @DisplayName("testando metodo create do controller tag")
    @Test
    public void create_success() {
      HttpServletResponse http = mock(HttpServletResponse.class);
      User user = mock(User.class);
      String name = "anything";
      TypeDto typeDto = TypeDto.builder().name(name).build();
      final var otherThing = "otherThing";
      TypeOutputDto typeOutputDto = TypeOutputDto.builder().name(name)
          .user(TypeOutputDto.UserOutputDto.builder().userName(otherThing).build())
          .build();
      when(typeService.create(typeDto, user)).thenReturn(typeOutputDto);
      assertThat(controller.create(http, typeDto, user)).isNotNull().isEqualTo(typeOutputDto);
    }

    @Test
    @DisplayName("testando metodo create com http servelet response nulo")
    void create_http_null() {
      User user = mock(User.class);
      String name = "anything";
      TypeDto typeDto = TypeDto.builder().name(name).build();
      assertThatExceptionOfType(NullPointerException.class).isThrownBy(
          () -> controller.create(null, typeDto, user));
    }

    @Test
    @DisplayName("testando metodo create com dto nulo")
    void create_dto_null() {
      HttpServletResponse http = mock(HttpServletResponse.class);
      User user = mock(User.class);
      assertThatExceptionOfType(NullPointerException.class).isThrownBy(
          () -> controller.create(http, null, user));

    }

    @Test
    @DisplayName("testando metodo create com user nulo")
    void create_user_null() {
      HttpServletResponse http = mock(HttpServletResponse.class);
      User user = mock(User.class);
      String name = "anything";
      TypeDto typeDto = TypeDto.builder().name(name).build();
      assertThatExceptionOfType(NullPointerException.class).isThrownBy(
          () -> controller.create(http, typeDto, null));

    }
  }
}

