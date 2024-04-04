package org.example.schoolmanagementsystemspring.config;

import lombok.RequiredArgsConstructor;
import org.example.schoolmanagementsystemspring.auth.AuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

/**
 * @author FFreitas
 * @LinkedIn: <a href="https://www.linkedin.com/in/francisco-freitas-a289b91b3/">Francisco Freitas</a>
 * @Github: <a href="https://github.com/FFreitas997">FFreitas997</a>
 * @Project: School-Management-System-Spring
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private static final String[] PERMIT_ALL_URLS = {
            "/api/v1/auth/**",
            "/actuator/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"
    };

    private final AuthenticationFilter authenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF because we are using JWT authentication
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(PERMIT_ALL_URLS).permitAll();

                    /*auth.requestMatchers("/api/v1/student/**").hasAnyRole(ADMIN.name(), STUDENT.name(), TEACHER.name(), PARENT.name());
                    auth.requestMatchers(HttpMethod.GET, "/api/v1/student/**").hasAnyAuthority(ADMIN_READ.name(), STUDENT_READ.name(), ADMIN_READ.name(), STUDENT_READ.name());
                    auth.requestMatchers(HttpMethod.POST, "/api/v1/student/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name());
                    auth.requestMatchers(HttpMethod.PUT, "/api/v1/student/**").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name());
                    auth.requestMatchers(HttpMethod.DELETE, "/api/v1/student/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name());*/

                    auth.anyRequest().authenticated();
                })
                .sessionManagement(param -> param.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(param -> {
                    param.logoutUrl("/api/v1/auth/logout");
                    param.addLogoutHandler(logoutHandler);
                    param.logoutSuccessHandler((req, res, auth) -> SecurityContextHolder.clearContext());
                })
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}