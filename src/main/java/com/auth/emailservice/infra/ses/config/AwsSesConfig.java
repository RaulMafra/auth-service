package com.auth.emailservice.infra.ses.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsSesConfig {

    private final String REGION = System.getenv().get("AWS_REGION");
    private final String ACCESS_KEY_ID = System.getenv().get("AWS_ACCESS_KEY_ID");
    private final String SECRET_KEY = System.getenv().get("AWS_SECRET_KEY");

    @Bean
    public AmazonSimpleEmailService amazonSimpleEmailService() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY_ID, SECRET_KEY);
        return AmazonSimpleEmailServiceClientBuilder.standard()
                .withRegion(REGION)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();

    }

}
