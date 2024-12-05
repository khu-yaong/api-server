package com.khu.yaong.domain.video.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khu.yaong.domain.video.dto.VideoReqDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;

import java.util.concurrent.CompletableFuture;

@Component
public class LambdaInvoker {

    @Value("${cloud.aws.accounts.first-account.region.static}")
    private String region;

    @Value("${cloud.aws.accounts.first-account.lambda.url}")
    private String functionUrl;

    @Async
    public CompletableFuture<String> updateRecommendVideos(VideoReqDTO.LambdaRequestDTO lambdaRequestDTO) {
        callLambda(lambdaRequestDTO);
        return CompletableFuture.completedFuture("Success");
    }

    public void callLambda(VideoReqDTO.LambdaRequestDTO lambdaRequestDTO) {

        LambdaClient lambdaClient = LambdaClient.builder()
                .region(Region.of(region))
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String payload = objectMapper.writeValueAsString(lambdaRequestDTO);

            InvokeRequest invokeRequest = InvokeRequest.builder()
                    .functionName(functionUrl)
                    .payload(SdkBytes.fromUtf8String(payload))
                    .build();

            InvokeResponse invokeResponse = lambdaClient.invoke(invokeRequest);
            System.out.println("Invoke Status : " + invokeResponse.statusCode());
            lambdaClient.close();

        } catch (JsonProcessingException jpe) {
            jpe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lambdaClient.close();
        }
    }
}
