package br.com.artur.offnance.service.imp;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import br.com.artur.offnance.domain.Type;
import br.com.artur.offnance.domain.User;
import br.com.artur.offnance.domain.dto.TypeDto;
import br.com.artur.offnance.domain.dto.TypeOutputDto;
import br.com.artur.offnance.repositories.TypeRepository;
import br.com.artur.offnance.repositories.UserRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class TypeServiceImplTest {
  @Mock
  private TypeRepository typeRepository;


  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private TypeServiceImp typeServiceImp;

  @Nested
  @DisplayName("Testes para o metodo create")
  class Create {

    @BeforeEach
    void setUp() {


    }

    @Test
    @DisplayName("Testando o sucesso do metodo")
    void success() {
      User user = mock(User.class);
      String name = "anything";
      TypeDto typeDto = TypeDto.builder().name(name).build();
      final var otherThing = "otherThing";
      when(user.getUsername()).thenReturn(otherThing);
      TypeOutputDto typeOutputDto = TypeOutputDto.builder().name(name)
          .user(TypeOutputDto.UserOutputDto.builder().userName(otherThing).build())
          .build();
      when(typeRepository.save(any(Type.class))).thenAnswer(invocationOnMock -> {
        final var t = invocationOnMock.getArgument(0, Type.class);
        return t;
      });
      assertThat(typeServiceImp.create(typeDto, user)).isNotNull().isEqualTo(typeOutputDto);

    }

    @Test
    @DisplayName("null entry")
    void null_entry() {
      assertThatExceptionOfType(NullPointerException.class).isThrownBy(
          () -> typeServiceImp.create(null, null));
    }

    @Test
    @DisplayName("Se o usuario nao estiver presente")
    void dto_null() {
      User user = new User();
      String name = "anything";
      TypeDto typeDto = TypeDto.builder().name(name).build();
      assertThatExceptionOfType(RuntimeException.class).isThrownBy(
          () -> typeServiceImp.create(null, user));
    }

    @Test
    @DisplayName("Se o usuario nao estiver presente")
    void user_null() {
      User user = new User();
      String name = "anything";
      TypeDto typeDto = TypeDto.builder().name(name).build();
      assertThatExceptionOfType(RuntimeException.class).isThrownBy(
          () -> typeServiceImp.create(typeDto, null));
    }
  }

  @Nested
  @DisplayName("tesntando o metodo findAll")
  class FindAll {

    @Test
    @DisplayName("tesntando o sucesso do metodo ")
    public void success() {
      final var typePagedList = mock(Page.class);
      final var pageRequest = mock(PageRequest.class);
      final var type = mock(Type.class);
      final var pageable = mock(Pageable.class);
      List<Type> faker = Arrays.asList(type);
      when(pageable.getPageNumber()).thenReturn(1);
      when(pageable.getPageSize()).thenReturn(2);
      when(typePagedList.getContent()).thenReturn(faker);
      when(typePagedList.getPageable()).thenReturn(pageable);
      when(typeRepository.findAll(pageRequest)).thenReturn(typePagedList);
      assertThat(typeServiceImp.findAll(pageRequest)).isNotNull()
          .isInstanceOf(Page.class);

    }
  }
}
