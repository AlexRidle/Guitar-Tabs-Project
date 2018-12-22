package com.project.MyProject.service;

import com.project.MyProject.converter.TabsConverter;
import com.project.MyProject.dto.TabsDto;
import com.project.MyProject.entity.Tabs;
import com.project.MyProject.repository.TabsRepository;
import com.project.MyProject.service.exception.DatabaseException;
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

    public List<TabsDto> getTabsList() {
        return tabsRepository.findAll().stream().map(tabsConverter::convertToDto).collect(Collectors.toList());
    }

    public TabsDto getTab(final long id){
        Optional<Tabs> tabs = tabsRepository.findById(id);
        if (!tabs.isPresent()){
            throw new DatabaseException("Tab with id " + id + " isn\'t exists.");
        }
        return tabsConverter.convertToDto(tabs.get());
    }

    public List<TabsDto> getUser(final long id){
        List<Tabs> tabs = tabsRepository.findByUserId(id);
        if (tabs.isEmpty()){
            throw new DatabaseException("User with id " + id + " hasn\'t published any tabs.");
        }
        return tabsRepository.findByUserId(id)
                .stream().map(tabsConverter::convertToDto)
                .collect(Collectors.toList());
    }

    public List<TabsDto> getArtist(final String artist){
        List<Tabs> tabs = tabsRepository.findByArtist(artist);
        if (tabs.isEmpty()){
            throw new DatabaseException("Cant find tabs with artist \"" + artist + "\"");
        }
        return tabsRepository.findByArtist(artist)
                .stream().map(tabsConverter::convertToDto)
                .collect(Collectors.toList());
    }

    public List<TabsDto> getTitle(final String title){
        List<Tabs> tabs = tabsRepository.findByTitle(title);
        if (tabs.isEmpty()){
            throw new DatabaseException("Cant find tabs with title \"" + title + "\"");
        }
        return tabsRepository.findByTitle(title).stream().map(tabsConverter::convertToDto).collect(Collectors.toList());
    }
}
