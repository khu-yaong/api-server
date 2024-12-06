package com.khu.yaong.global.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.khu.yaong.domain.video.dto.VideoResDTO;
import com.khu.yaong.global.common.exception.BaseException;
import com.khu.yaong.global.common.response.s3.S3ErrorCode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class S3JsonService {

    private final AmazonS3 amazonS3;

    public S3JsonService(@Qualifier("firstAmazonS3") AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Value("${cloud.aws.accounts.first-account.s3.bucket-name}")
    private String bucketName;

    public String downloadJson(String filename) {

        S3Object s3Object = amazonS3.getObject(bucketName, filename);
        S3ObjectInputStream s3InputStream = s3Object.getObjectContent();

        // InputStream -> Reader
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(s3InputStream))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
            return jsonContent.toString();
        } catch (IOException e) {
            throw new BaseException(S3ErrorCode.IO_EXCEPTION);
        }
    }
}
