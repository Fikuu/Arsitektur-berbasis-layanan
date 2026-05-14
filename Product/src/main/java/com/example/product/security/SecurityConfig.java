package com.example.product.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        // Actuator / Prometheus
                        .requestMatchers("/actuator/**").permitAll()

                        // endpoint public
                        .requestMatchers("/api/produk/public/**").permitAll()

                        // admin only
                        .requestMatchers("/api/produk/admin/**").hasRole("ADMIN")

                        // authenticated users
                        .requestMatchers("/api/produk/**").authenticated()

                        // sisanya
                        .anyRequest().permitAll())

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // penting untuk spring security
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}