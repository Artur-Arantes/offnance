package br.com.artur.offnance.service.imp;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import br.com.artur.offnance.domain.Person;
import br.com.artur.offnance.domain.Tag;
import br.com.artur.offnance.domain.Type;
import br.com.artur.offnance.domain.User;
import br.com.artur.offnance.domain.dto.TagDto;
import br.com.artur.offnance.domain.dto.TagOutPutDto;
import br.com.artur.offnance.exceptions.TypeNotFoundException;
import br.com.artur.offnance.repositories.PersonRepository;
import br.com.artur.offnance.repositories.TagRepository;
import br.com.artur.offnance.repositories.TypeRepository;
import java.math.BigDecimal;
import java.util.Optional;
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
public class TagServiceImplTest {
  @InjectMocks
  TagServiceImp tagServiceImp;

  @Mock
  TagRepository tagRepository;

  @Mock
  PersonRepository personRepository;

  @Mock
  TypeRepository typeRepository;

  @BeforeEach
  void setUp() {

  }

  @Nested
  @DisplayName("Testando o metodo create")
  class create {

    @Test
    @DisplayName("Testando o sucesso")
    public void success() {
      final var name = "anything";
      Person person = mock(Person.class);
      HttpServletResponse response = mock(HttpServletResponse.class);
      User user = mock(User.class);
      Type type = mock(Type.class);
      TagDto tagDto = TagDto.builder().name(name)
          .idPerson(1l)
          .idType(1L)
          .percentage(new BigDecimal("1"))
          .build();
      TagOutPutDto tagOutPutDto = TagOutPutDto.builder()
          .name(name)
          .type(TagOutPutDto.TypeOutPutDto.builder().name("whosKnow").build())
          .user(TagOutPutDto.UserOutPutDto.builder().username("anyone").build())
          .person(TagOutPutDto.PersonOutPutDto.builder().name("Jim carry").build())
          .percentage(new BigDecimal("1"))
          .build();
      when(personRepository.findById(tagDto.getIdPerson())).thenReturn(Optional.of(person));
      when(typeRepository.findById(tagDto.getIdType())).thenReturn(Optional.of(type));
      when(tagRepository.save(any(Tag.class))).thenAnswer(invocationOnMock -> {
        Tag tag = invocationOnMock.getArgument(0, Tag.class);
        return tag;
      });
      assertThat(tagServiceImp.create(response, user, tagDto)).isNotNull().isEqualTo(tagOutPutDto);
    }


    @Test
    @DisplayName("Type não encontrado")
    public void type_not_found() {
      final var name = "anything";
      Person person = mock(Person.class);
      HttpServletResponse response = mock(HttpServletResponse.class);
      User user = mock(User.class);
      Type type = mock(Type.class);
      TagDto tagDto = TagDto.builder().name(name)
              .idPerson(1l)
              .idType(1L)
              .percentage(new BigDecimal("1"))
              .build();
      TagOutPutDto tagOutPutDto = TagOutPutDto.builder()
              .name(name)
              .type(TagOutPutDto.TypeOutPutDto.builder().name("whosKnow").build())
              .user(TagOutPutDto.UserOutPutDto.builder().username("anyone").build())
              .person(TagOutPutDto.PersonOutPutDto.builder().name("Jim carry").build())
              .percentage(new BigDecimal("1"))
              .build();
      when(personRepository.findById(tagDto.getIdPerson())).thenReturn(Optional.of(person));
      when(typeRepository.findById(tagDto.getIdType())).thenReturn(Optional.empty());
      assertThatExceptionOfType(TypeNotFoundException.class)
              .isThrownBy(() -> tagServiceImp.create(response, user, tagDto));
    }

    @Test
    @DisplayName("com todas entradas nulas")
    public void null_entry(){
      assertThatExceptionOfType(NullPointerException.class).isThrownBy(()->
          tagServiceImp.create(null,null,null));
    }

    @Test
    @DisplayName("segundo parametro nulo")
    public void sec_param_null() {
      TagDto tagDto = TagDto.builder().name("a")
          .idPerson(1l)
          .idType(1L)
          .percentage(new BigDecimal("1"))
          .build();
      HttpServletResponse response=mock(HttpServletResponse.class);
      assertThatExceptionOfType(NullPointerException.class).isThrownBy(() ->
          tagServiceImp.create(response, null, tagDto));
    }

    @Test
    @DisplayName("terceiro parametro nulo")
    public void third_param_null() {
      HttpServletResponse response=mock(HttpServletResponse.class);
      User user= mock(User.class);
      assertThatExceptionOfType(NullPointerException.class).isThrownBy(() ->
          tagServiceImp.create(response, user, null));
    }
  }
}
