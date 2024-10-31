package com.example.app.restController;


import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.*;

@RestController
@Slf4j
@RequestMapping("/bSearch")
public class 깊이우선탐색 {

    Map<WebElement, List<WebElement>> makerGraph = new HashMap<>();

    @GetMapping("/cctv1")
    public void cctv1(){
        log.info("GET /bSearch/cctv1...");
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

        try{
            // 웹 페이지 열기
            driver.get("https://safecity.busan.go.kr/#/");
            // 페이지 로드 대기
            Thread.sleep(3000);  // 페이지 로딩을 위해 잠시 대기 (필요 시 명시적으로 대기)
            //팝업닫기
            closedPopup(driver);
            //재난감시 CCTV 클릭
            turnOnCCTV1(driver);

            //최상위 이동
            WebElement zoominEl = driver.findElement(By.cssSelector(".leaflet-control-zoom-out"));
            zoomInit(zoominEl);

            //최상위 클러스터 클릭
            WebElement 재난CCTV = driver.findElement(By.cssSelector(".leaflet-marker-icon.marker-cluster.marker-cluster-large.leaflet-zoom-animated.leaflet-interactive"));
            WebElement totalValue = driver.findElement(By.cssSelector(".leaflet-marker-icon.marker-cluster.marker-cluster-large.leaflet-zoom-animated.leaflet-interactive div span"));
            System.out.println("TOTAL :" + totalValue.getText());
            재난CCTV.click();
            Thread.sleep(1000);

            if(재난CCTV!=null)
                searchRecursive(재난CCTV,driver,0,0);

        }catch(Exception e){

            System.out.println("_!_");
        }


    }

    //--------------------------------------------------------------------
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //--------------------------------------------------------------------
    public void searchRecursive( WebElement root, WebDriver driver,int depth,int idx){
        //idx : 서브 클러스터의 몇번째를 찾기 위한용도

            //ex 17개
            List<WebElement> clusterTagList = driver.findElements(By.cssSelector(".leaflet-pane.leaflet-marker-pane>div"));
            if (clusterTagList != null) {

                for (int i = 0; i < clusterTagList.size(); i++) {
                    try {
                        WebElement e = clusterTagList.get(i);
                        System.out.println("depth level : " + depth + " clusterTag : " + e);
                        e.click();
                        idx = i;
                        searchRecursive(e, driver, depth++, 0);
                    } catch (Exception e) {
                        System.out.println("예외발생!.." + e);
                    }

                }
            }else {
                System.out.println("더이상 내려갈 클러스터가 없다...");
            }

    }


    public void makerAddEdge(WebElement node1, WebElement node2) {
        makerGraph.computeIfAbsent(node1, k -> new ArrayList<>()).add(node2);
        makerGraph.computeIfAbsent(node2, k -> new ArrayList<>()).add(node1);
    }

    public void makerDfs(WebElement startNode) {
        Set<WebElement> visited = new HashSet<>();  // 방문한 노드를 기록하는 집합 (Object 타입)
        markerDfsRecursive(startNode, visited);       // 재귀적으로 DFS 호출
    }
    // 재귀 메서드 - DFS 순회
    private void markerDfsRecursive(WebElement node, Set<WebElement> visited) {
        if (visited.contains(node)) return;     // 이미 방문한 노드는 재방문하지 않음

        visited.add(node);                      // 현재 노드를 방문 처리
        System.out.println("Visited node: " + node); // 방문한 노드 출력

        // 현재 노드에 연결된 이웃 노드를 순회
        for (WebElement neighbor : makerGraph.getOrDefault(node, new ArrayList<>())) {
            if (!visited.contains(neighbor)) {  // 방문하지 않은 이웃 노드만 탐색
                markerDfsRecursive(neighbor, visited); // 이웃 노드로 이동
            }
        }
    }




    Map<Object, List<Object>> graph = new HashMap<>();

    @GetMapping("/test")
    public void search1(){
        log.info("/GET /bSearch/cctv1...");
        addEdge("A", "B");
        addEdge("A", "C");
        addEdge("B", "D");
        addEdge("C", "E");
        addEdge("D", "E");
        addEdge("E", "F");

        dfs("A");
    }

    public void addEdge(Object node1, Object node2) {
        graph.computeIfAbsent(node1, k -> new ArrayList<>()).add(node2);
        graph.computeIfAbsent(node2, k -> new ArrayList<>()).add(node1);
    }

    // DFS를 통한 단방향 순회
    public void dfs(Object startNode) {
        Set<Object> visited = new HashSet<>();  // 방문한 노드를 기록하는 집합 (Object 타입)
        dfsRecursive(startNode, visited);       // 재귀적으로 DFS 호출
    }
    // 재귀 메서드 - DFS 순회
    private void dfsRecursive(Object node, Set<Object> visited) {
        if (visited.contains(node)) return;     // 이미 방문한 노드는 재방문하지 않음

        visited.add(node);                      // 현재 노드를 방문 처리
        System.out.println("Visited node: " + node); // 방문한 노드 출력

        // 현재 노드에 연결된 이웃 노드를 순회
        for (Object neighbor : graph.getOrDefault(node, new ArrayList<>())) {
            if (!visited.contains(neighbor)) {  // 방문하지 않은 이웃 노드만 탐색
                dfsRecursive(neighbor, visited); // 이웃 노드로 이동
            }
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
}
