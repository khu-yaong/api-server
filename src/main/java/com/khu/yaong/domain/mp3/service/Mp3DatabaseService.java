package com.khu.yaong.domain.mp3.service;

import com.khu.yaong.domain.mp3.domain.Mp3File;
import com.khu.yaong.domain.mp3.repository.Mp3FileRepository;
import com.khu.yaong.global.s3.S3Mp3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Mp3DatabaseService {

    private final S3Mp3Service mp3Service;
    private final Mp3FileRepository mp3FileRepository;

    // S3에서 가져온 MP3 URL 리스트를 데이터베이스에 저장
    public void saveS3FilesToDatabase() {
        List<S3Mp3Service.Mp3FileDTO>files = mp3Service.listFilesInBucket();

        for (S3Mp3Service.Mp3FileDTO file: files){
            if (!mp3FileRepository.existsByUrl(file.getMp3Url())) {
                // 파일 이름 파싱
                String[] parts = file.getFileName().split("_");
                if (parts.length <3) {
                    throw new IllegalArgumentException("Invalid file name format: " + file.getFileName());
                }
                String teamName = parts[0];
                String category = parts[1];
                String name = parts[2];
                Mp3File mp3File = new Mp3File(file.getMp3Url(), file.getFileName(), teamName, category, name);
                mp3FileRepository.save(mp3File);
            }
        }
    }
    // 모든 MP3 파일 데이터 조회
    public List<Mp3File> getAllMp3Files() {
        return mp3FileRepository.findAll();
    }
}
