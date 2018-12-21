package com.project.MyProject.controller;

import com.project.MyProject.dbo.UserDbo;
import com.project.MyProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String main() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(UserDbo userDbo, Map<String, Object> model){
        if(!userService.addUser(userDbo)){
            model.put("info", "ERROR: User exists");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code){
        boolean isActivated = userService.activateUser(code);

        if(isActivated){
            model.addAttribute("info", "User successfully activated");
        } else {
            model.addAttribute("info", "Activation code is not found");
        }

        return "login";
    }
}