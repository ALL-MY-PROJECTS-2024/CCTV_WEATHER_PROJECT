//package com.example.app.restController;
//
//import io.github.bonigarcia.wdm.WebDriverManager;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.List;
//
//public class AllJsController {
//
//    @GetMapping("/getAllResources")
//    public List<String> getAllResources(@RequestParam String url) {
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
//        List<String> resources = new ArrayList<>();
//
//        try {
//            // 지정된 URL로 이동
//            driver.get(url);
//
//            // CSS 파일 찾기
//            List<WebElement> cssLinks = driver.findElements(By.tagName("link"));
//            for (WebElement link : cssLinks) {
//                String href = link.getAttribute("href");
//                if (href != null && href.endsWith(".css")) {
//                    String cssContent = downloadResource(href);
//                    if (cssContent != null) {
//                        resources.add("CSS File: " + href + "\n" + cssContent);
//                    }
//                }
//            }
//
//            // JavaScript 파일 찾기
//            List<WebElement> scriptLinks = driver.findElements(By.tagName("script"));
//            for (WebElement script : scriptLinks) {
//                String src = script.getAttribute("src");
//                if (src != null && src.endsWith(".js")) {
//                    String jsContent = downloadResource(src);
//                    if (jsContent != null) {
//                        resources.add("JavaScript File: " + src + "\n" + jsContent);
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
//        return resources;  // CSS 및 JavaScript 파일 내용 리스트 반환
//    }
//
//    // 리소스(CSS/JavaScript 파일)을 다운로드하는 메서드
//    private String downloadResource(String resourceUrl) {
//        StringBuilder resourceContent = new StringBuilder();
//
//        try {
//            URL url = new URL(resourceUrl);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//
//            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
//            String inputLine;
//            while ((inputLine = in.readLine()) != null) {
//                resourceContent.append(inputLine).append("\n");
//            }
//            in.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//
//        return resourceContent.toString();
//    }
//
//}
