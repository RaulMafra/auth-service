package com.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

public class TranslatedExceptionsMessagesTest {

    private static final String UNEXPECTED_ERROR = "exception.unexpected";

    @Test
    public void givenExceptionTranslator() {
        String errorMessage = messageSource().getMessage(UNEXPECTED_ERROR, null, Locale.US);
        Assertions.assertEquals(errorMessage, "An unexpected exception occured while processing your request.");
    }

    @Bean
    static MessageSource messageSource(){
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages_en_US");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

}

