package br.com.artur.offnance.controller;

import static java.text.MessageFormat.format;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.artur.offnance.OffnanceDataBaseContainer;
import br.com.artur.offnance.domain.DataPagedList;
import br.com.artur.offnance.domain.dto.DataDto;
import br.com.artur.offnance.domain.dto.DataOutPutDto;
import br.com.artur.offnance.domain.dto.TagDto;
import br.com.artur.offnance.domain.dto.TypeDto;
import io.restassured.RestAssured;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers(disabledWithoutDocker = true)
@DisplayName("Teste para integração de Dados")
public class DataControllerIntegrationTest extends BaseControllerIntegrationTest {
  private String token;

  private Map<String, String> headers;

  @LocalServerPort
  private Integer port;

  public static final String ENV_DISABLE_TEST_CONTAIENRS = "DISABLE_TEST_CONTAIENRS";

  @Container
  public static OffnanceDataBaseContainer container = OffnanceDataBaseContainer
      .getInstance(StringUtils.isBlank(System.getenv(ENV_DISABLE_TEST_CONTAIENRS)));

  static {
    container.start();
  }


  @BeforeEach
  void setUp() {
    RestAssured.port = port;
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    token = getToken();
    headers = Map.of(
        "Authorization", "Bearer " + token,
        "Content-Type", "application/json"
    );


  }

  @Nested
  @DisplayName("testes para o metodo create")
  class CreateTest {

    @Test
    @DisplayName("Teste de sucesso")
    public void success() {
      final var id = 1L;
      List<String> faker = new ArrayList<>();
      final var fake = "any";
      final var other = "other";
      faker.add(fake);
      faker.add(other);
      final var typeOutputDto = createType(headers, TypeDto.builder().name("genericType").build());
      List<Long> idTags = new ArrayList<>();
      for (int i = 0; i < 2; i++) {
        List<String> stringList = new ArrayList<>();
        stringList.add(faker.get(i));
        final var tag =
            createTag(headers, TagDto.builder().idPerson(1L).idType(typeOutputDto.getId())
                .name(format("Tag {0}", i))
                .percentage(BigDecimal.valueOf(FAKER.number().numberBetween(0, 100)))
                .texts(stringList)
                .build());
        tag.setId(id + i);
        idTags.add(tag.getId());
      }
      final var name = "anything";
      final var value = BigDecimal.valueOf(FAKER.number().numberBetween(0, 1000));
      final var dataOutPutDto = DataOutPutDto.builder()
          .id(1L)
          .build();
      DataDto dto = DataDto.builder().name(name)
          .value(value)
          .status("any")
          .idTags(idTags)
          .name(name)
          .build();
      final var data = RestAssured.given().headers(headers)
          .body(dto)
          .when()
          .post("api/data/")
          .then()
          .statusCode(HttpStatus.OK.value())
          .extract().jsonPath()
          .getObject("", DataOutPutDto.class);
      dataOutPutDto.setId(data.getId());
      assertThat(data).isEqualTo(dataOutPutDto);
    }
  }

  @Nested
  @DisplayName("testando o metodo Find All")
  public class FindAll {

    @Test
    @DisplayName("testando o sucesso do metodo")
    public void success() {
      final var id = 1L;
      List<String> faker = new ArrayList<>();
      final var fake = "any";
      final var other = "other";
      faker.add(fake);
      faker.add(other);
      final var typeOutputDto = createType(headers, TypeDto.builder().name("genericType").build());
      List<Long> idTags = new ArrayList<>();
      for (int i = 0; i < 2; i++) {
        List<String> clone = new ArrayList<>();
        clone.add(faker.get(i));
        final var tag =
            createTag(headers, TagDto.builder().idPerson(1L).idType(typeOutputDto.getId())
                .name(format("Tag {0}", i))
                .percentage(BigDecimal.valueOf(FAKER.number().numberBetween(0, 100)))
                .texts(clone)
                .build());
        tag.setId(id + i);
        idTags.add(tag.getId());
      }
      final var name = "anything";
      final var value = BigDecimal.valueOf(FAKER.number().numberBetween(0, 1000));
      final var dataOutPutDto = DataOutPutDto.builder()
          .id(1L)
          .build();
      DataDto dto = DataDto.builder().name(name)
          .value(value)
          .status("any")
          .idTags(idTags)
          .name(name)
          .build();
      final var data = RestAssured.given().headers(headers)
          .body(dto)
          .when()
          .post("api/data/")
          .then()
          .statusCode(HttpStatus.OK.value())
          .extract().jsonPath()
          .getObject("", DataOutPutDto.class);
      final var result = RestAssured.given().headers(headers)
          .queryParam("pageNumber", 0)
          .queryParam("pageSize", 1)
          .when()
          .get("api/data/")
          .then()
          .statusCode(HttpStatus.OK.value())
          .extract().jsonPath()
          .getObject("", DataPagedList.class);

      assertThat(result).hasSize(1).element(0).isEqualTo(data);
    }
  }
}
