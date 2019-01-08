package com.project.MyProject.service;

import com.project.MyProject.converter.UserConverter;
import com.project.MyProject.dto.user.ShowUserDto;
import com.project.MyProject.dto.user.UserDto;
import com.project.MyProject.dto.tabs.TabsDto;
import com.project.MyProject.entity.User;
import com.project.MyProject.enumeration.UserRole;
import com.project.MyProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Autowired
    public UserService(final UserRepository userRepository, final UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    public void registerUser(final UserDto userDto) {
        userRepository.save(userConverter.convertToDbo(userDto));
    }

    public List<ShowUserDto> getUsersList() {
        return userRepository.findAll().stream().map(userConverter::convertDboToShowUserDto).collect(Collectors.toList());
    }

    public User getUser(String name) {
        return userRepository.findByUsername(name);
    }

    public Boolean protect(final String login){
        return userRepository.findByUsername(login).getRole().equals(UserRole.ADMIN);
    }


}
