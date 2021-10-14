package br.com.artur.offnance.controller;

import br.com.artur.offnance.OffnanceDataBaseContainer;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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
@Testcontainers
public class LoginControllerIntegrationTest {

  @LocalServerPort
  private Integer port;

  @Container
  public static OffnanceDataBaseContainer container = OffnanceDataBaseContainer.getInstance();

  static {
    container.start();
  }

  @BeforeEach
  void setUp() {
    RestAssured.port = port;
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
  }

  @Nested
  @DisplayName("testes para efetuar o login")
  class LoginTest {

    @Test
    @DisplayName("teste de login com sucesso")
    void testLoginSuccess() {
      RestAssured.given().contentType(ContentType.URLENC)
          .formParam("username", "admin")
          .formParam("password", "admin")
          .when()
          .post("/api/login")
          .then()
          .statusCode(HttpStatus.OK.value());
    }
    @Test
    @DisplayName("teste de login com falha")
    void testLoginFailure() {
      RestAssured.given().contentType(ContentType.URLENC)
          .formParam("username", "admin")
          .formParam("password", "x")
          .when()
          .post("/api/login")
          .then()
          .statusCode(HttpStatus.BAD_REQUEST.value());
    }

  }

}
