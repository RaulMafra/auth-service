package com.auth.handler;

import com.amazonaws.AmazonServiceException;
import com.auth.handler.exceptions.BusinessException;
import com.auth.handler.exceptions.CheckFieldsException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.dao.DataIntegrityViolationException;
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
    private ResponseEntity<Object> handleBusinessException(BusinessException e, WebRequest request, HttpServletRequest httpRequest) {
        if (e.getClass().isAssignableFrom(BusinessException.class)) {
            String message = messageSource().getMessage(e.getMessage(), null, Locale.US);
            ResponseError responseError = new ResponseError(HttpStatus.I_AM_A_TEAPOT.value(), HttpStatus.I_AM_A_TEAPOT, message, httpRequest.getRequestURI());
            return createResponseEntity(responseError, headers(), HttpStatus.I_AM_A_TEAPOT, request);
        }
        if (e.getClass().isAssignableFrom(CheckFieldsException.class)) {
            String message = messageSource().getMessage(e.getMessage(), null, Locale.US);
            ResponseError responseError = new ResponseError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, message, httpRequest.getRequestURI());
            return createResponseEntity(responseError, headers(), HttpStatus.BAD_REQUEST, request);
        }
        return handleGeneral(e, request, httpRequest);
    }

    @ExceptionHandler(AuthenticationException.class)
    private ResponseEntity<Object> handleAuthenticationFail(AuthenticationException e, WebRequest request, HttpServletRequest httpRequest) {
        if (e.getClass().isAssignableFrom(BadCredentialsException.class)) {
            AuthenticationException exception = new BadCredentialsException(MessagesExceptions.USER_NOT_FOUND_OR_INVALID_PASSWORD);
            String message = messageSource().getMessage(exception.getMessage(), null, Locale.US);
            ResponseError responseError = new ResponseError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED, message, httpRequest.getRequestURI());
            return createResponseEntity(responseError, headers(), HttpStatus.UNAUTHORIZED, request);
        }
        if (e.getClass().isAssignableFrom(InternalAuthenticationServiceException.class)) {
            AuthenticationException exception = new InternalAuthenticationServiceException(MessagesExceptions.USER_NOT_FOUND_OR_INVALID_PASSWORD);
            String message = messageSource().getMessage(exception.getMessage(), null, Locale.US);
            ResponseError responseError = new ResponseError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED, message, httpRequest.getRequestURI());
            return createResponseEntity(responseError, headers(), HttpStatus.UNAUTHORIZED, request);
        }
        if (e.getClass().isAssignableFrom(UsernameNotFoundException.class)) {
            AuthenticationException exception = new UsernameNotFoundException(e.getMessage());
            String message = messageSource().getMessage(exception.getMessage(), null, Locale.US);
            String[] nameUser = httpRequest.getRequestURI().split("/");
            ResponseError responseError = new ResponseError(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, message.concat(" " + nameUser[2]), httpRequest.getRequestURI());
            return createResponseEntity(responseError, headers(), HttpStatus.NOT_FOUND, request);
        }
        return handleGeneral(e, request, httpRequest);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    private ResponseEntity<Object> handleDatasPersistence(DataIntegrityViolationException e, WebRequest request, HttpServletRequest httpRequest) {
        DataIntegrityViolationException exception = new DataIntegrityViolationException(MessagesExceptions.INTEGRITY_VIOLATION);
        String message = messageSource().getMessage(exception.getMessage(), null, Locale.US);
        ResponseError responseError = new ResponseError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, message, httpRequest.getRequestURI());
        return createResponseEntity(responseError, headers(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(AmazonServiceException.class)
    public ResponseEntity<Object> handleEmailSending(AmazonServiceException e, WebRequest request, HttpServletRequest httpRequest){
        AmazonServiceException exception = new AmazonServiceException(MessagesExceptions.FAILURE_SEND_EMAIL);
        String message = messageSource().getMessage(exception.getMessage(), null, Locale.US);
        ResponseError responseError = new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, message, httpRequest.getRequestURI());
        return createResponseEntity(responseError, headers(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }


    @ExceptionHandler(Exception.class)
    private ResponseEntity<Object> handleGeneral(Exception e, WebRequest request, HttpServletRequest httpRequest) {
        String message = messageSource().getMessage(MessagesExceptions.EXCEPTION_UNEXPECTED, null, Locale.US);
        ResponseError responseError = new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, message, httpRequest.getRequestURI());
        e.printStackTrace();
        return createResponseEntity(responseError, headers(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}

