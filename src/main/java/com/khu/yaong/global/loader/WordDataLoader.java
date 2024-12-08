package com.khu.yaong.global.loader;

import com.khu.yaong.domain.data.domain.BaseballWord;
import com.khu.yaong.domain.data.repository.BaseballWordRepository;
import com.khu.yaong.global.common.exception.BaseException;
import com.khu.yaong.global.common.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;

@Component
@RequiredArgsConstructor
public class WordDataLoader implements CommandLineRunner {

    private final BaseballWordRepository baseballWordRepository;

    @Override
    public void run(String... args) throws Exception {
        // 야구 용어 데이터가 db에 저장되어 있지 않으면 csv 파일에서 로드
        String path = "data/word_raw_data.csv";
        if (baseballWordRepository.count() == 0) {
            saveWordData(path);
        }
    }

    private void saveWordData(String filePath) {

        try (InputStreamReader reader = new InputStreamReader(new ClassPathResource(filePath).getInputStream())) {
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withTrim());

            for (final CSVRecord csvRecord : csvParser) {
                BaseballWord baseballWord = BaseballWord.builder()
                        .word(csvRecord.get("word"))
                        .description(csvRecord.get("description"))
                        .build();
                baseballWordRepository.save(baseballWord);
            }

        } catch (IOException e) {
            throw new BaseException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
