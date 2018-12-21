package com.project.MyProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CabinetController {

    @GetMapping("/cabinet")
    public String main() {
        return "cabinet";
    }
}
