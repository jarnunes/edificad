package com.puc.edificad.web.config;

import com.puc.edificad.model.edsuser.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private static final String API_PATTERN = "/api/**";
    private AuthenticationProvider authProvider;

    @Autowired
    public void setAuthProvider(AuthenticationProvider authProvider) {
        this.authProvider = authProvider;
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authProvider);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain apiFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(ssm -> ssm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(mvc.pattern(POST, "/api/auth/**")).permitAll()
                        .requestMatchers(mvc.pattern(GET, API_PATTERN)).hasRole(Role.RL_WEBSERVICES)
                        .requestMatchers(mvc.pattern(POST, API_PATTERN)).hasRole(Role.RL_WEBSERVICES)
                        .requestMatchers(mvc.pattern(PUT, API_PATTERN)).hasRole(Role.RL_WEBSERVICES)
                        .requestMatchers(mvc.pattern(DELETE, API_PATTERN)).hasRole(Role.RL_WEBSERVICES)
                        .anyRequest().authenticated()
                )
                .addFilterBefore(getTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(handler -> handler.authenticationEntryPoint(getEntryPoint()))
                .build();
    }

    @Bean
    TokenFilter getTokenFilter() {
        return new TokenFilter();
    }

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    ApiAuthenticationEntryPoint getEntryPoint(){
        return new ApiAuthenticationEntryPoint();
    }

}
