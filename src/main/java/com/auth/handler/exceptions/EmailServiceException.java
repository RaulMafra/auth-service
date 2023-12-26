package com.auth.handler.exceptions;

import com.amazonaws.AmazonServiceException;

public class EmailServiceException extends AmazonServiceException {

    public EmailServiceException(String msg) {
        super(msg);
    }

}
