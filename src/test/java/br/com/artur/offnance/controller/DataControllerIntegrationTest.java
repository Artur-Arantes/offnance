package br.com.artur.offnance.controller;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.artur.offnance.OffnanceDataBaseContainer;
import br.com.artur.offnance.domain.dto.DataDto;
import br.com.artur.offnance.domain.dto.DataOutPutDto;
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
public class DataControllerIntegrationTest {
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
  @DisplayName("testes para o metodo create")
  class CreateTest {

    @Test
    @DisplayName("testando sucess")
    public void success() {
      List<Long> idTag = new ArrayList<>();
      idTag.add(1l);
      final var name = "anything";
      DataOutPutDto dataOutPutDto = DataOutPutDto.builder().name(name)
          .user(DataOutPutDto.UserOutPutDto.builder().username("admin").build())
          .id(1L)
          .status("any")
          .value(new BigDecimal("1"))
          .name(name)
          .build();
      DataDto dto = DataDto.builder().name(name)
          .value(new BigDecimal("1"))
          .status("any")
          .idTags(idTag)
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
