package com.puc.edificad.web.config;

import com.jnunes.spgauth.model.Role;
import com.jnunes.spgauth.web.AuthWebSecurityConst;
import com.puc.edificad.web.handlers.CustomAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private static final String API_PATTERN = "/api/**";
    private static final String API_CONFIG_PATTERN = "/api/config/**";
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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests ->
                        requests.requestMatchers("/",
                                        AuthWebSecurityConst.REQUEST_PERMIT_CHANGE_PWD,
                                        AuthWebSecurityConst.REQUEST_PERMIT_SAVE_PWD,
                                        API_PATTERN,
                                        "/error",
                                        "/css/**",
                                        "/js/**",
                                        "/images/**",
                                        "/webjars/**")
                                .permitAll()
                                .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin
                        .loginPage(AuthWebSecurityConst.REQUEST_LOGIN_PAGE)
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/dashboard")
                        .permitAll())
                .logout(formLogout -> formLogout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl(AuthWebSecurityConst.REQUEST_LOGIN_PAGE)
                        .deleteCookies("JSESSIONID")
                        .permitAll());

        return http.build();
    }


//    @Bean
//    @Order(2)
//    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .cors(Customizer.withDefaults())
//                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement(ssm -> ssm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers(API_PATTERN).permitAll()
//                        .requestMatchers(POST, "/api/auth/login").permitAll()
//                        .requestMatchers(POST, "/api/auth/create").hasRole(Role.RL_ADMIN)
//                        .requestMatchers(POST, API_CONFIG_PATTERN).hasRole(Role.RL_ADMIN)
//                        .requestMatchers(GET, API_CONFIG_PATTERN).hasRole(Role.RL_ADMIN)
//                        .requestMatchers(PUT, API_CONFIG_PATTERN).hasRole(Role.RL_ADMIN)
//                        .requestMatchers(GET, API_PATTERN).hasAnyRole(webServiceOperatorRoles())
//                        .requestMatchers(POST, API_PATTERN).hasAnyRole(webServiceOperatorRoles())
//                        .requestMatchers(PUT, API_PATTERN).hasAnyRole(webServiceOperatorRoles())
//                        .requestMatchers(DELETE, API_PATTERN).hasAnyRole(webServiceOperatorRoles())
//                        .anyRequest().authenticated())
//                .addFilterBefore(getTokenFilter(), UsernamePasswordAuthenticationFilter.class)
//                .exceptionHandling(handler -> handler.authenticationEntryPoint(new ApiAuthenticationEntryPoint()))
//                .exceptionHandling(handler -> handler.accessDeniedHandler(new CustomAccessDeniedHandler()))
//                .build();
//    }
//
//    @Bean
//    TokenFilter getTokenFilter() {
//        return new TokenFilter();
//    }
//
//    @Bean
//    String[] webServiceOperatorRoles() {
//        return new String[]{Role.RL_WEBSERVICES, Role.RL_OPERATOR};
//    }
//
//
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("*"));
//        configuration.setAllowedMethods(List.of("*"));
//        configuration.setAllowedHeaders(List.of("*"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
}
