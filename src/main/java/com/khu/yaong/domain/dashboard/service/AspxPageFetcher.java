package com.khu.yaong.domain.dashboard.service;

import com.khu.yaong.domain.dashboard.exception.CrawlingErrorCode;
import com.khu.yaong.domain.dashboard.exception.CrawlingException;
import com.khu.yaong.global.common.exception.BaseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

@Service
public class AspxPageFetcher {

    public String fetchPageContent(String url) {
        // ChromeOptions 설정
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        WebDriver driver = new ChromeDriver(options);
        String htmlContent = null;

        try {
            driver.get(url);
            // 데이터가 렌더링되길 대기
            Thread.sleep(5000);

            WebElement body = driver.findElement(By.tagName("body"));
            htmlContent = body.getAttribute("outerHTML");

        } catch (InterruptedException e) {
            throw new CrawlingException(CrawlingErrorCode.FETCH_FAILED);

        }
        finally {
            // WebDriver 닫기
            driver.quit();
        }
        if (htmlContent == null || htmlContent.isEmpty()) {
            throw new CrawlingException(CrawlingErrorCode.FETCH_FAILED);
        }
        return htmlContent;
    }
}
