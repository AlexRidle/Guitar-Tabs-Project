package com.project.MyProject.service;

import com.project.MyProject.converter.TabsConverter;
import com.project.MyProject.dto.UpdateTabDto;
import com.project.MyProject.entity.Tabs;
import com.project.MyProject.entity.User;
import com.project.MyProject.repository.TabsRepository;
import com.project.MyProject.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class TabServiceTest {

    @InjectMocks
    private TabsService tabsService;

    @Mock
    private TabsRepository tabsRepository;

    @Mock
    private TabsConverter tabsConverter;

    @Mock
    private UserRepository userRepository;

    @Test
    public void setHidden(){
        final Tabs tab = MockData.tabs();
        final User user = MockData.user();

        doReturn(true).when(tabsRepository).existsById(tab.getId());
        doReturn(user).when(userRepository).findByUsername(user.getUsername());
        doReturn(tab).when(tabsRepository).getById(tab.getId());

        final String answer1 = tabsService.setHidden(tab.getId(), user.getUsername());

        assertEquals(true, tab.isHidden());
        assertEquals("Измененно на "+tab.isHidden(), answer1);

        final String answer2 = tabsService.setHidden(tab.getId(), user.getUsername());

        verify(tabsRepository, times(2)).save(tab);
        assertEquals(false, tab.isHidden());
        assertEquals("Измененно на "+tab.isHidden(), answer2);
    }

    @Test
    public void deleteTab(){

        final Tabs tab = MockData.tabs();
        final User user = MockData.user();

        doReturn(true).when(tabsRepository).existsById(tab.getId());
        doReturn(user).when(userRepository).findByUsername(user.getUsername());
        doReturn(tab).when(tabsRepository).getById(tab.getId());

        final String answer1 = tabsService.deleteTab(tab.getId(), user.getUsername());

        verify(tabsRepository, times(1)).delete(tab);
        assertEquals("Tab с ID № "+tab.getId()+" был удалён", answer1);

        tab.setUserId(2L);
        user.setRole("ADMIN");

        final String answer2 = tabsService.deleteTab(tab.getId(), user.getUsername());

        verify(tabsRepository, times(2)).delete(tab);
        assertEquals("Tab с ID № "+tab.getId()+" был удалён", answer2);

        user.setRole("USER");

        final String answer3 = tabsService.deleteTab(tab.getId(), user.getUsername());

        assertEquals("Вы не можите удалить этот tab", answer3);
    }

    @Test
    public void updateTabs(){

        final Tabs tab = MockData.tabs();
        final User user = MockData.user();
        final UpdateTabDto updateTabDto = MockData.updateTabDto();

        doReturn(true).when(tabsRepository).existsById(tab.getId());
        doReturn(user).when(userRepository).findByUsername(user.getUsername());
        doReturn(tab).when(tabsRepository).getById(tab.getId());

        final String answer1 = tabsService.updateTabs(updateTabDto, tab.getId(), user.getUsername());

        verify(tabsRepository, times(1)).save(tab);
        assertEquals(updateTabDto.getTitle(), tab.getTitle());
        assertEquals(updateTabDto.getTabsBody(), tab.getTabsBody());
        assertEquals(updateTabDto.getArtist(), tab.getArtist());
        assertEquals("Tab с ID № "+tab.getId()+" был изменён", answer1);

        tab.setArtist("tabs artist");
        tab.setTitle("tabs title");
        tab.setTabsBody("tabs body");
        tab.setUserId(2L);
        user.setRole("ADMIN");

        final String answer2 = tabsService.updateTabs(updateTabDto, tab.getId(), user.getUsername());

        verify(tabsRepository, times(2)).save(tab);
        assertEquals(updateTabDto.getTitle(), tab.getTitle());
        assertEquals(updateTabDto.getTabsBody(), tab.getTabsBody());
        assertEquals(updateTabDto.getArtist(), tab.getArtist());
        assertEquals("Tab с ID № "+tab.getId()+" был изменён", answer2);

        user.setRole("USER");

        final String answer3 = tabsService.updateTabs(updateTabDto, tab.getId(), user.getUsername());

        assertEquals("Вы не можите изменить этот tab", answer3);
    }
}
