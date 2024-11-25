package com.khu.yaong.domain.data.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.khu.yaong.global.common.exception.BaseException;
import com.khu.yaong.global.common.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3FileService {

    private final AmazonS3 s3Client;

    @Value("${spring.aws.s3.bucket}")
    private String bucket;


    /**
     * uploadFile : S3에 파일을 업로드하는 함수
     * @param multipartFile : 실제 파일
     */
    public void uploadFile(MultipartFile multipartFile) {

        String filename = makeFileName(multipartFile);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());

        try {
            InputStream inputStream = multipartFile.getInputStream();
            s3Client.putObject(new PutObjectRequest(bucket, filename, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch(IOException e) {
            throw new BaseException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private String makeFileName(MultipartFile multipartFile) {
        String origin = multipartFile.getOriginalFilename();
        if (origin == null) {
            throw new BaseException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        String extension = origin.substring(origin.lastIndexOf(".") + 1);
        return UUID.randomUUID().toString() + extension;
    }

    /**
     * getFile : S3에서 파일을 불러와 로컬에 저장하는 함수
     * @param filename : 파일 이름(경로)
     */
    public String getFile(String filename) {
        try {

            // S3 Object 가져오기
            S3Object object = s3Client.getObject(bucket, filename);

            // Object에서 파일 내용 추출
            S3ObjectInputStream file = object.getObjectContent();

            // File Read & Write
            FileOutputStream fileOutputStream = new FileOutputStream(filename);
            byte[] buffer = new byte[1024];
            int readLength = 0;
            while ((readLength = file.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, readLength);
            }

            file.close();
            fileOutputStream.close();
            return filename;

        } catch(Exception e) {
            throw new BaseException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * deleteFile : S3에서 객체를 삭제하는 함수
     * @param filename : 파일 이름(경로)
     */
    public void deleteFile(String filename) {
        try {
            s3Client.deleteObject(bucket, filename);
        } catch (AmazonServiceException e) {
            throw new BaseException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * copyFile : 같은 bucket 내에서 파일을 복사하는 함수
     * @param src : source file
     * @param dst : destination file
     */
    public void copyFile(String src, String dst) {
        try {
            s3Client.copyObject(bucket, src, bucket, dst);
        } catch(AmazonServiceException e) {
            throw new BaseException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

}
