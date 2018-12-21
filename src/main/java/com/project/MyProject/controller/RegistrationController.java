package com.project.MyProject.controller;

import com.project.MyProject.dbo.UserDbo;
import com.project.MyProject.dbo.UserRoleDbo;
import com.project.MyProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.CollectionTable;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String main() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(UserDbo userDbo, Map<String, Object> model){
        UserDbo dbUser = userRepository.findByUsername(userDbo.getUsername());
        if(dbUser != null){
            model.put("info", "ERROR: User exists");
            return "registration";
        }

        userDbo.setActive(true);
        userDbo.setRoles(Collections.singleton(UserRoleDbo.USER));
        userRepository.save(userDbo);

        return "redirect:/login";
    }
}