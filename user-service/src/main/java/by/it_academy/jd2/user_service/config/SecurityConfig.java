package by.it_academy.jd2.user_service.config;

import by.it_academy.jd2.user_service.controller.filter.JWTFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JWTFilter jwtFilter) throws Exception {

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.cors(AbstractHttpConfigurer::disable);

        httpSecurity.sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        httpSecurity.exceptionHandling((exceptionHandling) ->
                exceptionHandling
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        })
        );

        httpSecurity.authorizeHttpRequests(requests ->
                requests.requestMatchers("/users", "users/*").hasAnyRole("ADMIN")
                        .requestMatchers("/cabinet/me").authenticated()
                        .requestMatchers("/cabinet/login").permitAll()
                        .requestMatchers("/cabinet/verification").permitAll()
                        .requestMatchers("/cabinet/registration").anonymous()
                        .requestMatchers("/users_data").permitAll()
                        .anyRequest().authenticated()

        );

        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();

    }




}
