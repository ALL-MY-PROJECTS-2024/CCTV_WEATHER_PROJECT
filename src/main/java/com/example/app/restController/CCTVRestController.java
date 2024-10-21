package com.example.app.restController;


import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class CCTVRestController {

    @GetMapping("/cctv2")
    public String cctv2() {
        // WebDriverManager를 통해 ChromeDriver 설정
        WebDriverManager.chromedriver().setup();

        // Headless 모드로 ChromeDriver 설정
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");  // UI 없이 실행
//        options.addArguments("--no-sandbox");
//        options.addArguments("--disable-dev-shm-usage");

        // ChromeDriver 인스턴스 생성
        WebDriver driver = new ChromeDriver(options);

        try {
            // 웹 페이지 열기
            driver.get("https://safecity.busan.go.kr/#/");



            // 페이지 로드 대기
            Thread.sleep(3000);  // 페이지 로딩을 위해 잠시 대기 (필요 시 명시적으로 대기)

            // 페이지 로드 후 DOM 가져오기 (JavaScript가 실행된 후의 상태)
            // String pageSource = driver.getPageSource();
            // 출력 또는 다른 처리 수행
            //System.out.println(pageSource);

            //팝업1 닫기 버튼 클릭
            WebElement Popupbutton = driver.findElement(By.cssSelector(".popupClose"));
            Popupbutton.click();
            Thread.sleep(500);

            //팝업2 닫기 버튼 클릭
            WebElement Popupbutton2 = driver.findElement(By.cssSelector(".center"));
            Popupbutton2.click();
            Thread.sleep(500);

            //재난감시 CCTV 클릭
            WebElement 재난감시CCTV버튼 = driver.findElement(By.cssSelector(".map_dep1_ul>li:nth-child(1)"));
            재난감시CCTV버튼.click();
            Thread.sleep(100);

            //교통감지끄기
            List<WebElement> CCTVFrameEls = driver.findElements(By.cssSelector(".induationCheckBox ul li .selectOption.active"));
            for(WebElement el : CCTVFrameEls)
                el.click();
            Thread.sleep(100);

            //재난감지CCTV 켜기
            WebElement CCTVFloodingEl = driver.findElement(By.cssSelector(".induationCheckBox ul li:nth-child(1) .selectOption"));
            if(CCTVFloodingEl.getText().contains("OFF"))
                CCTVFloodingEl.click();



            // 원하는 img 태그만 찾기 (XPath 또는 CSS Selector 사용)
            List<WebElement> 재난CCTVS = driver.findElements(By.cssSelector(".leaflet-pane.leaflet-marker-pane img"));
            System.out.println("재난CCTVS SIZE : " + 재난CCTVS.size());
            for(WebElement el : 재난CCTVS){
                System.out.println(el);
            }

            재난CCTVS.get(0).click();



            return "Page loaded successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred during Selenium test.";
        } finally {
            // 브라우저 닫기
            //driver.quit();
        }
    }


    @GetMapping("/cctv1")
    public void handleCctvRequest() throws Exception {
        try {
            // JSoup을 사용해 웹 페이지의 모든 DOM 요소를 가져오기
            String url = "https://safecity.busan.go.kr/#/";  // 대상 웹 페이지 URL
            Document doc = Jsoup.connect(url).get();  // 웹 페이지 파싱

            // 전체 DOM 가져오기
            Elements allElements = doc.getAllElements();
            System.out.println(allElements);
//            // 결과를 StringBuilder에 저장
//            StringBuilder sb = new StringBuilder();
//            sb.append("Total elements found: ").append(allElements.size()).append("\n\n");
//
//            // 모든 DOM 요소의 태그 이름 출력
//            for (Element element : allElements) {
//                System.out.println(element);
//                sb.append("Tag: ").append(element.tagName()).append("\n");
//            }

//            return sb.toString();  // 결과 반환
        } catch (IOException e) {
            e.printStackTrace();
//            return "Error occurred while fetching the DOM elements.";
        }
    }




    // 부산광역시_CCTV 설치 현황정보
    @GetMapping("/cctv")
    public List<Item> cctv(){
        log.info("GET /cctv..");

        //[임시] URL 요청
//        String url = "https://apis.data.go.kr/6260000/BusanITSCCTV/CCTVList";
//        String pageNo = "1";
//        String numOfRows = "10";
//        String serviceKey = "xYZ80mMcU8S57mCCY/q8sRsk7o7G8NtnfnK7mVEuVxdtozrl0skuhvNf34epviHrru/jiRQ41FokE9H4lK0Hhg==";
//
//        String total = url
//                + "?serviceKey=" + serviceKey
//                + "&pageNo=" + pageNo
//                + "&numOfRows=" + numOfRows;
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        ResponseEntity<Root> response = restTemplate.getForEntity(total, Root.class);
//        System.out.println(response.getBody());
        // 고정된 CCTV 데이터 100개


        List<Item> cctvList = new ArrayList<>();
        cctvList.add(new Item("충무교차로", "https://its-stream3.busan.go.kr:8443/rtplive/cctv_1.stream/playlist.m3u8", 129.024297, 35.096521));
        cctvList.add(new Item("남포동", "https://its-stream3.busan.go.kr:8443/rtplive/cctv_2.stream/playlist.m3u8", 129.029273, 35.097858));
        cctvList.add(new Item("옛시청", "https://its-stream3.busan.go.kr:8443/rtplive/cctv_3.stream/playlist.m3u8", 129.035343, 35.097879));
        cctvList.add(new Item("부산우체국", "https://its-stream3.busan.go.kr:8443/rtplive/cctv_4.stream/playlist.m3u8", 129.036658, 35.103092));
        cctvList.add(new Item("영주사거리", "https://its-stream3.busan.go.kr:8443/rtplive/cctv_5.stream/playlist.m3u8", 129.037914, 35.11186));
        cctvList.add(new Item("부산역", "https://its-stream3.busan.go.kr:8443/rtplive/cctv_6.stream/playlist.m3u8", 129.039353, 35.114967));
        cctvList.add(new Item("초량교차로", "https://its-stream3.busan.go.kr:8443/rtplive/cctv_7.stream/playlist.m3u8", 129.042203, 35.120005));
        cctvList.add(new Item("좌천삼거리", "https://its-stream3.busan.go.kr:8443/rtplive/cctv_8.stream/playlist.m3u8", 129.052554, 35.132734));
        cctvList.add(new Item("자유시장", "https://its-stream3.busan.go.kr:8443/rtplive/cctv_9.stream/playlist.m3u8", 129.059168, 35.141795));
        cctvList.add(new Item("범내골교차로", "https://its-stream3.busan.go.kr:8443/rtplive/cctv_10.stream/playlist.m3u8", 129.059051, 35.147345));
        // 계속해서 100개의 데이터를 추가합니다.
        // 실제 데이터에 맞게 아래의 형식을 따라 나머지 데이터를 채워주세요.
        cctvList.add(new Item("천우장", "https://its-stream3.busan.go.kr:8443/rtplive/cctv_11.stream/playlist.m3u8", 129.058965, 35.15453));
        cctvList.add(new Item("서면교차로", "https://its-stream3.busan.go.kr:8443/rtplive/cctv_12.stream/playlist.m3u8", 129.059124, 35.157704));
        cctvList.add(new Item("삼전교차로", "https://its-stream3.busan.go.kr:8443/rtplive/cctv_13.stream/playlist.m3u8", 129.063771, 35.163847));
        cctvList.add(new Item("양정교차로", "https://its-stream3.busan.go.kr:8443/rtplive/cctv_15.stream/playlist.m3u8", 129.070956, 35.172854));
        cctvList.add(new Item("부산지방경찰청", "https://its-stream3.busan.go.kr:8443/rtplive/cctv_16.stream/playlist.m3u8", 129.075663, 35.178605));
        cctvList.add(new Item("연산교차로", "https://its-stream3.busan.go.kr:8443/rtplive/cctv_17.stream/playlist.m3u8", 129.081753, 35.186064));
        cctvList.add(new Item("교대사거리", "https://its-stream3.busan.go.kr:8443/rtplive/cctv_18.stream/playlist.m3u8", 129.080232, 35.19575));
        cctvList.add(new Item("동래롯데", "https://its-stream3.busan.go.kr:8443/rtplive/cctv_19.stream/playlist.m3u8", 129.078823, 35.211747));
        cctvList.add(new Item("온천교사거리", "https://its-stream3.busan.go.kr:8443/rtplive/cctv_20.stream/playlist.m3u8", 129.084225, 35.218093));
        cctvList.add(new Item("부산대학교사거리", "https://its-stream3.busan.go.kr:8443/rtplive/cctv_21.stream/playlist.m3u8", 129.086384, 35.231319));
        cctvList.add(new Item("부곡사거리", "https://its-stream3.busan.go.kr:8443/rtplive/cctv_22.stream/playlist.m3u8", 129.091587, 35.230741));


        return cctvList;
    }
    @Data
    private static class Content{
        public int pageNo;
        public int numOfRows;
        public int totalCount;
        public ArrayList<Item> items;
    }
    @Data
    @AllArgsConstructor
    private static class Item{
        public String instlPos;
        public String hlsAddr;
        public double lot;
        public double lat;
    }
    @Data
    private static class Root{
        public String resultCode;
        public String resultMsg;
        public Content content;
    }


}
