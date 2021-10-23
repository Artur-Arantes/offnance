package br.com.artur.offnance.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
    prefix = "configs",
    ignoreUnknownFields = true
)
@Setter
@Getter
public class ConfigProperties {
  private Security security;

  public String getCookie() {
    return this.getSecurity().getAuthentication().getJwt().getCookie();
  }

  public Long getExpiresIn() {
    return this.getSecurity().getAuthentication().getJwt().getExpiresIn();
  }

  public String getHeader() {
    return this.getSecurity().getAuthentication().getJwt().getHeader();
  }

  public String getAppAuthroization() {
    return this.getSecurity().getAuthentication().getJwt().getHeader();
  }

  @ConfigurationProperties(prefix = "security")
  @Getter
  @NoArgsConstructor
  public static class Security {
    @Setter
    private String contentSecurityPolicy =
        "default-src 'self'; frame-src 'self' data:; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com; style-src 'self' 'unsafe-inline'; img-src 'self' data:; font-src 'self' data:";
    private final Authentication authentication =
        new Authentication();

    @Getter
    @ConfigurationProperties(prefix = "authentication")
    public static class Authentication {
      private final Jwt jwt =
          new Jwt();

      @Getter
      @Setter
      @ConfigurationProperties(prefix = "jwt")
      public static class Jwt {
        private String appAuthorization;
        private String header;
        private Long expiresIn;
        private String cookie;


      }
    }


  }
}
