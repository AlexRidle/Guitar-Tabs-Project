package com.project.MyProject.service;

import com.project.MyProject.dto.TabsDto;
import com.project.MyProject.dto.UpdateTabDto;
import com.project.MyProject.dto.UserDto;
import com.project.MyProject.entity.Tabs;
import com.project.MyProject.entity.User;

public class MockData {

    public static User user() {
        final User user = new User();
        user.setId(1L);
        user.setUsername("user username");
        user.setPassword("user password");
        user.setEmail("user email");
        user.setActive(true);
        user.setActivationCode("user activationCode");
        user.setRole("USER");
        return user;
    }

    public static UserDto userDto() {
        final UserDto userDto = new UserDto();
        userDto.setUsername("userDto username");
        userDto.setEmail("userDto email");
        return userDto;
    }

    public static Tabs tabs() {
        final Tabs tabs = new Tabs();
        tabs.setId(new Long(1));
        tabs.setUserId(1L);
        tabs.setHidden(false);
        tabs.setArtist("tabs artist");
        tabs.setTitle("tabs title");
        tabs.setTabsBody("tabs body");
        return tabs;
    }

    public static TabsDto tabsDto() {
        final TabsDto tabsDto = new TabsDto();
        tabsDto.setUserId(100L);
        tabsDto.setHidden(true);
        tabsDto.setArtist("tabsDto artist");
        tabsDto.setTitle("tabsDto title");
        tabsDto.setTabsBody("tabsDto body");
        return tabsDto;
    }

    public static UpdateTabDto updateTabDto() {
        final UpdateTabDto updateTabDto = new UpdateTabDto();
        updateTabDto.setArtist("new artist");
        updateTabDto.setTabsBody("new tabs body");
        updateTabDto.setTitle("new title");
        return updateTabDto;
    }

}
