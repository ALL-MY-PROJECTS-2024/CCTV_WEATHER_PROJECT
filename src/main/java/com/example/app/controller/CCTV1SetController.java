package com.example.app.controller;


import com.example.app.domain.entity.CCTV1;
import com.example.app.domain.repository.CCTV1Respository;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@Slf4j
public class CCTV1SetController {


    @Autowired
    private CCTV1Respository cctv1Respository;

    @GetMapping("/set/cctv1")
    public void set1(Model model){
        log.info("GET /set/cctv1");

        List<CCTV1> cctv1List   =cctv1Respository.findAll();
        cctv1List.forEach(el->System.out.println(el));

        model.addAttribute("cctv1List",cctv1List);
    }

    // CCTV 정보를 업데이트하는 API
    @GetMapping("/update/cctv")
    @ResponseBody
    public String updateCCTV(@RequestParam("id")Long id, @RequestParam("lat")double lat , @RequestParam("lon")double lon ) {
        log.info("Updating CCTV: " + id + " "+ lat + " " + lon);
        CCTV1 cctv = cctv1Respository.findById(id).orElseThrow();
        cctv.setLat(lat);
        cctv.setLon(lon);
        cctv1Respository.save(cctv);
        return "CCTV information updated successfully!";
    }




    @GetMapping("/position")
    public @ResponseBody void initpos(@RequestParam("address") String address){
    //https://api.ncloud-docs.com/docs/ai-naver-mapsgeocoding-geocode
//        네이버
        //Client ID
        //(X-NCP-APIGW-API-KEY-ID)  : s0femem0gn

        //Client Secret
        //(X-NCP-APIGW-API-KEY) : SUwuByccz8qwPaALU1SaPKTp9q73Qkg4Xq0yFF7U

        String  clientId = "s0femem0gn";
        String secretKey = "SUwuByccz8qwPaALU1SaPKTp9q73Qkg4Xq0yFF7U";

        String url = UriComponentsBuilder.fromHttpUrl("https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode")
                .queryParam("query", address)
                .toUriString();

        // 헤더에 Client ID와 Client Secret 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-ncp-apigw-api-key-id", clientId);
        headers.set("x-ncp-apigw-api-key", secretKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // API 요청
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        System.out.println(response.getBody());

    }


}
