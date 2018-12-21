package com.project.MyProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class GreetingController {

    @GetMapping("/greeting")
    public String getName(
            @RequestParam(name = "name", required = false, defaultValue = "unnamed") String name,
            Map<String, Object> model
    ) {
        model.put("name", name);
        return "greeting";
    }
}