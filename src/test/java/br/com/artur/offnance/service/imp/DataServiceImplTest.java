package br.com.artur.offnance.service.imp;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import br.com.artur.offnance.domain.Data;
import br.com.artur.offnance.domain.Tag;
import br.com.artur.offnance.domain.User;
import br.com.artur.offnance.domain.dto.DataDto;
import br.com.artur.offnance.domain.dto.DataOutPutDto;
import br.com.artur.offnance.exceptions.TagNotFoundException;
import br.com.artur.offnance.repositories.DataRepository;
import br.com.artur.offnance.repositories.TagRepository;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
public class DataServiceImplTest {
  @Mock
  private DataRepository dataRepository;

  @Mock
  private TagRepository tagRepository;

  @InjectMocks
  private DataServiceImp dataServiceImp;

  @BeforeEach
  void setUp() {

  }

  @Nested
  @DisplayName("Testando o metodo o metodo create do data controller")
  class Create {

    @Test
    @DisplayName("testando o sucesso do metodo")
    void success() {
      final var dataDto = mock(DataDto.class);
      final var user = mock(User.class);
      final var tag1 = mock(Tag.class);
      final var tag2 = mock(Tag.class);
      final var name = "Data Name";
      final var dataValue = BigDecimal.TEN;
      final var dataStatus = "status";
      final var tag1Name = "tag1";
      final var tag2Name = "tag2";
      final var userName = "usuario";

      final var tagOutputDtoList = Arrays.asList(
          // tag 1
          DataOutPutDto.TagOutputDto.builder()
              .id(1L)
              .name(tag1Name)
              .build(),
          // tag 2
          DataOutPutDto.TagOutputDto.builder()
              .id(2L)
              .name(tag2Name)
              .build());
      final var userOutPutDto = DataOutPutDto.UserOutPutDto.builder()
          .username(userName)
          .build();
      final var dataOutPutDto = DataOutPutDto.builder()
          .tags(tagOutputDtoList)
          .id(null)
          .name(dataDto.getName())
          .status(dataStatus)
          .value(dataValue)
          .user(userOutPutDto)
          .build();

      when(user.getUsername()).thenReturn(userName);
      when(tag1.getId()).thenReturn(1L);
      when(tag2.getId()).thenReturn(2L);
      when(tag1.getName()).thenReturn(tag1Name);
      when(tag2.getName()).thenReturn(tag2Name);
      when(dataDto.getIdTags()).thenReturn(Arrays.asList(1L, 2L));
      when(tagRepository.findAllById(dataDto.getIdTags())).thenReturn(Arrays.asList(tag1, tag2));
      when(dataDto.getName()).thenReturn(name);
      when(dataDto.getValue()).thenReturn(dataValue);
      when(dataDto.getStatus()).thenReturn(dataStatus);
      when(dataRepository.save(any(Data.class))).thenAnswer(
          invocationOnMock -> invocationOnMock.getArgument(0));
      when(tagRepository.saveAll(any(List.class))).thenAnswer(
          invocationOnMock -> invocationOnMock.getArgument(0));
      assertThat(dataServiceImp.create(dataDto, user)).isNotNull().isEqualTo(dataOutPutDto);
    }
  }

  @Test
  @DisplayName("Falha ao encontra tags id")
  void type_not_found() {
    final var name = "anything";
    final var status = "any status";
    User user = mock(User.class);
    DataDto dto = DataDto.builder()
        .name(name)
        .status(status)
        .value(new BigDecimal(Math.random()))
        .build();
    assertThatExceptionOfType(TagNotFoundException.class).isThrownBy(() ->
        dataServiceImp.create(dto, user));

  }

  @Test
  @DisplayName("metodo com  dto  nulo ")
  void null_dto() {
    User user = mock(User.class);
    assertThatExceptionOfType(NullPointerException.class).isThrownBy(() ->
        dataServiceImp.create(null, user));
  }

  @Test
  @DisplayName("metodo com  dto  nulo ")
  void null_user() {
    final var dataDto = mock(DataDto.class);
    assertThatExceptionOfType(NullPointerException.class).isThrownBy(() ->
        dataServiceImp.create(dataDto, null));
  }

  @Nested
  @DisplayName("Testando o metodo o metodo create do data controller")
  class FindAll {
    @Test
    @DisplayName("testando o sucesso do metodo")
    void success() {
      final var dataPagedList = mock(Page.class);
      final var pageRequest = mock(PageRequest.class);
      final var data = mock(Data.class);
      final var pageable = mock(Pageable.class);
      List<Data> faker = Arrays.asList(data);
      when(pageable.getPageNumber()).thenReturn(1);
      when(pageable.getPageSize()).thenReturn(2);
      when(dataPagedList.getContent()).thenReturn(faker);
      when(dataPagedList.getPageable()).thenReturn(pageable);
      when(dataRepository.findAll(pageRequest)).thenReturn(dataPagedList);
      assertThat(dataServiceImp.findAll(pageRequest)).isNotNull()
          .isInstanceOf(Page.class);
    }
  }

  @Nested
  @DisplayName("Testando o metodo find by id")
  class FindById {

    @Test
    @DisplayName("testando o sucesso do metodo")
    void sucess() {
      final var da = mock(Data.class);
      final var outPutDto = mock(DataOutPutDto.class);
      when(dataRepository.findById(anyLong())).thenReturn(Optional.of(da));
      assertThat(dataServiceImp.findById(anyLong())).isEqualTo(da.toOutput());
    }
  }
}