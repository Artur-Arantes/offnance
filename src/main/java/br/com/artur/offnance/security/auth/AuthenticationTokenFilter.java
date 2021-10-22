package br.com.artur.offnance.security.auth;

import br.com.artur.offnance.security.TokenHelper;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthenticationTokenFilter extends OncePerRequestFilter {

  @Autowired
  TokenHelper tokenHelper;

  @Autowired
  UserDetailsService userDetailsService;

  public static final String ROOT_MATCHER = "/";
  public static final String FAVICON_MATCHER = "/favicon.ico";
  public static final String HTML_MATCHER = "/**/*.html";
  public static final String CSS_MATCHER = "/**/*.css";
  public static final String JS_MATCHER = "/**/*.js";
  public static final String IMG_MATCHER = "/images/*";
  public static final String LOGIN_MATCHER = "/auth/login";
  public static final String LOGOUT_MATCHER = "/auth/logout";

  private List<String> routeToMiss = Arrays.asList(
      ROOT_MATCHER,
      HTML_MATCHER,
      FAVICON_MATCHER,
      CSS_MATCHER,
      JS_MATCHER,
      IMG_MATCHER,
      LOGIN_MATCHER,
      LOGOUT_MATCHER
  );




  @Override
  public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

    String tokenDeAutorizacao = tokenHelper.getToken(request);
    if (tokenDeAutorizacao != null && !routeToMiss(request, routeToMiss)) {
      try {
        final var user = tokenHelper.getUsernameFromToken(tokenDeAutorizacao);
        final var userDetails = userDetailsService.loadUserByUsername(user);
        final var authetication = new TokenBasedAuthentication(userDetails);
        authetication.setToken(tokenDeAutorizacao);
        SecurityContextHolder.getContext().setAuthentication(authetication);
      } catch (Exception e) {
        SecurityContextHolder.getContext().setAuthentication(new AnonymousAuthentication());
      }
    } else {
      SecurityContextHolder.getContext().setAuthentication(new AnonymousAuthentication());
    }

    chain.doFilter(request, response);
  }

  private boolean routeToMiss(HttpServletRequest requesition, List<String> routeToMiss ) {
    Assert.notNull(routeToMiss, "rota não pode ser nula;");
    List<RequestMatcher> routesToMiss = routeToMiss.stream().map(path ->
        new AntPathRequestMatcher(path)).collect(Collectors.toList());
    OrRequestMatcher matchers = new OrRequestMatcher(routesToMiss);
    return matchers.matches(requesition);
  }
}
