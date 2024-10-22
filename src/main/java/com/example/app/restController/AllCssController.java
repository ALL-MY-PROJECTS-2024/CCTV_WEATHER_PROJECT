//package com.example.app.restController;
//
//import io.github.bonigarcia.wdm.WebDriverManager;
//import lombok.extern.slf4j.Slf4j;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.List;
//
//
//@RestController
//@Slf4j
//public class AllCssController {
//
//
//    @GetMapping("/getAllCss")
//    public List<String> getAllCss(@RequestParam("url") String url) {
//        // WebDriverManager를 통해 ChromeDriver 설정
//        WebDriverManager.chromedriver().setup();
//
//        // ChromeOptions 설정
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");  // UI 없이 실행
//        options.addArguments("--no-sandbox");
//        options.addArguments("--disable-dev-shm-usage");
//
//        // WebDriver 인스턴스 생성
//        WebDriver driver = new ChromeDriver(options);
//
//        List<String> cssContents = new ArrayList<>();
//
//        try {
//            // 지정된 URL로 이동
//            driver.get(url);
//
//            // CSS 파일을 찾기 위해 <link> 태그 검색
//            List<WebElement> cssLinks = driver.findElements(By.tagName("link"));
//
//            for (WebElement link : cssLinks) {
//                String href = link.getAttribute("href");
//
//                // CSS 파일 경로인지 확인
//                if (href != null && href.endsWith(".css")) {
//                    // CSS 파일 내용 다운로드
//                    String cssContent = downloadCss(href);
//                    if (cssContent != null) {
//                        cssContents.add(cssContent);
//                    }
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            // WebDriver 종료
//            driver.quit();
//        }
//
//        return cssContents;  // CSS 파일 내용 리스트 반환
//    }
//
//
//
//    // CSS 파일을 다운로드하는 메서드
//    private String downloadCss(String cssUrl) {
//        StringBuilder cssContent = new StringBuilder();
//
//        try {
//            URL url = new URL(cssUrl);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//
//            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
//            String inputLine;
//            while ((inputLine = in.readLine()) != null) {
//                cssContent.append(inputLine).append("\n");
//            }
//            in.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//
//        return cssContent.toString();
//    }
//
//
//}
