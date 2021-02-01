package com.thehecklers.planefinder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange()
                .pathMatchers("/aircraft/**").hasAuthority("SCOPE_closedid")
                .pathMatchers("/aircraftadmin/**").hasAuthority("SCOPE_openid")
                .and().oauth2ResourceServer().jwt();

        return http.build();
    }
}
