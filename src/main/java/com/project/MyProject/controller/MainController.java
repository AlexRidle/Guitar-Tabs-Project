package com.project.MyProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class MainController {

    @GetMapping
    public String main(Map<String, Object> model){
        model.put("some", "Main page");
        return "home";
    }
}
