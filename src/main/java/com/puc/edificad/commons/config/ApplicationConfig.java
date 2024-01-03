package com.puc.edificad.commons.config;

import com.jnunes.spgcore.commons.config.Message;
import com.puc.edificad.web.support.BeneficiarioConverter;
import com.puc.edificad.web.support.FornecedorConverter;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {
    @Bean
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:/messages/messages", "classpath:/messages/spgauth-messages");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(5);
        return messageSource;
    }

    @Bean
    public Message message() {
        return new Message();
    }


    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new BeneficiarioConverter());
        registry.addConverter(new FornecedorConverter());
    }

}