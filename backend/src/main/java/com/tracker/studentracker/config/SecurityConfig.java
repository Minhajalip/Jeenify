package com.tracker.studentracker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            throw new UsernameNotFoundException("Use JWT authentication");
        };
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(
            "http://localhost:3000",
            "http://localhost:5173"
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/auth/register").permitAll()
                        .requestMatchers("/api/auth/logout").permitAll()

                        .requestMatchers(HttpMethod.GET, "/courses/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/courses/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/courses/**").hasRole("ADMIN")

                        .requestMatchers("/api/students/register").permitAll()
                        .requestMatchers("/api/students/select-courses").hasAnyRole("TEACHER", "ADMIN")
                        .requestMatchers("/api/students/approve/**").hasRole("ADMIN")
                        .requestMatchers("/api/departments/**").hasRole("ADMIN")
                        .requestMatchers("/api/exams/**").hasAnyRole("TEACHER", "ADMIN", "STUDENT")
                        .requestMatchers("/api/assignments/**").hasAnyRole("TEACHER", "ADMIN", "STUDENT")
                        .requestMatchers("/api/marks/**").hasAnyRole("TEACHER", "ADMIN", "STUDENT")

                        // Attendance claims - students can submit, view and delete their own
                        .requestMatchers(HttpMethod.POST, "/api/attendance/claims").hasAnyRole("STUDENT", "TEACHER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/attendance/claims/student/**").hasAnyRole("STUDENT", "TEACHER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/attendance/claims/**").hasAnyRole("STUDENT", "TEACHER", "ADMIN")

                        // Attendance claims - teacher/admin only
                        .requestMatchers(HttpMethod.GET, "/api/attendance/claims/**").hasAnyRole("TEACHER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/attendance/claims/**").hasAnyRole("TEACHER", "ADMIN")

                        // Rest of attendance - teacher/admin only
                        .requestMatchers("/api/attendance/**").hasAnyRole("TEACHER", "ADMIN")

                        .requestMatchers("/api/reports/**").hasAnyRole("STUDENT", "TEACHER", "ADMIN")

                        // Timetable - get is public, create/delete is admin only
                        .requestMatchers(HttpMethod.GET, "/api/timetable/**").hasAnyRole("STUDENT", "TEACHER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/timetable/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/timetable/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/timetable/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/calendar/**").hasAnyRole("STUDENT", "TEACHER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/calendar/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/calendar/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/calendar/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}