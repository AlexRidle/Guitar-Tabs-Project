package com.project.MyProject.service;

import com.project.MyProject.converter.TabsConverter;
import com.project.MyProject.dto.TabsDto;
import com.project.MyProject.dto.UpdateTabDto;
import com.project.MyProject.entity.Tabs;
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

    public void createTabs(final TabsDto tabsDto) {
        tabsRepository.save(tabsConverter.convertToDbo(tabsDto));
    }

    public TabsDto getTab(final long id) {
        final Optional<Tabs> tabs = tabsRepository.findById(id);
        if (!tabs.isPresent()) {
            throw new DatabaseException("Tab with id " + id + " isn\'t exists.");
        }
        return tabsConverter.convertToDto(tabs.get());
    }

    public List<TabsDto> getUserTabs(final long id, final boolean hidden) {
        final List<Tabs> tabs = tabsRepository.findByUserId(id);

        if (tabs.isEmpty()) {
            throw new DatabaseException("User with id " + id + " hasn\'t published any tabs.");
        }

        if (hidden) {
            return tabsRepository.findByUserId(id)
                    .stream().map(tabsConverter::convertToDto)
                    .collect(Collectors.toList());
        } else {
            return tabsRepository.findByUserIdAndHiddenIsFalse(id)
                    .stream().map(tabsConverter::convertToDto)
                    .collect(Collectors.toList());
        }
    }

    public List<TabsDto> findTabs(final String artist, final String title, final boolean hidden) {

        if (artist.equals("ALL_ARTISTS") && title.equals("ALL_TITLES")) {
            if (hidden) {
                return tabsRepository.findAll().stream().map(tabsConverter::convertToDto).collect(Collectors.toList());
            } else {
                return tabsRepository.findAllByHiddenIsFalse().stream().map(tabsConverter::convertToDto).collect(Collectors.toList());
            }
        }

        if (artist.equals("ALL_ARTISTS")) {
            final List<Tabs> tabs = tabsRepository.findByTitle(title);
            if (tabs.isEmpty()) {
                throw new DatabaseException("Cant find any tabs with title \'" + title + "\'.");
            }

            if (hidden) {
                return tabsRepository.findByTitle(title).stream().map(tabsConverter::convertToDto).collect(Collectors.toList());
            } else {
                return tabsRepository.findByTitleAndHiddenIsFalse(title).stream().map(tabsConverter::convertToDto).collect(Collectors.toList());
            }
        }

        if (title.equals("ALL_TITLES")) {
            final List<Tabs> tabs = tabsRepository.findByArtist(artist);
            if (tabs.isEmpty()) {
                throw new DatabaseException("Cant find any tabs with artist \'" + artist + "\'.");
            }
            if (hidden) {
                return tabsRepository.findByArtist(artist).stream().map(tabsConverter::convertToDto).collect(Collectors.toList());
            } else {
                return tabsRepository.findByArtistAndHiddenIsFalse(artist).stream().map(tabsConverter::convertToDto).collect(Collectors.toList());

            }
        }

        final List<Tabs> tabs = tabsRepository.findByArtistAndTitle(artist, title);

        if (tabs.isEmpty()) {
            throw new DatabaseException("Cant find any tabs with artist \'" + artist + "\' and title \'" + title +"\'.");
        }

        if (hidden) {
            return tabsRepository.findByArtistAndTitle(artist, title).stream().map(tabsConverter::convertToDto).collect(Collectors.toList());
        } else {
            return tabsRepository.findByArtistAndTitleAndHiddenIsFalse(artist, title).stream().map(tabsConverter::convertToDto).collect(Collectors.toList());
        }

    }

    /*author Stanislav Patskevich */
    public String deleteTab(final long id, final String login){
        if (tabsRepository.existsById(id)){
            if (userRepository.findByUsername(login).getRole().equals("ADMIN") ||
                    userRepository.findByUsername(login).getId()==tabsRepository.getById(id).getUserId()) {
                final Tabs tab = tabsRepository.getById(id);
                tabsRepository.delete(tab);
                return  "Tab с ID № "+id+" был удалён";
            } else return "Вы не можите удалить этот tab";
        } else return "Неверный id";
    }

    public String updateTabs(final UpdateTabDto updateTabDto, final long id, final String login) {
        if (tabsRepository.existsById(id)){
            if (userRepository.findByUsername(login).getRole().equals("ADMIN") ||
                    userRepository.findByUsername(login).getId()==tabsRepository.getById(id).getUserId()) {
                final Tabs tab = tabsRepository.getById(id);
                tab.setArtist(updateTabDto.getArtist());
                tab.setTitle(updateTabDto.getTitle());
                tab.setTabsBody(updateTabDto.getTabsBody());
                tabsRepository.save(tab);
                return  "Tab с ID № "+id+" был изменён";
            } else return "Вы не можите изменить этот tab";
        } else return "Неверный id";
    }

    public String setHidden(final Long id, final String login){
        if (tabsRepository.existsById(id)){
            if (userRepository.findByUsername(login).getId()==tabsRepository.getById(id).getUserId()){
                final Tabs tab = tabsRepository.getById(id);
                if (tab.isHidden()) tab.setHidden(false);
                else tab.setHidden(true);
                tabsRepository.save(tab);
                return  "Измененно на "+tab.isHidden();
            } else return "Вы не можите редактировать этот tab";
        } else return "Неверный id";
    }

    public List<TabsDto> getTabsList(final Authentication auth) {
        if (auth.getName().equals("anonymousUser")) return tabsRepository.findAllByHiddenIsFalse().stream().map(tabsConverter::convertToDto).collect(Collectors.toList());
        else if (userRepository.findByUsername(auth.getName()).getRole().equals("ADMIN"))
            return tabsRepository.findAll().stream().map(tabsConverter::convertToDto).collect(Collectors.toList());
        else {
            final List<TabsDto> list = tabsRepository.findAll().stream().map(tabsConverter::convertToDto).collect(Collectors.toList());
            final List<TabsDto> newList = new LinkedList<>();
            for (TabsDto tab : list) {
                if (tab.getUserId()==userRepository.findByUsername(auth.getName()).getId()) newList.add(tab);
                else if (!tab.isHidden()) newList.add(tab);
            }
            return newList;
        }
    }
}
