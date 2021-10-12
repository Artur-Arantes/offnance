package br.com.artur.offnance.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ConfigurationProperties(
        prefix = "configs",
        ignoreUnknownFields = false
)
@Getter
public class ConfigProperties {
    private Security security;

    @Getter
    public static class Security {
        @Setter
        private String contentSecurityPolicy = "default-src 'self'; frame-src 'self' data:; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com; style-src 'self' 'unsafe-inline'; img-src 'self' data:; font-src 'self' data:";
        private final ConfigProperties.Security.ClientAuthorization clientAuthorization = new ConfigProperties.Security.ClientAuthorization();
        private final ConfigProperties.Security.Authentication authentication = new ConfigProperties.Security.Authentication();
        private final ConfigProperties.Security.RememberMe rememberMe = new ConfigProperties.Security.RememberMe();
        private final ConfigProperties.Security.OAuth2 oauth2 = new ConfigProperties.Security.OAuth2();

        public Security() {
        }

        @Getter
        public static class OAuth2 {
            private List<String> audience = new ArrayList();

            public OAuth2() {
            }

            public List<String> getAudience() {
                return Collections.unmodifiableList(this.audience);
            }

            public void setAudience(List<String> audience) {
                this.audience.addAll(audience);
            }
        }

        @Getter
        @Setter
        public static class RememberMe {
            public static final String DEFAULT_KEY = null;
            private String key;

            public RememberMe() {
                this.key = DEFAULT_KEY;
            }
        }

        @Getter
        public static class Authentication {
            private final ConfigProperties.Security.Authentication.Jwt jwt = new ConfigProperties.Security.Authentication.Jwt();

            public Authentication() {
            }

            @Getter
            @Setter
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

        @NoArgsConstructor
        @Getter
        @Setter
        public static class ClientAuthorization {
            private String accessTokenUri;
            private String tokenServiceId;
            private String clientId;
            private String clientSecret;
        }
    }
}
