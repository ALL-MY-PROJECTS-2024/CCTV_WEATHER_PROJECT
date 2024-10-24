package com.example.app.restController;


import com.example.app.domain.entity.CCTV1;
import com.example.app.domain.entity.CCTV2;
import com.example.app.domain.repository.CCTV1Respository;
import com.example.app.domain.repository.CCTV2Respository;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@RestController
@Slf4j
public class CCTV1RestController {



    @Autowired
    private CCTV1Respository cCTV1repository;




    List<WebElement> clusterOne = new ArrayList();


    int zoomLevel = 4 ;      //값이 올라갈수록 depth 반복

    @GetMapping("/cctv1")
    public List<WebElement> cctv2() {
        // WebDriverManager를 통해 ChromeDriver 설정
        WebDriverManager.chromedriver().setup();

        // Headless 모드로 ChromeDriver 설정
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");  // UI 없이 실행
//        options.addArguments("--no-sandbox");
//        options.addArguments("--disable-dev-shm-usage");

        // ChromeDriver 인스턴스 생성
        WebDriver driver = new ChromeDriver(options);
        options.setBinary("/bin/google-chrome-stable");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));


        try {
            // 웹 페이지 열기
            driver.get("https://safecity.busan.go.kr/#/");

            // 페이지 로드 대기
            Thread.sleep(3000);  // 페이지 로딩을 위해 잠시 대기 (필요 시 명시적으로 대기)

            //팝업닫기
            closedPopup(driver);

            //재난감시 CCTV 클릭
            turnOnCCTV1(driver);
            
            //ONE DEPTH TEST
            getOneDepthCCTVUrl(driver);

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // 브라우저 닫기
            //driver.quit();
        }
    }
    //--------------------------
    //팝업 닫기
    //--------------------------
    public void closedPopup(WebDriver driver) throws InterruptedException {
        //팝업1 닫기 버튼 클릭
        WebElement PopupcheckOne = driver.findElement(By.cssSelector(".todayClose input[type='checkbox']"));
        if(PopupcheckOne.isDisplayed())
            PopupcheckOne.click();

        WebElement Popupbutton = driver.findElement(By.cssSelector(".popupClose"));
        if(Popupbutton.isDisplayed()){
            Popupbutton.click();
            Thread.sleep(500);
        }

        WebElement PopupcheckTwo = driver.findElement(By.cssSelector(".todayGuideClose input[type='checkbox']"));
        if(PopupcheckTwo!=null)
            PopupcheckTwo.click();

        //팝업2 닫기 버튼 클릭
        WebElement Popupbutton2 = driver.findElement(By.cssSelector(".center"));
        if(Popupbutton2.isDisplayed()){
            Popupbutton2.click();
            Thread.sleep(500);
        }
    }

    //--------------------------
    //재난감지 CCTV2 켜기
    //--------------------------
    public void turnOnCCTV1(WebDriver driver) throws InterruptedException {
        WebElement 재난감시CCTV버튼 = driver.findElement(By.cssSelector(".map_dep1_ul>li:nth-child(1)"));
        재난감시CCTV버튼.click();
        Thread.sleep(100);

        //CCTV 끄기
        List<WebElement> CCTVFrameEls = driver.findElements(By.cssSelector(".induationCheckBox ul li .selectOption.active"));
        for(WebElement el : CCTVFrameEls)
            el.click();
        Thread.sleep(100);

        ////교통감지켜기
        WebElement CCTVFloodingEl = driver.findElement(By.cssSelector(".induationCheckBox ul li:nth-child(2) .selectOption"));
        if(CCTVFloodingEl.getText().contains("OFF"))
            CCTVFloodingEl.click();





    }

    //--------------------------
    //줌 out
    //--------------------------
    public void zoomInit(WebElement zoominEl) throws InterruptedException {
        for(int i=0;i<9;i++){
            zoominEl.click();
            Thread.sleep(500);
        }

    }
    public void zoomInit(WebElement zoominEl,int count) throws InterruptedException {
        for(int i=0;i<count;i++){
            zoominEl.click();Thread.sleep(500);
        }

    }

    //ONE DEPTH
    public void getOneDepthCCTVUrl(WebDriver driver) throws InterruptedException {
       
        //최상위 이동
        WebElement zoominEl = driver.findElement(By.cssSelector(".leaflet-control-zoom-out"));
        zoomInit(zoominEl);

        //최상위 클러스터 클릭
        WebElement 재난CCTV = driver.findElement(By.cssSelector(".leaflet-marker-icon.marker-cluster.marker-cluster-large.leaflet-zoom-animated.leaflet-interactive"));
        WebElement totalValue = driver.findElement(By.cssSelector(".leaflet-marker-icon.marker-cluster.marker-cluster-large.leaflet-zoom-animated.leaflet-interactive div span"));
        System.out.println("TOTAL :" + totalValue.getText());
        재난CCTV.click();
        Thread.sleep(1000);

        //최상위  클릭 이후 카메라 수집
        List<WebElement> cam  = driver.findElements(By.cssSelector(".leaflet-pane.leaflet-marker-pane>img"));
        System.out.println("CAM's size : " + cam.size());
        saveCCTVUrl(cam,driver);
        Thread.sleep(1000);


        //OneDepth 클러스터 수집
        List<WebElement> ALLClusteredMarkers = driver.findElements(By.cssSelector(".leaflet-pane.leaflet-marker-pane>div"));
        System.out.println("One DEPTH TOTAL size : " + ALLClusteredMarkers.size());

        List<WebElement> oneDepthList = new ArrayList<>(ALLClusteredMarkers);


        //반복해서 OneDepth 기준 카메라 긁어옴
        for(int i=0;i<oneDepthList.size();i++){

            //페이지 새로고침
            driver.navigate().refresh();

            //CCTV2 켜기
            turnOnCCTV1(driver);


            //최상위 이동
            zoominEl = driver.findElement(By.cssSelector(".leaflet-control-zoom-out"));
            zoomInit(zoominEl);
            Thread.sleep(1000);

            //최상위 클러스터 클릭
            재난CCTV = driver.findElement(By.cssSelector(".leaflet-marker-icon.marker-cluster.marker-cluster-large.leaflet-zoom-animated.leaflet-interactive"));
            재난CCTV.click();
            Thread.sleep(1000);

            //ONEDEPTH_GET
            List<WebElement> one = driver.findElements(By.cssSelector(".leaflet-pane.leaflet-marker-pane>div"));
            WebElement e = one.get(i);
            WebElement valEl =  e.findElement((By.cssSelector("div span")));
            System.out.println("i : "  + i + "  VAL : "+  valEl.getText());
            e.click();

            Thread.sleep(2000);

            //카메라 GET
            cam  = driver.findElements(By.cssSelector(".leaflet-pane.leaflet-marker-pane>img"));
            System.out.println("CAM's size : " + cam.size());
            saveCCTVUrl(cam,driver);
            Thread.sleep(1000);
        }

    }
    //TWO DEPTH
    public void getTwoDepthCCTVUrl(){

    }
    //THREE DEPTH
    public void getThreeDepthCCTVUrl(){

    }
    //FOUR DEPTH
    public void getFourDepthCCTVUrl(){

    }

    //--------------------------
    //CCTV URL 받기
    //--------------------------
    public void saveCCTVUrl(List<WebElement> list ,WebDriver driver)  {

            int i = 1;
            for (WebElement e : list) {
                try {
                    System.out.print("반복 횟수 : " + i + " ");
                    e.click();
                    System.out.println(e);
                    Thread.sleep(1000);

                    // 새로 열린 모든 창 핸들 가져오기
                    Set<String> allWindowHandles = driver.getWindowHandles();

                    // 현재 창의 핸들을 저장
                    String mainWindowHandle = driver.getWindowHandle();

                    // 새창 탐색
                    for (String windowHandle : allWindowHandles) {
                        if (!windowHandle.equals(mainWindowHandle)) {
                            // 새창으로 전환
                            driver.switchTo().window(windowHandle);

                            // 새창의 URL 확인
                            String popupURL = driver.getCurrentUrl();
                            System.out.println("팝업 창 URL: " + popupURL);

                            //DB저장
                            if (!cCTV1repository.existsByHlsAddr(popupURL)) {
                                CCTV1 cctv1 = new CCTV1();
                                cctv1.setCategory("교통");
                                cctv1.setHlsAddr(popupURL);
                                cctv1.setLastUpdateAt(LocalDateTime.now());
                                cCTV1repository.save(cctv1);
                            }
                            // 팝업창 닫기
                            driver.close();

                            // 메인 창으로 다시 전환
                            driver.switchTo().window(mainWindowHandle);
                        }
                    }
                    i++;

                }catch(Exception e1){
                    System.out.print("_!_");
                }
            }
            System.out.println();


    }



    


}
