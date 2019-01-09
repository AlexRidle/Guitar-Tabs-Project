package com.project.MyProject.converter;

import com.project.MyProject.dto.UserDto;
import com.project.MyProject.entity.User;
import com.project.MyProject.repository.UserRepository;
import com.project.MyProject.service.MockData;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserConverterTest {

    @InjectMocks
    private UserConverter userConverter;

    @Spy
    private TabsConverter converter;

    @Mock
    private UserRepository userRepository;

    @Before
    public void init() {
        converter = new TabsConverter(userRepository);
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void convertToDto() {
        final User user = MockData.user();
        when(userRepository.findByUsername(Mockito.anyString())).thenReturn(any(User.class));
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
