package com.project.MyProject.converter;

import com.project.MyProject.dto.TabsDto;
import com.project.MyProject.entity.Tabs;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class TabsConverter implements DtoEntityConverter<TabsDto, Tabs> {

    @Override
    public TabsDto convertToDto(final Tabs entity) {
        final TabsDto tabsDto = new TabsDto();
        BeanUtils.copyProperties(entity, tabsDto);
        return tabsDto;
    }

    @Override
    public Tabs convertToDbo(final TabsDto tabsDto) {
        final Tabs tabs = new Tabs();
        BeanUtils.copyProperties(tabsDto, tabs);
        return tabs;
    }

}
