package com.puc.edificad.commons;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class StaticContextAccessor implements ApplicationContextAware {

    private static  ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContextIn) throws BeansException {
        StaticContextAccessor.setContext(applicationContextIn);
    }

    private static void setContext(ApplicationContext applicationContextIn){
        applicationContext = applicationContextIn;
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

}