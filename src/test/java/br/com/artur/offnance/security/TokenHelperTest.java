package br.com.artur.offnance.security;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import br.com.artur.offnance.config.ConfigProperties;
import br.com.artur.offnance.domain.User;
import io.jsonwebtoken.security.SignatureException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TokenHelperTest {
  @InjectMocks
  TokenHelper tokenHelper;

  @Mock
  ConfigProperties configProperties;

  private String username;
  private String token;
  private String invalidToken;

  @Nested
  @DisplayName("Testes para o metodo getUsernameFromToken")
  class GetUsernameFromToken {


    @BeforeEach
    void setUp() {
      when(configProperties.getAppAuthroization()).thenReturn("Anything");
      when(configProperties.getExpiresIn()).thenReturn(60L);
      username = "usuario";
      token = tokenHelper.create(username);
    }

    @Test
    @DisplayName("Testando o sucesso do metodo")
    void validToken_sucess() {
      assertThat(tokenHelper.getUsernameFromToken(token))
          .isNotEmpty().isEqualTo(username);
    }

    @Test
    @DisplayName("testando se o metodo ira buscar o objeto correto")
    void validToken_fail() {
      final var otherToken = tokenHelper.create("otherUser");
      assertThat(tokenHelper.getUsernameFromToken(otherToken)).usingDefaultComparator()
          .isNotEqualTo(username);
    }

    @Test
    @DisplayName("")
    void invalidToken_fail() {
      final var invalidToken = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJBbnl0aGluZyIsInN1" +
          "YiI6InVzdWFyaW8iLCJpYXQiOjE2MzUwMDk1NzcsImV4cCI6MTYzNTAwOTYzN30." +
          "J59NdH7ZIir-3qE9qt29HXXqFryWaY0cfyRCoiJU-A8";
      assertThatExceptionOfType(SignatureException.class).isThrownBy(() -> tokenHelper
          .getUsernameFromToken(invalidToken));
    }
  }

  @Nested
  @DisplayName("Testes para o metodo create")
  class Create {
    private String otherUsername;

    @BeforeEach
    void setUp() {

      otherUsername = "otherUser";
      username = "user";
      invalidToken = "eyJhbGciOiJIUzI1NiJ9." +
          "eyJpc3MiOiJBbnl0aGluZyIsInN1YiI6InVzdWFyaW8iLCJpYXQiOjE2MzUwMDk1NzcsImV4cCI6MTYzNTAwOTYzN30." +
          "J59NdH7ZIir-3qE9qt29HXXqFryWaY0cfyRCoiJU-A8";

    }

    @Test
    @DisplayName("Testando se o metodo ira criar corretamente")
    void create_success() {
      assertThat(tokenHelper.create(username))
          .isNotNull()
          .isNotEmpty()
          .isNotSameAs(tokenHelper.create(otherUsername));

    }

    @Test
    @DisplayName("Testando as falhas do metodo create")
    void create_fail() {
    assertThatExceptionOfType(NullPointerException.class).isThrownBy(()->tokenHelper.create(null));
    }
  }

  @Nested
  @DisplayName("Testando o metodo getToken")
  class GetToken {

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("testando getToken sucess")
    void getToken_sucess() {
      final var request = mock(HttpServletRequest.class);
      final var authCookie = mock(Cookie.class);
      final var cookieName = "cookie";
      when(configProperties.getCookie()).thenReturn(cookieName);
      when(authCookie.getName()).thenReturn(cookieName);
      when(request.getCookies()).thenReturn(new Cookie[] {authCookie});
      when(authCookie.getValue()).thenReturn("value");
      assertThat(tokenHelper.getToken(request))
          .isNotNull()
          .isNotEmpty()
          .isEqualTo(authCookie.getValue());
    }

    @Test
    @DisplayName("testando getToken com HtppServelet null")
    void getToken_null() {
      final var request = mock(HttpServletRequest.class);
      assertThat(tokenHelper.getToken(request)).isNullOrEmpty();
    }

    @Test
    @DisplayName("testando entrada nula")
    void null_entry() {
      assertThatExceptionOfType(NullPointerException.class).isThrownBy(
          () -> tokenHelper.getToken(null));
    }
    @Test
    @DisplayName("testando getToken sucess")
    void auth_success() {
      final var request = mock(HttpServletRequest.class);
      final var cookieName = "cookie";
      when(configProperties.getCookie()).thenReturn(cookieName);
      when(request.getCookies()).thenReturn(null);
      when(configProperties.getHeader()).thenReturn("Bearer ");
      final var text = "any text";
      when(request.getHeader(configProperties.getHeader())).thenReturn("Bearer "+ text);
      assertThat(tokenHelper.getToken(request))
          .isNotEmpty()
          .isEqualTo(text);
    }
    @Test
    @DisplayName("testando getToken sucess")
    void fail_auth() {
      final var request = mock(HttpServletRequest.class);
      final var cookieName = "cookie";
      when(configProperties.getCookie()).thenReturn(cookieName);
      when(request.getCookies()).thenReturn(null);
      when(configProperties.getHeader()).thenReturn("Bearer ");
      final var text = "any text";
      when(request.getHeader(configProperties.getHeader())).thenReturn(""+ text);
      assertThat(tokenHelper.getToken(request))
          .isNotSameAs(text);
    }
    @Test
    @DisplayName("testando getToken sucess")
    void auth_null_header_null_null_return() {
      final var request = mock(HttpServletRequest.class);
      final var cookieName = "cookie";
      when(configProperties.getCookie()).thenReturn(cookieName);
      when(request.getCookies()).thenReturn(null);
      when(configProperties.getHeader()).thenReturn("Bearer ");
      when(request.getHeader(configProperties.getHeader())).thenReturn(null);
      assertThat(tokenHelper.getToken(request))
          .isNull();
    }
  }

  @Nested
  @DisplayName("testando getCookieValueByName")
  class GetCookeValueByName {

    @DisplayName("testando o sucesso do metodo")
    @Test
    void success() {
      final var authCookie = mock(Cookie.class);
      final var cookieName = "name";
      HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
      when(httpServletRequest.getCookies()).thenReturn(new Cookie[] {authCookie});
      when(authCookie.getName()).thenReturn(cookieName);
      assertThat(tokenHelper.getCookeValueByName(httpServletRequest, cookieName)).isNotNull();
    }

    @DisplayName("testando entrada null")
    @Test
    void null_entry() {
      assertThatExceptionOfType(NullPointerException.class).isThrownBy(
          () -> tokenHelper.getCookeValueByName(null, "name"));
    }

    @Test
    @DisplayName("testando o metodo com cookies nulls")
    void null_cookie() {
      HttpServletRequest request = mock(HttpServletRequest.class);
      when(request.getCookies()).thenReturn(null);
      assertThat(tokenHelper.getCookeValueByName(request, "name")).isNull();
    }
    @DisplayName("testando o metodo com nome diferente")
    @Test
    void different_names() {

      final var othername = "name2";
      final var authCookie = mock(Cookie.class);
      final var cookieName = "name";
      HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
      when(httpServletRequest.getCookies()).thenReturn(new Cookie[] {authCookie});
      when(authCookie.getName()).thenReturn(cookieName);
      assertThat(tokenHelper.getCookeValueByName(httpServletRequest, cookieName)).isNotEqualTo(tokenHelper.getCookeValueByName(httpServletRequest,othername));
    }

  }

}