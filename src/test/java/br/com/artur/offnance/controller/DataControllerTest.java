//package br.com.artur.offnance.controller;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//import br.com.artur.offnance.domain.User;
//import br.com.artur.offnance.domain.dto.TagDto;
//import br.com.artur.offnance.domain.dto.TagOutPutDto;
//import br.com.artur.offnance.service.DataService;
//import java.math.BigDecimal;
//import javax.servlet.http.HttpServletResponse;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//@ExtendWith(MockitoExtension.class)
//public class DataControllerTest {
//
//  @Mock
//  DataService dataService;
//
//  @Mock
//  DataController controller;
//
//  @BeforeEach
//  public void setUp() {
//
//  }
//
//  @Nested
//  @DisplayName("testando metodo Post create")
//  class create {
//
//
//    @DisplayName("testando metodo create do controller tag")
//    @Test
//    public void create_success() {
//      HttpServletResponse http = mock(HttpServletResponse.class);
//      final var name = "anything";
//      User user = mock(User.class);
//      TagDto tagDto = TagDto.builder().name(name)
//          .idPerson(1l)
//          .idType(1L)
//          .percentage(new BigDecimal("1"))
//          .build();
//      TagOutPutDto tagOutPutDto = TagOutPutDto.builder()
//          .name(name)
//          .type(TagOutPutDto.TypeOutputDto.builder().name("whosKnow").build())
//          .user(TagOutPutDto.UserOutPutDto.builder().username("anyone").build())
//          .person(TagOutPutDto.PersonOutPutDto.builder().name("Jim carry").build())
//          .percentage(new BigDecimal("1"))
//          .build();
//      when(tagService.create(http, user, tagDto)).thenReturn(tagOutPutDto);
//      assertThat(controller.create(http, tagDto, user)).isNotNull().isEqualTo(tagOutPutDto);
//    }
//
//    @Test
//    @DisplayName("testando metodo create com http servelet response nulo")
//    void create_http_null() {
//      final var name = "anything";
//      User user = mock(User.class);
//      TagDto tagDto = TagDto.builder().name(name)
//          .idPerson(1l)
//          .idType(1L)
//          .percentage(new BigDecimal("1"))
//          .build();
//      assertThatExceptionOfType(NullPointerException.class).isThrownBy(
//          () -> controller.create(null, tagDto, user));
//    }
//
//    @Test
//    @DisplayName("testando metodo create com dto nulo")
//    void create_dto_null() {
//      HttpServletResponse http = mock(HttpServletResponse.class);
//      User user = mock(User.class);
//      assertThatExceptionOfType(NullPointerException.class).isThrownBy(
//          () -> controller.create(http, null, user));
//
//    }
//
//    @Test
//    @DisplayName("testando metodo create com user nulo")
//    void create_user_null() {
//      HttpServletResponse http = mock(HttpServletResponse.class);
//      final var name = "anything";
//      TagDto tagDto = TagDto.builder().name(name)
//          .idPerson(1l)
//          .idType(1L)
//          .percentage(new BigDecimal("1"))
//          .build();
//      assertThatExceptionOfType(NullPointerException.class).isThrownBy(
//          () -> controller.create(http, tagDto, null));
//
//    }
//  }
//}
