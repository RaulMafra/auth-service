package com.auth.emailservice.infra.ses;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import com.auth.emailservice.adapters.EmailSenderGateway;
import com.auth.handler.MessagesExceptions;
import com.auth.handler.exceptions.EmailServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SesEmailSender implements EmailSenderGateway {

    private final AmazonSimpleEmailService amazonSimpleEmailService;

    @Autowired
    public SesEmailSender(AmazonSimpleEmailService amazonSimpleEmailService){
        this.amazonSimpleEmailService = amazonSimpleEmailService;
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        SendEmailRequest request = new SendEmailRequest()
                .withSource("raulcesar.sm@gmail.com")
                .withDestination(new Destination().withToAddresses(to))
                .withMessage(new Message()
                        .withSubject(new Content(subject))
                        .withBody(new Body(new Content(body)))
                );

        try{
            this.amazonSimpleEmailService.sendEmail(request);
        } catch(AmazonSimpleEmailServiceException e){
            throw new EmailServiceException(MessagesExceptions.FAILURE_SEND_EMAIL);
        }
    }
}
