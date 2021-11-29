//package br.com.artur.offnance.service.imp;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//import br.com.artur.offnance.domain.Data;
//import br.com.artur.offnance.domain.Tag;
//import br.com.artur.offnance.domain.User;
//import br.com.artur.offnance.domain.dto.DataDto;
//import br.com.artur.offnance.domain.dto.DataOutPutDto;
//import br.com.artur.offnance.exceptions.TagNotFoundException;
//import br.com.artur.offnance.repositories.DataRepository;
//import br.com.artur.offnance.repositories.TagRepository;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//import javax.servlet.http.HttpServletResponse;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//@ExtendWith(MockitoExtension.class)
//public class DataServiceImplTest {
//  @Mock
//  private DataRepository dataRepository;
//
//  @Mock
//  private TagRepository tagRepository;
//
//  @InjectMocks
//  private DataServiceImp dataServiceImp;
//
//  @BeforeEach
//  void setUp() {
//
//  }
//
//  @Nested
//  @DisplayName("Testando o metodo o metodo create do data controller")
//  class Create {
//
//    @Test
//    @DisplayName("testando o sucesso do metodo")
//    void success() {
//      final var name = "anything";
//      final var status = "any status";
//      List<Long> fakeIdTags = new ArrayList<>();
//      fakeIdTags.add(1L);
//      fakeIdTags.add(2L);
//
//      User user = mock(User.class);
//      HttpServletResponse response = mock(HttpServletResponse.class);
//
//      DataDto dto = DataDto.builder().name(name).status(status).idTags()
//          .value(new BigDecimal(Math.random()))
//          .build();
//
//      Data data = Data.builder().name(dto.getName()).value(dto.getValue()).user(user)
//          .status(dto.getStatus())
//          .build();
//      data.getTags().add(tag);
//
//      DataOutPutDto outPutDto = DataOutPutDto.builder().name(name).status(status).id(1L)
//          .value(new BigDecimal(Math.random()))
//          .build();
//
//      when(tagRepository.findAllById(anyLong())).thenReturn(
//          (List<Long>) fakeIdTags.stream().mapToLong(t -> t.getId()));
//
//      assertThat(dataServiceImp.create(response, dto, user)).hasNoNullFieldsOrProperties()
//          .isEqualTo(data.toOutput());
//
//    }
//
//    @Test
//    @DisplayName("Falha ao encontra tags id")
//    void type_not_found() {
//      HttpServletResponse response = mock(HttpServletResponse.class);
//      final var name = "anything";
//      final var status = "any status";
//      User user = mock(User.class);
//      DataDto dto = DataDto.builder()
//          .name(name)
//          .status(status)
//          .value(new BigDecimal(Math.random()))
//          .build();
//      assertThatExceptionOfType(TagNotFoundException.class).isThrownBy(() ->
//          dataServiceImp.create(response, dto, user));
//
//    }
//
//    @Test
//    @DisplayName("metodo com  http servelet response nulo ")
//    void null_response() {
//      final var name = "anything";
//      final var status = "any status";
//      List<Long> fakeIdTags = new ArrayList<>();
//      User user = mock(User.class);
//      DataDto dto = DataDto.builder().name(name).status(status).idTags(fakeIdTags)
//          .value(new BigDecimal(Math.random()))
//          .build();
//      assertThatExceptionOfType(NullPointerException.class).isThrownBy(() ->
//          dataServiceImp.create(null, dto, user));
//    }
//
//  }
//}
