package com.puc.edificad.commons.config;

import com.jnunes.spgauth.commons.config.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaConfig {

    @Bean
    AuditorAware<String> auditorAware(){
        return new AuditorAwareImpl();
    }
}
