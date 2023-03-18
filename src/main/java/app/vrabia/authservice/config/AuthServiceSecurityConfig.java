package app.vrabia.authservice.config;

import app.vrabia.vrcommon.config.security.FilterChainExceptionHandlerFilter;
import app.vrabia.vrcommon.config.security.SecurityConfig;
import app.vrabia.vrcommon.models.security.PublicEndpoints;
import app.vrabia.vrcommon.service.JWTService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AuthServiceSecurityConfig extends SecurityConfig {

    private final UserDetailsService userDetailsService;

    public AuthServiceSecurityConfig(JWTService jwtService, PublicEndpoints publicEndpoints, FilterChainExceptionHandlerFilter filterChainExceptionHandlerFilter, UserDetailsService userDetailsService) {
        super(jwtService, publicEndpoints, filterChainExceptionHandlerFilter);
        this.userDetailsService = userDetailsService;
    }

    @Bean
    @Override
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        super.securityFilterChain(http);
        http.authorizeHttpRequests().anyRequest().authenticated();
        http.authenticationProvider(authenticationProvider());
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
