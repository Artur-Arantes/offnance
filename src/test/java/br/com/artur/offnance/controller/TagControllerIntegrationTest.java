package br.com.artur.offnance.controller;


import static java.text.MessageFormat.format;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.artur.offnance.OffnanceDataBaseContainer;
import br.com.artur.offnance.domain.TagPagedList;
import br.com.artur.offnance.domain.dto.TagDto;
import br.com.artur.offnance.domain.dto.TagOutPutDto;
import br.com.artur.offnance.domain.dto.TypeDto;
import br.com.artur.offnance.exceptions.PersonNotFoundException;
import br.com.artur.offnance.exceptions.TypeNotFoundException;
import br.com.artur.offnance.repositories.TagRepository;
import io.restassured.RestAssured;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpStatus;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers(disabledWithoutDocker = true)
@DisplayName("Teste para integração da tag")
public class TagControllerIntegrationTest extends BaseControllerIntegrationTest {


  private String token;

  private Map<String, String> headers;

  @Autowired
  private TagRepository tagRepository;


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
  @DisplayName("Testando o metodo Create")
  class Create {

    @Test
    @DisplayName("testando sucess")
    public void success() {
      final var name = "anything";
      final var typeOutputDto = createType(headers, TypeDto.builder().name("anything")
          .build());
      final var percentage = BigDecimal.valueOf(FAKER.number().numberBetween(0, 100));
      final var tagOutPutDto = TagOutPutDto.builder().name(name)
          .user(TagOutPutDto.UserOutPutDto.builder().username("admin").build())
          .person(TagOutPutDto.PersonOutPutDto.builder().name("artur").build())
          .type(TagOutPutDto.TypeOutputDto.builder().name("anything").build())
          .percentage(percentage)
          .build();
      ArrayList<String> faker = new ArrayList<>();
      final var fake = "any";
      faker.add(fake);
      final var dto = TagDto.builder().idPerson(1L).idType(typeOutputDto.getId()).name(name)
          .texts(faker)
          .percentage(percentage)
          .build();
      final var tag = RestAssured.given().headers(headers)
          .body(dto)
          .when()
          .post("api/tags/")
          .then()
          .statusCode(HttpStatus.OK.value())
          .extract().jsonPath()
          .getObject("", TagOutPutDto.class);
      tagOutPutDto.setId(tag.getId());
      assertThat(tag).isEqualTo(tagOutPutDto);
    }

    @Test
    @DisplayName("testando not found type")
    public void type_not_found() {
      final var name = "anything";
      final var exception = format(TypeNotFoundException.ERROR_MESSAGE,
          "admin", "900");
      final var dto =
          TagDto.builder().idPerson(1L).idType(900L).name(name).percentage(new BigDecimal("1"))
              .build();
      final var result = RestAssured.given().headers(headers)
          .body(dto)
          .when()
          .post("api/tags/")
          .then()
          .statusCode(HttpStatus.BAD_REQUEST.value())
          .extract().body().asString();
      assertThat(result).isNotBlank().isEqualTo(exception);
    }

    @Test
    @DisplayName("testando person not found")
    public void person_not_found() {
      final var name = "anything";
      final var exception = format(PersonNotFoundException.ERROR_MESSAGE, "5");
      TagDto dto =
          TagDto.builder().idPerson(5L).idType(1L).name(name).percentage(new BigDecimal("1"))
              .build();
      final var result = RestAssured.given().headers(headers)
          .body(dto)
          .when()
          .post("api/tags/")
          .then()
          .statusCode(HttpStatus.BAD_REQUEST.value())
          .extract().body().asString();
      assertThat(result).isNotBlank().isEqualTo(exception);
    }
  }

  @Nested
  @DisplayName("testando o metodo find All")
  @EnableSpringDataWebSupport
  class FindAll {

    @Test
    @DisplayName("testando o sucesso do metodo find all")
    public void findAll_success() {
      ArrayList<String> faker = new ArrayList<>();
      final var fake = "any";
      faker.add(fake);
      final var percentage = BigDecimal.valueOf(FAKER.number().numberBetween(0, 100));
      final var typeOutputDto = createType(headers, TypeDto.builder().name(
          FAKER.harryPotter().book()
      ).build());
      final var tagOutPutDto =
          createTag(headers, TagDto.builder().idPerson(1L).idType(typeOutputDto.getId())
              .name(FAKER.harryPotter().character())
              .percentage(BigDecimal.valueOf(FAKER.number().numberBetween(0, 100)))
              .texts(faker)
              .build());
      ;
      final var result = RestAssured.given().headers(headers)
          .queryParam("pageNumber", 0)
          .queryParam("pageSize", 1)
          .when()
          .get("api/tags/")
          .then()
          .statusCode(HttpStatus.OK.value())
          .extract().jsonPath()
          .getObject("", TagPagedList.class);

      assertThat(result).hasSize(1).element(0).isEqualTo(tagOutPutDto);
    }
  }


  @Nested
  @DisplayName(" Testando o metodo Find By Id")
  public class FindById {

    @Test
    @DisplayName("testando o sucesso do metodo")
    public void success() {
      final var name = "anything";
      ArrayList<String> faker = new ArrayList<>();
      final var fake = "any";
      faker.add(fake);
      final var typeOutputDto = createType(headers, TypeDto.builder().name(
          FAKER.harryPotter().book()
      ).build());
      final var dto =
          TagDto.builder().idPerson(1L).idType(900L).name(name).percentage(new BigDecimal("1"))
              .texts(faker)
              .build();
      final var tagOutPutDto =
          createTag(headers, TagDto.builder().idPerson(1L).idType(typeOutputDto.getId())
              .name(FAKER.harryPotter().character())
              .texts(faker)
              .percentage(BigDecimal.valueOf(FAKER.number().numberBetween(0, 100)))
              .build());
      final var result = RestAssured.given().headers(headers)
          .body(dto)
          .queryParam("id", 1)
          .when()
          .get("api/tags/find")
          .then()
          .statusCode(HttpStatus.OK.value())
          .extract().jsonPath()
          .getObject("", TagOutPutDto.class);
      assertThat(result).isEqualTo(tagOutPutDto);
    }

  }

}

