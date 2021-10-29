package br.com.artur.offnance.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import br.com.artur.offnance.OffnanceDataBaseContainer;
import br.com.artur.offnance.domain.dto.TypeDto;
import br.com.artur.offnance.domain.dto.TypeOutputDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.io.InputStream;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
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
@DisplayName("Teste para integração do tipo")
public class TypeControllerIntegrationTest {

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
  @DisplayName("testes criar")
  class create {

    @Test
    @DisplayName("testes de criar com sucesso")
    void create_Sucess() {
      final var name = "anything";
      TypeOutputDto typeOutputDto = TypeOutputDto.builder().name(name).user(
          TypeOutputDto.UserOutputDto.builder().userName("admin").build()
      ).build();
      TypeDto typeDto = TypeDto.builder().idUser(1L).name(name).build();
      final var type = RestAssured.given().headers(headers)
          .body(typeDto)
          .when()
          .post("api/type/")
          .then()
          .statusCode(HttpStatus.OK.value())
          .extract().jsonPath()
          .getObject("", TypeOutputDto.class);
      assertThat(type).isEqualTo(typeOutputDto);
    }



    @Test
    @DisplayName("Usuario nao encontrado")
    void create_user_not_found() {
      final var typeDto = TypeDto.builder()
          .name("Anything").idUser(10L).build();
      final var type = RestAssured.given().headers(headers)
          .body(typeDto)
          .when()
          .post("api/type/")
          .then()
          .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
  }
}


