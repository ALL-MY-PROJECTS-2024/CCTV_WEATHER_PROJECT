package com.example.app.restController;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class CCTVRestController {

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
