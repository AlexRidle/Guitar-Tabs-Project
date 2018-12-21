package com.project.MyProject.service;

import com.project.MyProject.dbo.UserDbo;
import com.project.MyProject.dbo.UserRoleDbo;
import com.project.MyProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailSenderService mailSenderService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public boolean addUser(UserDbo userDbo){
        UserDbo dbUser = userRepository.findByUsername(userDbo.getUsername());
        if (dbUser != null) return false;

        userDbo.setActive(false);
        userDbo.setRoles(Collections.singleton(UserRoleDbo.USER));
        userDbo.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(userDbo);

        if(!StringUtils.isEmpty(userDbo.getEmail())){
            String link = "http://localhost:8080/activate/";
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to our band site. \n" +
                            "To complete registration you need to click this link: \n" +
                            link + "%s", userDbo.getUsername(), userDbo.getActivationCode());

            mailSenderService.send(userDbo.getEmail(), "Activation code", message);
        }

        return true;
    }

    public boolean activateUser(String code) {
        UserDbo userDbo = userRepository.findByActivationCode(code);

        if (userDbo == null) {
            return false;
        }

        userDbo.setActivationCode(null);
        userDbo.setActive(true);

        userRepository.save(userDbo);

        return true;
    }
}
