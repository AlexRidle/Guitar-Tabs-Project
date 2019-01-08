package com.project.MyProject.controller;

import com.project.MyProject.dto.tabs.TabsDto;
import com.project.MyProject.dto.tabs.UpdateTabDto;
import com.project.MyProject.service.TabsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
        tabsService.createTabs(tabsDto, SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());
        return "Tabs added";
    }

    @DeleteMapping("/delete")
    //Cannot delete or update a parent row: a foreign key constraint fails (have to change settings for deleting)
    public String deleteTab(@RequestParam final long id){
        return  tabsService.deleteTab(id,SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());
    }

    @GetMapping("/all")
    public List<TabsDto> getAllTabs() {
        return tabsService.getTabsList(SecurityContextHolder
                .getContext()
                .getAuthentication());
    }

    @GetMapping("/tab")
    public TabsDto getTab(@RequestParam final long id){
        return tabsService.getTab(id, SecurityContextHolder
                .getContext()
                .getAuthentication());
    }

    @GetMapping("/user")
    public List<TabsDto> getUser(
            @RequestParam final long id,
            @RequestParam(name = "hidden", required = false, defaultValue = "false") final boolean hidden
    ){
        return tabsService.getUserTabs(id, hidden, SecurityContextHolder
                .getContext()
                .getAuthentication());
    }

    @GetMapping("/search")
    public List<TabsDto> findTabs(
            @RequestParam(name = "artist", required = false, defaultValue = "ALL_ARTISTS") final String artist,
            @RequestParam(name = "title", required = false, defaultValue = "ALL_TITLES") final String title,
            @RequestParam(name = "hidden", required = false, defaultValue = "false") final boolean hidden){
        return tabsService.findTabs(artist, title, hidden, SecurityContextHolder
                .getContext()
                .getAuthentication());
    }

    @PutMapping("/update")
    public String updateTabs(@RequestBody final UpdateTabDto updateTabDto, @RequestParam final long id){
        return  tabsService.updateTabs(updateTabDto, id, SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());
    }

    @PutMapping("/swapHidden")
    public String setHiddenTabs(@RequestParam final long id){
        return tabsService.swapHidden(id, SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());
    }

}
