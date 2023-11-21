package com.auth.handler;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Bean
    static MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages_en_US");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @ExceptionHandler(BusinessException.class)
    private ResponseEntity<Object> handleBusinessException(Exception e, WebRequest request) {
        String errorMessage = messageSource().getMessage(e.getMessage(), null, Locale.US);
        ResponseError responseError = new ResponseError(HttpStatus.I_AM_A_TEAPOT.value(), errorMessage);
        return createResponseEntity(responseError, headers(), HttpStatus.I_AM_A_TEAPOT, request);
    }

    @ExceptionHandler(AuthenticationException.class)
    private ResponseEntity<Object> handleAuthenticationFail(AuthenticationException e, WebRequest request) {
        if (e.getClass().isAssignableFrom(BadCredentialsException.class)) {
            AuthenticationException exception = new BadCredentialsException(MessagesExceptions.USER_NOT_FOUND_OR_INVALID_PASSWORD);
            String errorMessage = messageSource().getMessage(exception.getMessage(), null, Locale.US);
            ResponseError responseError = new ResponseError(HttpStatus.BAD_REQUEST.value(), errorMessage);
            return createResponseEntity(responseError, headers(), HttpStatus.BAD_REQUEST, request);
        }
        if (e.getClass().isAssignableFrom(InternalAuthenticationServiceException.class)) {
            AuthenticationException exception = new InternalAuthenticationServiceException(MessagesExceptions.USER_NOT_FOUND_OR_INVALID_PASSWORD);
            String errorMessage = messageSource().getMessage(exception.getMessage(), null, Locale.US);
            ResponseError responseError = new ResponseError(HttpStatus.BAD_REQUEST.value(), errorMessage);
            return createResponseEntity(responseError, headers(), HttpStatus.BAD_REQUEST, request);
        }
        if (e.getClass().isAssignableFrom(UsernameNotFoundException.class)) {
            AuthenticationException exception = new UsernameNotFoundException(MessagesExceptions.USER_NOT_FOUND);
            String errorMessage = messageSource().getMessage(exception.getMessage(), null, Locale.US);
            ResponseError responseError = new ResponseError(HttpStatus.NOT_FOUND.value(), errorMessage);
            return createResponseEntity(responseError, headers(), HttpStatus.NOT_FOUND, request);
        }
        ResponseError responseError = new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        e.printStackTrace();
        return createResponseEntity(responseError, headers(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }


    @ExceptionHandler(Exception.class)
    private ResponseEntity<Object> handleGeneral(Exception e, WebRequest request) {
        if (e.getClass().isAssignableFrom(TokenExpiredException.class)) {
            Exception exception = new JWTVerificationException(MessagesExceptions.JWT_VERIFICATION_ERROR);
            String errorMessage = messageSource().getMessage(exception.getMessage(), null, Locale.US);
            ResponseError responsError = new ResponseError(HttpStatus.FORBIDDEN.value(), errorMessage);
            return createResponseEntity(responsError, headers(), HttpStatus.FORBIDDEN, request);
        }
        String errorMessage = messageSource().getMessage(MessagesExceptions.EXCEPTION_UNEXPECTED, null, Locale.US);
        ResponseError responseError = new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessage);
        e.printStackTrace();
        return createResponseEntity(responseError, headers(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }


}