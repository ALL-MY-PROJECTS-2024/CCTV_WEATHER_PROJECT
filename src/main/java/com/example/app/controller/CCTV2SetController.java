package com.example.app.controller;

import com.example.app.domain.entity.CCTV1;
import com.example.app.domain.entity.CCTV2;
import com.example.app.domain.repository.CCTV1Respository;
import com.example.app.domain.repository.CCTV2Respository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
@Controller
@Slf4j
public class CCTV2SetController {
    @Autowired
    private CCTV2Respository cctv2Respository;
    @GetMapping("/set/cctv2")
    public void set1(Model model){
        log.info("GET /set/cctv2");

        List<CCTV2> cctv2List   = cctv2Respository.findAll();
        cctv2List.forEach(el->System.out.println(el));

        model.addAttribute("cctv2List",cctv2List);
    }
    // CCTV 정보를 업데이트하는 API
    @GetMapping("/update/cctv2")
    @ResponseBody
    public String updateCCTV(@RequestParam("id")Long id, @RequestParam("lat")double lat , @RequestParam("lon")double lon ) {
        log.info("Updating CCTV1: " + id + " "+ lat + " " + lon);
        CCTV2 cctv = cctv2Respository.findById(id).orElseThrow();
        cctv.setLat(lat);
        cctv.setLon(lon);
        cctv2Respository.save(cctv);
        return "CCTV information updated successfully!";
    }
}
