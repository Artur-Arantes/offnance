package br.com.artur.offnance.controller;

import static java.text.MessageFormat.format;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.artur.offnance.OffnanceDataBaseContainer;
import br.com.artur.offnance.domain.dto.DataDto;
import br.com.artur.offnance.domain.dto.DataOutPutDto;
import br.com.artur.offnance.domain.dto.TagDto;
import br.com.artur.offnance.domain.dto.TagOutPutDto;
import br.com.artur.offnance.domain.dto.TypeDto;
import br.com.artur.offnance.domain.dto.TypeOutputDto;
import br.com.artur.offnance.service.DataService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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

  private DataService dataService;


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
      final var typeOutputDto = createType(headers, TypeDto.builder().name("genericType").build());
      List<Long> idTags = new ArrayList<>();
      for (int i=0; i < 10; i++) {
        final var tag = createTag(headers, TagDto.builder().idPerson(1L).idType(typeOutputDto.getId())
                .name(format("Tag {0}", i))
                .percentage(BigDecimal.valueOf(FAKER.number().numberBetween(0, 100)))
                .build());
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
      assertThat(data).isEqualTo(dataOutPutDto);
    }
//    @Test
//    @DisplayName("teste de login com falha")
//    void testLoginFailure() {
//      RestAssured.given().contentType(ContentType.URLENC)
//          .formParam("username", "admin")
//          .formParam("password", "x")
//          .when()
//          .post("/api/login")
//          .then()
//          .statusCode(HttpStatus.UNAUTHORIZED.value());
//    }

  }
}
