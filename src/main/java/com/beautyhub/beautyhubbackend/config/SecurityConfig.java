package com.beautyhub.beautyhubbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder()
                        .encode("admin123"))
                .roles("ADMIN")
                .build();

        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder()
                        .encode("user123"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(
                admin, user);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth

                        // Public routes
                        .requestMatchers("/login")
                        .permitAll()
                        .requestMatchers("/access-denied")
                        .permitAll()
                        .requestMatchers("/h2-console/**")
                        .permitAll()

                        // ADMIN only — web delete
                        .requestMatchers(
                                "/countries/delete/**")
                        .hasRole("ADMIN")
                        .requestMatchers(
                                "/companies/delete/**")
                        .hasRole("ADMIN")
                        .requestMatchers(
                                "/shopowners/delete/**")
                        .hasRole("ADMIN")
                        .requestMatchers(
                                "/persons/delete/**")
                        .hasRole("ADMIN")
                        .requestMatchers(
                                "/products/delete/**")
                        .hasRole("ADMIN")

                        // ADMIN only — REST API delete
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/**")
                        .hasRole("ADMIN")

                        // Everything else needs login
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/countries",
                                true)
                        .failureUrl("/login?error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                )
                .exceptionHandling(ex -> ex
                                .accessDeniedPage("/access-denied")

                                // 401 for unauthenticated REST API requests
                                .defaultAuthenticationEntryPointFor(
                                        (request, response, authException) ->
                                                response.sendError(
                                                        401, "Unauthorized"),
                                        new AntPathRequestMatcher("/api/**")
                                )

                        // 302 redirect for unauthenticated Web requests
                        // (Spring default handles this automatically
                        // via formLogin redirect to /login)
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(
                                "/h2-console/**")
                        .ignoringRequestMatchers(
                                "/api/**")
                )
                .headers(headers -> headers
                        .frameOptions(frame ->
                                frame.disable())
                );

        return http.build();
    }
}