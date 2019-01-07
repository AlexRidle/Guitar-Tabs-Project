package com.project.MyProject.converter;

import com.project.MyProject.repository.UserRepository;
import com.project.MyProject.service.MockData;
import com.project.MyProject.dto.UserDto;
import com.project.MyProject.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class UserConverterTest {

    @Autowired
    private final UserRepository userRepository;

    private final UserConverter userConverter;

    @Autowired
    public UserConverterTest(UserRepository userRepository) {
        this.userRepository = userRepository;
        userConverter = new UserConverter(new TabsConverter(this.userRepository));
    }

    @Test
    public void convertToDto() {
        final User user = MockData.user();
        final UserDto userDto = userConverter.convertToDto(user);
        assertEquals(user.getUsername(), userDto.getUsername());
        assertEquals(user.getEmail(), userDto.getEmail());
    }

    @Test
    public void convertToDbo() {
        final UserDto userDto = MockData.userDto();
        final User user = userConverter.convertToDbo(userDto);
        assertEquals(userDto.getUsername(), user.getUsername());
        assertEquals(userDto.getEmail(), user.getEmail());
    }

}
