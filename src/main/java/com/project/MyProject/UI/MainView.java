package com.project.MyProject.UI;

import com.project.MyProject.entity.Tabs;
import com.project.MyProject.repository.TabsRepository;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringUI
@Theme("valo")
public class MainView extends UI {

    @Autowired
    private  SpringNavigator navigator;

    @Autowired
    private TabsRepository tabsRepository;

    private HorizontalLayout tabsLayout;

    @Override
    protected void init(final VaadinRequest vaadinRequest) {
        Grid<Tabs> grid = new Grid<>();
//        Grid<Tabs> grid = new Grid<>(Tabs.class);
        grid.addColumn(Tabs::getArtist).setCaption("Artist");
        grid.addColumn(Tabs::getTitle).setCaption("Title");
        grid.addColumn(Tabs::getTabsBody).setCaption("Tabs");
        setContent(grid);
        grid.setSizeFull();
        List<Tabs> tabs = tabsRepository.findAllByHiddenIsFalse();
        grid.setItems(tabs);
//        grid.setDataProvider(
//                q -> tabsRepository.findAll().stream(),
//                q -> (int) tabsRepository.count());

//        setupTabsLayout();
//        final VerticalLayout viewContainer = setupViewContainer();
//        final VerticalLayout content = new VerticalLayout(tabsLayout, viewContainer);
//        content.setSizeFull();
//        setContent(content);
//        navigator.init(this, viewContainer);
//        navigator.navigateTo(LoginView.NAME);
    }

//    private VerticalLayout setupViewContainer() {
//        final VerticalLayout v1 = new VerticalLayout();
//        v1.setSizeFull();
//        return v1;
//    }
//
//    private void setupTabsLayout() {
//        this.tabsLayout = new HorizontalLayout();
//    }
}
