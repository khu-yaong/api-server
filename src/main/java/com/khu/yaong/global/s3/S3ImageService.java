package com.khu.yaong.global.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.khu.yaong.global.common.exception.BaseException;
import com.khu.yaong.global.common.response.s3.S3ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3ImageService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public String uploadImage(String dir, MultipartFile image) {
        // image가 비어있으면 오류
        if (image.isEmpty() || Objects.isNull(image.getOriginalFilename())) {
            throw new BaseException(S3ErrorCode.FILE_IS_NULL);
        }
        return uploadImageWithPath(dir, image);
    }

    private String uploadImageWithPath(String dir, MultipartFile image) {
        // image가 비어있으면 오류
        if (image.isEmpty() || Objects.isNull(image.getOriginalFilename())) {
            throw new BaseException(S3ErrorCode.FILE_IS_NULL);
        }
        String originalFilename = image.getOriginalFilename(); // 원본 파일명
        String extension = validateImageFileExtention(originalFilename);

        try {
            String s3FileName = dir + UUID.randomUUID().toString().substring(0, 10) + originalFilename; //변경된 파일 명
            InputStream is = image.getInputStream();
            uploadStreamToS3(is, extension, s3FileName);
            return amazonS3.getUrl(bucketName, s3FileName).toString();
        } catch (IOException e) {
            throw new BaseException(S3ErrorCode.IO_EXCEPTION);
        }
    }

    private static String validateImageFileExtention(String filename) {
        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex == -1) {
            throw new BaseException(S3ErrorCode.BAD_FILE_EXTENSION);
        }

        String extension = filename.substring(lastDotIndex + 1).toLowerCase();
        List<String> allowedExtentionList = Arrays.asList("jpg", "jpeg", "png", "gif");

        if (!allowedExtentionList.contains(extension)) {
            throw new BaseException(S3ErrorCode.BAD_FILE_EXTENSION);
        }
        return extension;
    }

    private void uploadStreamToS3(InputStream is, String extension, String s3FileName) {
        try {
            byte[] bytes = IOUtils.toByteArray(is);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image/" + extension);
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
        } catch (IOException e) {
            throw new BaseException(S3ErrorCode.IO_EXCEPTION);
        }
    }

    public void deleteImageFromS3(String imageAddress) {
        String key = getKeyFromImageAddress(imageAddress);
        try{
            amazonS3.deleteObject(new DeleteObjectRequest(bucketName, key));
        }catch (Exception e){
            throw new BaseException(S3ErrorCode.IO_EXCEPTION);
        }
    }

    private String getKeyFromImageAddress(String imageAddress) {
        try {
            URL url = new URL(imageAddress);
            String decodingKey = URLDecoder.decode(url.getPath(), StandardCharsets.UTF_8);
            return decodingKey.substring(1); // 맨 앞의 '/' 제거
        } catch (MalformedURLException e) {
            throw new BaseException(S3ErrorCode.IO_EXCEPTION);
        }
    }
}
