package com.project.MyProject.service;

import com.project.MyProject.converter.TabsConverter;
import com.project.MyProject.dto.tabs.TabsDto;
import com.project.MyProject.dto.tabs.UpdateTabDto;
import com.project.MyProject.entity.Tabs;
import com.project.MyProject.entity.User;
import com.project.MyProject.enumeration.UserRole;
import com.project.MyProject.repository.TabsRepository;
import com.project.MyProject.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

//@RunWith(MockitoJUnitRunner.class)
public class TabServiceTest {

    @InjectMocks
    private TabsService tabsService;

    @Mock
    private TabsRepository tabsRepository;

    @Mock
    private UserRepository userRepository;

    @Spy
    private TabsConverter tabsConverter;

    @Before
    public void init() {
        tabsConverter = new TabsConverter(userRepository);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void setHidden(){
        final Tabs tab = MockData.tabs();
        final User user = MockData.user();

        doReturn(true).when(tabsRepository).existsById(tab.getId());
        doReturn(user).when(userRepository).findByUsername(user.getUsername());
        doReturn(tab).when(tabsRepository).getById(tab.getId());

        final String answer1 = tabsService.swapHidden(tab.getId(), user.getUsername());

        assertTrue(tab.isHidden());
        assertEquals("Hidden flag of tab id " + tab.getId() + " has been changed on " + tab.isHidden(), answer1);

        final String answer2 = tabsService.swapHidden(tab.getId(), user.getUsername());

        verify(tabsRepository, times(2)).save(tab);
        assertFalse(tab.isHidden());
        assertEquals("Hidden flag of tab id " + tab.getId() + " has been changed on " + tab.isHidden(), answer2);
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
        assertEquals("Tab with id "+ tab.getId() + " has been deleted", answer1);

        tab.setUser(MockData.user());
        user.setRole(UserRole.ADMIN);

        final String answer2 = tabsService.deleteTab(tab.getId(), user.getUsername());

        verify(tabsRepository, times(2)).delete(tab);
        assertEquals("Tab with id "+ tab.getId() + " has been deleted", answer2);

        user.setRole(UserRole.USER);

        final String answer3 = tabsService.deleteTab(tab.getId(), user.getUsername());

        verify(tabsRepository, times(3)).delete(tab);
        assertEquals("Tab with id "+ tab.getId() + " has been deleted", answer3);
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
        assertEquals("Tab with id " + tab.getId() + " has been updated", answer1);

        tab.setArtist("tabs artist");
        tab.setTitle("tabs title");
        tab.setTabsBody("tabs body");
        tab.setUser(MockData.user());
        user.setRole(UserRole.ADMIN);

        final String answer2 = tabsService.updateTabs(updateTabDto, tab.getId(), user.getUsername());

        verify(tabsRepository, times(2)).save(tab);
        assertEquals(updateTabDto.getTitle(), tab.getTitle());
        assertEquals(updateTabDto.getTabsBody(), tab.getTabsBody());
        assertEquals(updateTabDto.getArtist(), tab.getArtist());
        assertEquals("Tab with id " + tab.getId() + " has been updated", answer2);

        user.setRole(UserRole.USER);

        final String answer3 = tabsService.updateTabs(updateTabDto, tab.getId(), user.getUsername());

        assertEquals("Tab with id " + tab.getId() + " has been updated", answer3);
    }

    @Test
    public void addToFavourites(){

        final long [] tabIds = new long[] {2L, 3L, 4L};

        final Tabs tab = MockData.tabs();
        User user = MockData.user();

        doReturn(user).when(userRepository).findByUsername(user.getUsername());
        doReturn(Optional.of(tab)).when(tabsRepository).findById(tab.getId());
        doReturn(user).when(userRepository).save(user);

        final TabsDto[] tabsDtos = tabsService.addTabsToFavourites(user.getUsername(), tabIds);

        verify(userRepository, times(1)).save(user);

    }
}
