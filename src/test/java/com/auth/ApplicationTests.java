package com.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.annotation.Bean;

import java.beans.BeanProperty;
import java.util.Locale;

@SpringBootTest
class ApplicationTests {

    @Test
    void contextLoads() {
    }

    private static final String UNEXPECTED_ERROR = "Exception.unexpected";


    @Test
    public void givenExceptionTranslator() {
        String errorMessage = Testx.messageSource().getMessage(UNEXPECTED_ERROR, null, Locale.US);
        Assertions.assertEquals(errorMessage, "An unexpected exception occured while processing your request.");
    }



}

class Testx {

    @Bean
    static MessageSource messageSource(){
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages_en_US");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

}
