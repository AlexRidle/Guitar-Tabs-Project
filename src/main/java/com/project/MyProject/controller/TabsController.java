package com.project.MyProject.controller;

import com.project.MyProject.dto.TabsDto;
import com.project.MyProject.service.TabsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @DeleteMapping("/delete/{id}")
    public String deleteTab(@PathVariable final long id){
        if (tabsService.deleteTab(id)){
            return "Successfully deleted tabs.";
        }
        return "An error occurred while deleting tabs with id" + id;
    }

    @GetMapping("/all")
    public List<TabsDto> getAllTabs(
            @RequestParam(name = "hidden", required = false, defaultValue = "false") boolean hidden
    ) {
        return tabsService.getTabsList(hidden);
    }

    @GetMapping("/{id}")
    public TabsDto getTab(@PathVariable final long id){
        return tabsService.getTab(id);
    }

    @GetMapping("/user/{id}")
    public List<TabsDto> getUser(
            @PathVariable final long id,
            @RequestParam(name = "hidden", required = false, defaultValue = "false") boolean hidden
    ){
        return tabsService.getUserTabs(id, hidden);
    }

    @GetMapping("/search")
    public List<TabsDto> findTabs(
            @RequestParam(name = "artist", required = false, defaultValue = "ALL_ARTISTS") String artist,
            @RequestParam(name = "title", required = false, defaultValue = "ALL_TITLES") String title,
            @RequestParam(name = "hidden", required = false, defaultValue = "false") boolean hidden){
        return tabsService.findTabs(artist, title, hidden);
    }

    @PutMapping("/update/{id}")
    public String updateUser(@RequestBody final TabsDto tabsDto, @PathVariable final long id){
        if (tabsService.updateTabs(tabsDto, id)){
            return "Tabs with id " + id + " was updated.";
        }
        return "Cant update tabs with id " + id;
    }

}
