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

      public Authentication() {
      }

      @Getter
      @Setter
      @ConfigurationProperties(prefix = "jwt")
      public static class Jwt {
        public static final String DEFAULT_SECRET = null;
        public static final String BASE_64_SECRET = null;
        private String secret;
        private String base64Secret;
        private long tokenValidityInSeconds;
        private long tokenValidityInSecondsForRememberMe;

        public Jwt() {
          this.secret = DEFAULT_SECRET;
          this.base64Secret = BASE_64_SECRET;
          this.tokenValidityInSeconds = 1800L;
          this.tokenValidityInSecondsForRememberMe = 2592000L;
        }
      }
    }


  }
}
