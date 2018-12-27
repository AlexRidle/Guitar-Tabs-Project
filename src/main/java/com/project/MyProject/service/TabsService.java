package com.project.MyProject.service;

import com.project.MyProject.converter.TabsConverter;
import com.project.MyProject.dto.TabsDto;
import com.project.MyProject.entity.Tabs;
import com.project.MyProject.repository.TabsRepository;
import com.project.MyProject.exception.DatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TabsService {

    private final TabsRepository tabsRepository;
    private final TabsConverter tabsConverter;

    @Autowired
    public TabsService(final TabsRepository tabsRepository, final TabsConverter tabsConverter) {
        this.tabsRepository = tabsRepository;
        this.tabsConverter = tabsConverter;
    }

    public void createTabs(final TabsDto tabsDto) {
        tabsRepository.save(tabsConverter.convertToDbo(tabsDto));
    }

    public TabsDto getTab(final long id) {
        Optional<Tabs> tabs = tabsRepository.findById(id);
        if (!tabs.isPresent()) {
            throw new DatabaseException("Tab with id " + id + " isn\'t exists.");
        }
        return tabsConverter.convertToDto(tabs.get());
    }

    public List<TabsDto> getTabsList(boolean hidden) {
        if (hidden) {
            return tabsRepository.findAll().stream().map(tabsConverter::convertToDto).collect(Collectors.toList());
        } else {
            return tabsRepository.findAllByHiddenIsFalse().stream().map(tabsConverter::convertToDto).collect(Collectors.toList());
        }
    }

    public List<TabsDto> getUserTabs(final long id, boolean hidden) {
        List<Tabs> tabs = tabsRepository.findByUserId(id);

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
            List<Tabs> tabs = tabsRepository.findByTitle(title);
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
            List<Tabs> tabs = tabsRepository.findByArtist(artist);
            if (tabs.isEmpty()) {
                throw new DatabaseException("Cant find any tabs with artist \'" + artist + "\'.");
            }
            if (hidden) {
                return tabsRepository.findByArtist(artist).stream().map(tabsConverter::convertToDto).collect(Collectors.toList());
            } else {
                return tabsRepository.findByArtistAndHiddenIsFalse(artist).stream().map(tabsConverter::convertToDto).collect(Collectors.toList());

            }
        }

        List<Tabs> tabs = tabsRepository.findByArtistAndTitle(artist, title);

        if (tabs.isEmpty()) {
            throw new DatabaseException("Cant find any tabs with artist \'" + artist + "\' and title \'" + title +"\'.");
        }

        if (hidden) {
            return tabsRepository.findByArtistAndTitle(artist, title).stream().map(tabsConverter::convertToDto).collect(Collectors.toList());
        } else {
            return tabsRepository.findByArtistAndTitleAndHiddenIsFalse(artist, title).stream().map(tabsConverter::convertToDto).collect(Collectors.toList());
        }

    }

    public boolean deleteTab(final long id){
        tabsRepository.deleteById(id);
        Optional <Tabs> tabs = tabsRepository.findById(id);
        return !tabs.isPresent();
    }

    public boolean updateTabs(TabsDto tabsDto, long id) {
        Optional<Tabs> tabs = tabsRepository.findById(id);
        if (tabs.isPresent()){
            tabsDto.setId(id);
            return tabsRepository.save(tabsConverter.convertToDbo(tabsDto)) != null;
        }
        return false;
    }
}
