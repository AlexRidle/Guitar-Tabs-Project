package com.project.MyProject.service;

import com.project.MyProject.converter.TabsConverter;
import com.project.MyProject.dto.TabsDto;
import com.project.MyProject.dto.UpdateTabDto;
import com.project.MyProject.entity.Tabs;
import com.project.MyProject.entity.User;
import com.project.MyProject.repository.TabsRepository;
import com.project.MyProject.exception.DatabaseException;
import com.project.MyProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TabsService {

    private final TabsRepository tabsRepository;
    private final TabsConverter tabsConverter;
    private final UserRepository userRepository;

    @Autowired
    public TabsService(final TabsRepository tabsRepository, final TabsConverter tabsConverter, final UserRepository userRepository) {
        this.tabsRepository = tabsRepository;
        this.tabsConverter = tabsConverter;
        this.userRepository = userRepository;
    }

    public void createTabs(final TabsDto tabsDto, final String username) {
        tabsDto.setUserId(userRepository.findByUsername(username).getId());
        tabsRepository.save(tabsConverter.convertToDbo(tabsDto));
    }

    public TabsDto getTab(final long id, final Authentication auth) {
        if (!tabsRepository.existsById(id)) {
            try {
                throw new DatabaseException("Tab with id " + id + " isn\'t exists.");
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
        }
        Tabs tab = tabsRepository.findById(id).get();

        if (userRepository.findByUsername(auth.getName()).getRole().equals("ROLE_ADMIN"))
            return tabsConverter.convertToDto(tab);
        else {
            if (tab.getUser().getId()
                    .equals(userRepository.findByUsername(auth.getName()).getId())
                    || !tab.isHidden()) {
                return tabsConverter.convertToDto(tab);
            } else {
                try {
                    throw new DatabaseException("User don\'t have permissions to watch private tabs.");
                } catch (DatabaseException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
    }

    public List<TabsDto> getTabsList(final Authentication auth) {
        if (auth.getName().equals("anonymousUser"))
            return tabsRepository.findAllByHiddenIsFalse()
                    .stream().map(tabsConverter::convertToDto)
                    .collect(Collectors.toList());
        else if (userRepository.findByUsername(auth.getName()).getRole().equals("ROLE_ADMIN"))
            return tabsRepository.findAll().stream().map(tabsConverter::convertToDto).collect(Collectors.toList());
        else {
            final List<TabsDto> list = tabsRepository.findAll()
                    .stream().map(tabsConverter::convertToDto)
                    .collect(Collectors.toList());
            final List<TabsDto> customUserList = new LinkedList<>();
            for (TabsDto tab : list) {
                if (tab.getUserId().equals(userRepository.findByUsername(auth.getName()).getId()))
                    customUserList.add(tab);
                else if (!tab.isHidden())
                    customUserList.add(tab);
            }
            return customUserList;
        }
    }

    public List<TabsDto> getUserTabs(final long id, final boolean hidden, final Authentication auth) {
        if (tabsRepository.findByUserId(id).isEmpty()) {
            try {
                throw new DatabaseException("User with id " + id + " hasn\'t published any tabs.");
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
        }

        if (hidden) {
            if (!auth.getName().equals("anonymousUser")
                    && userRepository.findByUsername(auth.getName()).getRole().equals("ROLE_ADMIN")) {
                return tabsRepository.findByUserId(id)
                        .stream().map(tabsConverter::convertToDto)
                        .collect(Collectors.toList());
            }
        }
        return tabsRepository.findByUserIdAndHiddenIsFalse(id)
                .stream().map(tabsConverter::convertToDto)
                .collect(Collectors.toList());
    }

    public List<TabsDto> findTabs(final String artist, final String title, boolean hidden,
                                  final Authentication auth) {

        if (hidden) {
            if (auth.getName().equals("anonymousUser")
                    || !userRepository.findByUsername(auth.getName()).getRole().equals("ROLE_ADMIN")) {
                hidden = false;
            }
        }


        if (artist.equals("ALL_ARTISTS") && title.equals("ALL_TITLES")) {
            return hidden ?
                    tabsRepository.findAll().stream().map(tabsConverter::convertToDto).collect(Collectors.toList())
                    : tabsRepository.findAllByHiddenIsFalse().stream().map(tabsConverter::convertToDto).collect(Collectors.toList());
        }

        if (artist.equals("ALL_ARTISTS")) {
            final List<Tabs> tabs = tabsRepository.findByTitle(title);
            if (tabs.isEmpty()) {
                try {
                    throw new DatabaseException("Cant find any tabs with title \'" + title + "\'.");
                } catch (DatabaseException e) {
                    e.printStackTrace();
                }
            }
            return hidden ?
                    tabsRepository.findByTitle(title).stream().map(tabsConverter::convertToDto).collect(Collectors.toList())
                    : tabsRepository.findByTitleAndHiddenIsFalse(title).stream().map(tabsConverter::convertToDto).collect(Collectors.toList());
        }

        if (title.equals("ALL_TITLES")) {
            final List<Tabs> tabs = tabsRepository.findByArtist(artist);
            if (tabs.isEmpty()) {
                try {
                    throw new DatabaseException("Cant find any tabs with artist \'" + artist + "\'.");
                } catch (DatabaseException e) {
                    e.printStackTrace();
                }
            }
            return hidden ?
                    tabsRepository.findByArtist(artist).stream().map(tabsConverter::convertToDto).collect(Collectors.toList())
                    : tabsRepository.findByArtistAndHiddenIsFalse(artist).stream().map(tabsConverter::convertToDto).collect(Collectors.toList());
        }

        final List<Tabs> tabs = tabsRepository.findByArtistAndTitle(artist, title);

        if (tabs.isEmpty()) {
            try {
                throw new DatabaseException("Cant find any tabs with artist \'" + artist + "\' and title \'" + title + "\'.");
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
        }

        return hidden ?
                tabsRepository.findByArtistAndTitle(artist, title).stream().map(tabsConverter::convertToDto).collect(Collectors.toList())
                : tabsRepository.findByArtistAndTitleAndHiddenIsFalse(artist, title).stream().map(tabsConverter::convertToDto).collect(Collectors.toList());
    }

    public String deleteTab(final long id, final String username) {
        User user = userRepository.findByUsername(username);
        if (tabsRepository.existsById(id)) {
            final Tabs tab = tabsRepository.getById(id);
            if (user.getRole().equals("ROLE_ADMIN")
                    || user.getId().equals(tab.getUser().getId())) {
                tabsRepository.delete(tab);
                return "Tab with id " + id + " has been deleted";
            } else return "You don\'t have permissions to delete this tab";
        } else return "Cant find tab with id " + id;
    }

    public String updateTabs(final UpdateTabDto updateTabDto, final long id, final String username) {
        if (tabsRepository.existsById(id)) {
            final User user = userRepository.findByUsername(username);
            final Tabs tab = tabsRepository.getById(id);
            if (user.getRole().equals("ROLE_ADMIN")
                    || user.getId().equals(tab.getUser().getId())) {
                tab.setArtist(updateTabDto.getArtist());
                tab.setTitle(updateTabDto.getTitle());
                tab.setTabsBody(updateTabDto.getTabsBody());
                tabsRepository.save(tab);
                return "Tab with id " + id + " has been updated";
            } else return "You don\'t have permissions to update this tab";
        } else return "Cant find tab with id " + id;
    }

    public String swapHidden(final Long id, final String username) {
        if (tabsRepository.existsById(id)) {
            final Tabs tab = tabsRepository.getById(id);
            final User user = userRepository.findByUsername(username);
            if (user.getId().equals(tab.getUser().getId())) {
                tab.setHidden(!tab.isHidden());
                tabsRepository.save(tab);
                return "Hidden flag of tab id " + id + " has been changed on " + tab.isHidden();
            } else return "You don\'t have permissions to update this tab";
        } else return "Cant find tab with id " + id;
    }
}