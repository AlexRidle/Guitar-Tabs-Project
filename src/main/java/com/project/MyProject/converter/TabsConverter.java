package com.project.MyProject.converter;

import com.project.MyProject.dto.TabsDto;
import com.project.MyProject.entity.Tabs;
import com.project.MyProject.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service

public class TabsConverter implements DtoEntityConverter<TabsDto, Tabs> {

    private final UserRepository userRepository;

    public TabsConverter(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public TabsDto convertToDto(final Tabs entity) {
        final TabsDto tabsDto = new TabsDto();
        tabsDto.setId(entity.getId());
        tabsDto.setArtist(entity.getArtist());
        tabsDto.setTitle(entity.getTitle());
        tabsDto.setHidden(entity.isHidden());
        tabsDto.setTabsBody(entity.getTabsBody());
        tabsDto.setUserId(entity.getUser().getId());
        return tabsDto;
    }

    @Override
    public Tabs convertToDbo(final TabsDto tabsDto) {
        final Tabs tabs = new Tabs();
        BeanUtils.copyProperties(tabsDto, tabs);
        tabs.setUser(userRepository.findByUsername(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName()));
        return tabs;
    }

}
