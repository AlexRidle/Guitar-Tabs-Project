package com.project.MyProject.controller;

import com.project.MyProject.dto.TabsDto;
import com.project.MyProject.dto.UserDto;
import com.project.MyProject.service.TabsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tabs")
public class TabsController {

    private final TabsService tabsService;

    @Autowired
    public TabsController(final TabsService tabsService) {
        this.tabsService = tabsService;
    }

    @PostMapping("/add")
    public String addTabs(@RequestBody final TabsDto tabsDto) {
        tabsService.createTabs(tabsDto);
        return "Tabs added";
    }

    @GetMapping("/all")
    public List<TabsDto> getAllTabs() {
        return tabsService.getTabsList();
    }

    @GetMapping("/{id}")
    public TabsDto getTab(@PathVariable final long id){
        return tabsService.getTab(id);
    }

    @GetMapping("/user/{id}")
    public List<TabsDto> getUser(@PathVariable final long id){
        return tabsService.getUser(id);
    }

    @GetMapping("/artist/{artist}")
    public List<TabsDto> getArtist(@PathVariable final String artist){
        return tabsService.getArtist(artist);
    }

    @GetMapping("/title/{title}")
    public List<TabsDto> getTitle(@PathVariable final String title){
        return tabsService.getTitle(title);
    }


}
