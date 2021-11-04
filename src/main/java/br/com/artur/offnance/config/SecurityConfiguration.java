package br.com.artur.offnance.config;

import br.com.artur.offnance.enums.EnumEntryPoint;
import br.com.artur.offnance.security.auth.AuthenticationTokenFilter;
import br.com.artur.offnance.security.auth.EntryPointAuthenticationRest;
import br.com.artur.offnance.security.auth.FailedAuthenticationHandler;
import br.com.artur.offnance.security.auth.LogoutSuccessful;
import br.com.artur.offnance.security.auth.SuccessfulAuthenticationHandler;
import br.com.artur.offnance.service.imp.CustomUserDetailsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@DependsOn("passwordEncoder")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private ConfigProperties configProperties;

    @Autowired
    private CustomUserDetailsServiceImp customUserDetailsServiceImp;

    @Autowired
    private EntryPointAuthenticationRest entryPointAuthenticationRest;

    @Autowired
    private LogoutSuccessful logoutSuccessful;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SuccessfulAuthenticationHandler successfulAuthenticationHandler;

    @Autowired
    private FailedAuthenticationHandler failedAuthenticationHandler;

    @Bean
    public AuthenticationTokenFilter jwtAuthenticationTokenFilter() throws Exception {
        return new AuthenticationTokenFilter();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder)
            throws Exception {
        authenticationManagerBuilder.userDetailsService(customUserDetailsServiceImp)
                .passwordEncoder(passwordEncoder);

    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and().csrf().disable().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .exceptionHandling().authenticationEntryPoint(entryPointAuthenticationRest).and()
                .authorizeRequests().anyRequest().authenticated().and()
                .addFilterBefore(jwtAuthenticationTokenFilter(), BasicAuthenticationFilter.class)
                .formLogin().loginPage(EnumEntryPoint.LOGIN_ROUTE.getRoute())
                .successHandler(successfulAuthenticationHandler)
                .failureHandler(failedAuthenticationHandler).and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher(EnumEntryPoint.TURN_OFF_ROUTE.getRoute()))
                .logoutSuccessHandler(logoutSuccessful).deleteCookies(configProperties.getCookie());

    }


    // To enable CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("*"));  //set access from all domains
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("**"));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
