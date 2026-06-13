package com.littlesteps.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            )
            .securityContext(securityContext -> securityContext
                    .securityContextRepository(new HttpSessionSecurityContextRepository())
            )
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/auth/**", "/login.html", "/api.css", "/api.js").permitAll()
                    .requestMatchers("/static/**", "/*.html", "/*.css", "/*.js", "/*.png", "/*.jpg", "/*.svg",
                            "/fonts/**", "/favicon.ico").permitAll()
                    .requestMatchers("/").permitAll()
                    .requestMatchers("/api/**").authenticated()
                    .anyRequest().permitAll()
            )
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())
            .logout(logout -> logout
                    .logoutUrl("/api/auth/logout")
                    .logoutSuccessHandler((request, response, authentication) -> {
                        response.setContentType("application/json;charset=UTF-8");
                        response.getWriter().write("{\"success\":true,\"message\":\"已退出登录\",\"data\":null}");
                    })
            );

        return http.build();
    }
}
