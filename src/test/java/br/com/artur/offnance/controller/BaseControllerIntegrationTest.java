package br.com.artur.offnance.controller;

import br.com.artur.offnance.domain.dto.DataDto;
import br.com.artur.offnance.domain.dto.DataOutPutDto;
import br.com.artur.offnance.domain.dto.TagDto;
import br.com.artur.offnance.domain.dto.TagOutPutDto;
import br.com.artur.offnance.domain.dto.TypeDto;
import br.com.artur.offnance.domain.dto.TypeOutputDto;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.Map;
import org.springframework.http.HttpStatus;

public class BaseControllerIntegrationTest {

  public final static Faker FAKER = Faker.instance();

  public String getToken() {
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

  public TypeOutputDto createType(Map<String, String> headers, TypeDto typeDto) {
    return RestAssured.given().headers(headers)
        .body(typeDto)
        .when()
        .post("api/type/")
        .then()
        .statusCode(HttpStatus.OK.value())
        .extract().jsonPath()
        .getObject("", TypeOutputDto.class);
  }


  public TagOutPutDto createTag(Map<String, String> headers, TagDto dto) {
    return RestAssured.given().headers(headers)
        .body(dto)
        .when()
        .post("api/tags/")
        .then()
        .statusCode(HttpStatus.OK.value())
        .extract().jsonPath()
        .getObject("", TagOutPutDto.class);
  }

}
