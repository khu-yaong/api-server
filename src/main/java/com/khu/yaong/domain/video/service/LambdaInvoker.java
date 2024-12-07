package com.khu.yaong.domain.video.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khu.yaong.domain.video.dto.VideoReqDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

@Component
public class LambdaInvoker {

    @Value("${cloud.aws.accounts.first-account.lambda.url}")
    private String functionUrl;

    @Async
    public CompletableFuture<String> updateRecommendVideos(VideoReqDTO.LambdaRequestDTO lambdaRequestDTO) {
        callLambda(lambdaRequestDTO);
        return CompletableFuture.completedFuture("Success");
    }

    public void callLambda(VideoReqDTO.LambdaRequestDTO lambdaRequestDTO) {

        try {
            // Lambda 호출을 위한 HttpClient 설정
            HttpClient httpClient = HttpClient.newHttpClient();

            // JSON 변환
            ObjectMapper objectMapper = new ObjectMapper();
            String payload = objectMapper.writeValueAsString(lambdaRequestDTO);

            // Lambda URL로 요청을 보내기 위한 HttpRequest
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(functionUrl))
                    .header("Content-Type", "application/json")
                    .header("x-amz-invocation-type", "Event")  // 또는 "RequestResponse"
                    .POST(HttpRequest.BodyPublishers.ofString(payload))
                    .build();

            // 요청 전송
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // 응답 확인
            System.out.println("Invoke Status : " + response.statusCode());

        } catch (JsonProcessingException jpe) {
            jpe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
