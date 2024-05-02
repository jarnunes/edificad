package com.puc.edificad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.jnunes", "com.puc"})
@EntityScan(basePackages = {"com.jnunes", "com.puc"})
@EnableJpaRepositories(basePackages = {"com.jnunes", "com.puc"})
public class EdificadApplication {

    public static void main(String[] args) {
        SpringApplication.run(EdificadApplication.class, args);
    }



    @Bean
    public ErrorProperties getError(){
        return new ErrorProperties();
    }
}
