package com.project.MyProject.controller;

import com.project.MyProject.dbo.BandDbo;
import com.project.MyProject.repository.BandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.PublicKey;
import java.util.List;
import java.util.Map;

@Controller
public class BandController {
    @Autowired
    private BandRepository bandRepository;

    @GetMapping("/band")
    public String main(Map<String, Object> model) {
        Iterable<BandDbo> bandDbos = bandRepository.findAll();
        model.put("bands", bandDbos);
        return "band";
    }

    @PostMapping
    public String add(@RequestParam String bandName, @RequestParam String genre, Map<String, Object> model) {
        BandDbo bandDbo = new BandDbo(bandName, genre);
        bandRepository.save(bandDbo);
        Iterable<BandDbo> bandDbos = bandRepository.findAll();
        model.put("bands", bandDbos);
        return "band";
    }

    @GetMapping(path = "/band/all")
    public @ResponseBody
    Iterable<BandDbo> getAllBands() {
        return bandRepository.findAll();
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        Iterable<BandDbo> bandDbos;
        if(filter != null && !filter.isEmpty()) {
            bandDbos = bandRepository.findByGenre(filter);
        }else{
            bandDbos = bandRepository.findAll();
        }
        model.put("bands", bandDbos);
        return "band";
    }
}