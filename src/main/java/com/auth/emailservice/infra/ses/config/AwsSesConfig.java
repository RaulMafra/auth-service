package com.auth.emailservice.infra.ses.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.auth.configs.ses.SesConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsSesConfig {

    @Bean
    public AmazonSimpleEmailService amazonSimpleEmailService() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(SesConfig.ACCESS_KEY_ID, SesConfig.SECRET_KEY);
        return AmazonSimpleEmailServiceClientBuilder.standard()
                .withRegion(SesConfig.REGION)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();

    }

}
