package br.com.artur.offnance.security.auth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class FailedAuthenticationHandler extends SimpleUrlAuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(final HttpServletRequest request,final HttpServletResponse response,
                                      final AuthenticationException exception) throws IOException,
      ServletException {

    super.onAuthenticationFailure(request, response, exception);
  }
}