package br.com.artur.offnance.controller;


import static java.text.MessageFormat.format;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.artur.offnance.OffnanceDataBaseContainer;
import br.com.artur.offnance.domain.dto.TagDto;
import br.com.artur.offnance.domain.dto.TagOutPutDto;
import br.com.artur.offnance.exceptions.TypeNotFoundException;
import br.com.artur.offnance.service.TagService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.math.BigDecimal;
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
@DisplayName("Teste para integração da tag")
public class TagControllerIntegrationTest {


  private String token;

  private Map<String, String> headers;

  private TagService tagService;


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


  private String getToken() {
    String token = RestAssured.given().contentType(ContentType.URLENC)
        .formParam("username", "admin")
        .formParam("password", "admin")
        .when()
        .post("/api/login")
        .then()
        .statusCode(HttpStatus.OK.value())
        .extract().jsonPath()
        .getObject("accessToken", String.class);
    return token;
  }

  @Nested
  @DisplayName("Testando o metodo Create")
  class Create {

    @Test
    @DisplayName("testando sucess")
    public void success() {
      final var name = "anything";
      TagOutPutDto tagOutPutDto = TagOutPutDto.builder().name(name)
          .id(1L)
          .user(TagOutPutDto.UserOutPutDto.builder().username("admin").build())
          .person(TagOutPutDto.PersonOutPutDto.builder().name("anyone").build())
          .type(TagOutPutDto.TypeOutPutDto.builder().name("anything").build())
          .build();
      TagDto dto =
          TagDto.builder().idPerson(1L).idType(1L).name(name).percentage(new BigDecimal("1"))
              .build();
      final var tag = RestAssured.given().headers(headers)
          .body(dto)
          .when()
          .post("api/tags/")
          .then()
          .statusCode(HttpStatus.OK.value())
          .extract().jsonPath()
          .getObject("", TagOutPutDto.class);
      assertThat(tag).isEqualTo(tagOutPutDto);
    }

    @Test
    @DisplayName("testando not found type")
    public void not_found_type() {
      final var name = "anything";
      final var exception = format(TypeNotFoundException.ERROR_MESSAGE, "admin", "900");
      TagDto dto =
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

  }
}
