package com.project.MyProject.converter;

import com.project.MyProject.dto.UserDto;
import com.project.MyProject.dto.tabs.TabsDto;
import com.project.MyProject.entity.Tabs;
import com.project.MyProject.entity.User;
import com.project.MyProject.enumeration.UserRole;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserConverter implements DtoEntityConverter<UserDto, User> {

    private final TabsConverter tabsConverter;

    @Autowired
    public UserConverter(TabsConverter tabsConverter) {
        this.tabsConverter = tabsConverter;
    }

    @Override
    public UserDto convertToDto(final User entity) {
        final UserDto userDto = new UserDto();
        BeanUtils.copyProperties(entity, userDto);
        return userDto;
    }

    @Override
    public User convertToDbo(final UserDto userDto) {
        final User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setActive(true);
        if (userDto.getUsername().equals("root"))
            user.setRole(UserRole.ADMIN);
        else user.setRole(UserRole.USER);
        return user;
    }

    private void setTabsSetToDto(final UserDto userDto, final User user){
        final Set<Tabs> tabsSet = user.getTabsSet();
        if (tabsSet != null){
            for (final Tabs tabs : tabsSet){
                tabs.setUser(null);
            }
        }
        final Set<TabsDto> dtoTabsSet = tabsConverter.convertToDto(tabsSet);
        userDto.setTabsSet(dtoTabsSet);
    }

    private void setTabsSetToEntity(final User user, final UserDto userDto){
        final Set<Tabs> tabsSet = tabsConverter.convertToDbo(userDto.getTabsSet());
        user.setTabsSet(tabsSet);
    }

}
