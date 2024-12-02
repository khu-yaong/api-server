package com.khu.yaong.global.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.khu.yaong.domain.mp3.domain.Mp3File;
import com.khu.yaong.global.common.exception.BaseException;
import com.khu.yaong.global.common.response.s3.S3ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementPermission;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class S3Mp3Service {
    private final AmazonS3 amazonS3;

    public S3Mp3Service(@Qualifier("secondAmazonS3") AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Value("${cloud.aws.accounts.second-account.s3.bucket-name}")
    private String bucketName;

    public List<Mp3FileDTO> listFilesInBucket() {
        List<Mp3FileDTO> files = new ArrayList<>();

        ListObjectsV2Request request = new ListObjectsV2Request()
                .withBucketName(bucketName)
                .withPrefix("baseballSong/");
        ListObjectsV2Result result;

        do {
            result = amazonS3.listObjectsV2(request);

            for (S3ObjectSummary summary : result.getObjectSummaries()) {
                String fileName = summary.getKey()
                        .substring(summary.getKey().lastIndexOf('/') + 1);
                if (fileName.endsWith(".mp3")) {
                    String fileUrl = amazonS3.getUrl(bucketName, summary.getKey()).toString();
                    files.add(new Mp3FileDTO(fileName, fileUrl));
                }
            }
            request.setContinuationToken(result.getNextContinuationToken());
        } while (result.isTruncated());
        return files;
    }

    @Getter
    public static class Mp3FileDTO {
        private final String fileName;
        private final String mp3Url;

        public Mp3FileDTO(String fileName, String mp3Url) {
            this.fileName = fileName;
            this.mp3Url = mp3Url;
        }

    }

    public String uploadMp3(String dir, MultipartFile mp3) {
        // image가 비어있으면 오류
        if (mp3.isEmpty() || Objects.isNull(mp3.getOriginalFilename())) {
            throw new BaseException(S3ErrorCode.FILE_IS_NULL);
        }
        return uploadMp3WithPath(dir, mp3);
    }

    public String uploadMp3WithPath(String dir, MultipartFile mp3) {
        // mp3가 비어있으면 오류
        if (mp3.isEmpty() || Objects.isNull(mp3.getOriginalFilename())) {
            throw new BaseException(S3ErrorCode.FILE_IS_NULL);
        }
        String originalFilename = mp3.getOriginalFilename(); // 원본 파일명
        String extension = validateMp3FileExtension(originalFilename);

        try {
            String s3FileName = dir + UUID.randomUUID().toString().substring(0,10) + originalFilename;
            InputStream is = mp3.getInputStream();
            uploadMp3ToS3(is, extension, s3FileName);
            return amazonS3.getUrl(bucketName, s3FileName).toString();
        } catch (IOException e){
            throw new BaseException(S3ErrorCode.IO_EXCEPTION);
        }
    }

    private static String validateMp3FileExtension(String filename){
        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex == -1) {
            throw new BaseException(S3ErrorCode.BAD_FILE_EXTENSION);
        }

        String extension = filename.substring(lastDotIndex + 1).toLowerCase();
        List<String> allowedExtentionList = Arrays.asList("mp3");

        if (!allowedExtentionList.contains(extension)) {
            throw new BaseException(S3ErrorCode.BAD_FILE_EXTENSION);
        }
        return extension;
    }

    private void uploadMp3ToS3(InputStream is, String extension, String s3FileName){
        try{
            byte[] bytes = IOUtils.toByteArray(is);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("mp3/" + extension);
            metadata.setContentLength(bytes.length);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

            try {
                PutObjectRequest putObjectRequest =
                        new PutObjectRequest(bucketName, s3FileName, byteArrayInputStream, metadata)
                                .withCannedAcl(CannedAccessControlList.PublicRead);
                amazonS3.putObject(putObjectRequest);
            } catch (Exception e) {
                throw new BaseException(S3ErrorCode.PUT_OBJECT_EXCEPTION);
            } finally {
                byteArrayInputStream.close();
                is.close();
            }
        } catch (IOException e){
            throw new BaseException(S3ErrorCode.IO_EXCEPTION);
        }
    }

    public void deleteImageFromS3(String mp3) {
        if (mp3 == null || mp3.isEmpty()) {
            return ;
        }
        String key = getKeyFromMp3Address(mp3);
        try{
            amazonS3.deleteObject(new DeleteObjectRequest(bucketName, key));
        }catch (Exception e){
            throw new BaseException(S3ErrorCode.IO_EXCEPTION);
        }
    }

    private String getKeyFromMp3Address(String mp3Address) {
        try {
            URL url = new URL(mp3Address);
            String decodingKey = URLDecoder.decode(url.getPath(), StandardCharsets.UTF_8);
            return decodingKey.substring(1); // 맨 앞의 '/' 제거
        } catch (MalformedURLException e) {
            throw new BaseException(S3ErrorCode.IO_EXCEPTION);
        }
    }
}
