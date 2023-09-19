package com.puc.edificad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.puc.edificad"})
@EntityScan(basePackages = {"com.puc.edificad"})
@EnableJpaRepositories(basePackages = {"com.puc.edificad"})
public class EdificadApplication {

    public static void main(String[] args) {
        SpringApplication.run(EdificadApplication.class, args);
    }

}
