package com.khu.yaong.global.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class S3Config {

    @Value("${cloud.aws.accounts.first-account.credentials.access-key}")
    private String firstAccessKey;

    @Value("${cloud.aws.accounts.first-account.credentials.secret-key}")
    private String firstSecretKey;

    @Value("${cloud.aws.accounts.first-account.region.static}")
    private String firstRegion;

    @Value("${cloud.aws.accounts.second-account.credentials.access-key}")
    private String secondAccessKey;

    @Value("${cloud.aws.accounts.second-account.credentials.secret-key}")
    private String secondSecretKey;

    @Value("${cloud.aws.accounts.second-account.region.static}")
    private String secondRegion;

    @Bean(name = "firstAmazonS3")
    @Primary
    public AmazonS3 firstAmazonS3() {
        AWSCredentials credentials = new BasicAWSCredentials(firstAccessKey, firstSecretKey);

        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(firstRegion)
                .build();
    }

    @Bean(name = "secondAmazonS3")
    public AmazonS3 secondAmazonS3() {
        AWSCredentials credentials = new BasicAWSCredentials(secondAccessKey, secondSecretKey);

        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(secondRegion)
                .build();
    }
}