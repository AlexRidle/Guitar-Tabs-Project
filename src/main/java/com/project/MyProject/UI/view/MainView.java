//package com.project.MyProject.UI.view;
//
//import com.project.MyProject.UI.window.CreateTabWindow;
//import com.project.MyProject.UI.window.DeleteTabWindow;
//import com.project.MyProject.UI.window.EditTabWindow;
//import com.project.MyProject.UI.window.TabWindow;
//import com.project.MyProject.dto.comment.CommentDto;
//import com.project.MyProject.dto.tabs.TabsDto;
//import com.project.MyProject.entity.Tabs;
//import com.project.MyProject.repository.TabsRepository;
//import com.project.MyProject.service.CommentService;
//import com.project.MyProject.service.TabsService;
//import com.vaadin.annotations.Theme;
//import com.vaadin.navigator.View;
//import com.vaadin.server.VaadinRequest;
//import com.vaadin.shared.ui.MarginInfo;
//import com.vaadin.spring.annotation.SpringUI;
//import com.vaadin.spring.annotation.SpringView;
//import com.vaadin.spring.annotation.UIScope;
//import com.vaadin.ui.Alignment;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.Grid;
//import com.vaadin.ui.HorizontalLayout;
//import com.vaadin.ui.Label;
//import com.vaadin.ui.Notification;
//import com.vaadin.ui.PasswordField;
//import com.vaadin.ui.TextField;
//import com.vaadin.ui.UI;
//import com.vaadin.ui.VerticalLayout;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.vaadin.spring.security.VaadinSecurity;
//
//import javax.annotation.PostConstruct;
//import java.util.List;
//import java.util.Set;
//
//@SpringView(name = MainView.NAME)
////@AllArgsConstructor
//@Component(MainView.NAME)
//@UIScope
//public class MainView extends AbstractViewGrid<Tabs>  {
//    public static final String NAME = "tabs";
//
//    @Autowired
//    private TabsRepository tabsRepository;
//    @Autowired
//    private CommentService commentService;
//    @Autowired
//    private TabsService tabsService;
//
////    private VaadinSecurity vaadinSecurity;
//
//    private Button openTab = new Button("Open");
//    private Button addTab = new Button("Add");
//    private Button editTab = new Button("Edit");
//    private Button deleteTab = new Button("Delete");
//
//    @PostConstruct
//    void init() {
//        setSizeFull();
//
//        grid = new Grid<>();
//        grid.setSelectionMode(Grid.SelectionMode.MULTI);
//        grid.addColumn(Tabs::getArtist).setCaption("Artist");
//        grid.addColumn(Tabs::getTitle).setCaption("Title");
//        grid.addColumn(Tabs::getTabsBody).setCaption("Tabs");
//        List<Tabs> tabs = tabsRepository.findAll();
//        grid.setItems(tabs);
//
//        HorizontalLayout buttons = new HorizontalLayout();
//        buttons.addComponents(openTab,addTab,editTab,deleteTab);
//
//        VerticalLayout tabsList = new VerticalLayout();
//        tabsList.addComponent(buttons);
//        tabsList.addComponent(grid);
//        tabsList.setMargin(new MarginInfo(true, true, true, true));
//        tabsList.setExpandRatio(grid, 1);
//        tabsList.setSizeFull();
//
//        this.addComponent(tabsList);
//        this.setExpandRatio(tabsList, 1);
//        this.setSizeFull();
//
//        deleteTab.setEnabled(false);
//        editTab.setEnabled(false);
//        openTab.setEnabled(false);
////        addTab.setEnabled(vaadinSecurity.isAuthenticated());
//        addTab.setEnabled(false);
//        setEventListeners();
//    }
//
//    @Override
//    protected void settingSelectionModel() {
//        selectionModel.addMultiSelectionListener(multiSelectionEvent -> {
////            if (vaadinSecurity.isAuthenticated()) {
//            if (true) {
//                final int selectedItemsSize = multiSelectionEvent.getAllSelectedItems().size();
//                editTab.setEnabled(selectedItemsSize == 1);
//                openTab.setEnabled(selectedItemsSize == 1);
//                deleteTab.setEnabled(selectedItemsSize > 0);
//                addTab.setEnabled(true);
//            } else {
//                final int selectedItemsSize = multiSelectionEvent.getAllSelectedItems().size();
//                openTab.setEnabled(selectedItemsSize == 1);
//                editTab.setEnabled(false);
//                deleteTab.setEnabled(false);
//                addTab.setEnabled(false);
//            }
//        });
//    }
//
//    protected void setEventListeners() {
//        deleteTab.addClickListener(clickEvent -> {
//            Set<Tabs> tabs = grid.getSelectedItems();
////            DeleteTabWindow deleteTabWindow = new DeleteTabWindow(tabs, grid, tabsService, vaadinSecurity);
////            UI.getCurrent().addWindow(deleteTabWindow);
//        });
//        editTab.addClickListener(clickEvent -> {
//            Tabs tabs = grid.getSelectedItems().iterator().next();
////            EditTabWindow editTabWindow = new EditTabWindow(tabs, tabsService, vaadinSecurity);
////            UI.getCurrent().addWindow(editTabWindow);
//        });
//        openTab.addClickListener(clickEvent -> {
//            Tabs tabs = grid.getSelectedItems().iterator().next();
//            List<CommentDto> comments = commentService.getCommentsOfTab(tabs.getId());
//            TabWindow tabWindow = new TabWindow(tabs, comments);
//            UI.getCurrent().addWindow(tabWindow);
//        });
//        openTab.addClickListener(clickEvent -> {
////            CreateTabWindow createTabWindow = new CreateTabWindow(tabsService, vaadinSecurity);
////            UI.getCurrent().addWindow(createTabWindow);
//        });
//    }
//}