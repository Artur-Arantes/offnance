package br.com.artur.offnance.security.auth;


import br.com.artur.offnance.config.ConfigProperties;
import br.com.artur.offnance.domain.TokenState;
import br.com.artur.offnance.domain.User;
import br.com.artur.offnance.security.TokenHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class SuccessfulAuthenticationHandler extends SimpleUrlAuthenticationSuccessHandler {

  private ConfigProperties configProperties;


  @Autowired
  TokenHelper tokenHelper;

  @Autowired
  ObjectMapper objectMapper;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws IOException,
      ServletException {
    clearAuthenticationAttributes(request);
    User user = (User) authentication.getPrincipal();

    String jws = tokenHelper.makeToken(user.getUsername());

    // Cria o token de autorização para o cookie
    Cookie authCookie = new Cookie(configProperties.getCookie(), (jws));

    authCookie.setHttpOnly(true);

    authCookie.setMaxAge(configProperties.getExpiresIn().intValue());

    authCookie.setPath("/");
    // Adiciona o cookie na resposta
    response.addCookie(authCookie);

    // Adiciona o JWT no header também
    TokenState userTokenState = new TokenState(jws, configProperties.getExpiresIn());
    String jwtResponse = objectMapper.writeValueAsString(userTokenState);
    response.setContentType("application/json");
    response.getWriter().write(jwtResponse);

  }
}