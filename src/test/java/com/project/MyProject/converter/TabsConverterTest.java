package com.project.MyProject.converter;

import com.project.MyProject.service.MockData;
import com.project.MyProject.dto.tabs.TabsDto;
import com.project.MyProject.entity.Tabs;
import org.junit.Test;

import static org.junit.Assert.*;

public class TabsConverterTest {

    private final TabsConverter tabsConverter;

    public TabsConverterTest(final TabsConverter tabsConverter) {
        this.tabsConverter = tabsConverter;
    }

    @Test
    public void convertToDto() {
        final Tabs tabs = MockData.tabs();
        final TabsDto tabsDto = tabsConverter.convertToDto(tabs);
        assertEquals(tabs.getUser().getId(), tabsDto.getUserId());
        assertEquals(tabs.isHidden(), tabsDto.isHidden());
        assertEquals(tabs.getArtist(), tabsDto.getArtist());
        assertEquals(tabs.getTitle(), tabsDto.getTitle());
        assertEquals(tabs.getTitle(), tabsDto.getTitle());
        assertEquals(tabs.getTabsBody(), tabsDto.getTabsBody());
    }

    @Test
    public void convertToDbo() {
        final TabsDto tabsDto = MockData.tabsDto();
        final Tabs tabs = tabsConverter.convertToDbo(tabsDto);
        assertEquals(tabsDto.getUserId(), tabs.getUser().getId());
        assertEquals(tabsDto.isHidden(), tabs.isHidden());
        assertEquals(tabsDto.getArtist(), tabs.getArtist());
        assertEquals(tabsDto.getTitle(), tabs.getTitle());
        assertEquals(tabsDto.getTitle(), tabs.getTitle());
        assertEquals(tabsDto.getTabsBody(), tabs.getTabsBody());
    }

}
