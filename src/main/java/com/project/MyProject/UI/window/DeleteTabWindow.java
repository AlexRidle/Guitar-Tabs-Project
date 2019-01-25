package com.project.MyProject.UI.window;

import com.project.MyProject.entity.Tabs;
import com.project.MyProject.repository.TabsRepository;
import com.project.MyProject.service.TabsService;
import com.project.MyProject.service.UserService;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.security.VaadinSecurity;
import org.vaadin.spring.servlet.Vaadin4SpringServlet;

import java.util.Set;

public class DeleteTabWindow extends Window {

    private TabsService tabsService;
    private VaadinSecurity vaadinSecurity;

    private Grid<Tabs> grid;
    private Set<Tabs> tabs;
    private VerticalLayout mainLayout;
    private HorizontalLayout buttons;
    private Label label = new Label();
    private Button delete = new Button("Delete", this::deleteTabButtonClick);
    private Button cancel = new Button("Cancel", this::cancelButtonClick);

    public DeleteTabWindow(Set<Tabs> tabs, Grid<Tabs> grid, TabsService tabsService, VaadinSecurity vaadinSecurity) {
        super("Delete selected tabs");
        this.grid = grid;
        this.tabs = tabs;
        this.tabsService = tabsService;
        this.vaadinSecurity = vaadinSecurity;
        label.setValue("Are you sure?");

        buttons = new HorizontalLayout();
        buttons.addComponents(delete, cancel);

        mainLayout = new VerticalLayout();
        mainLayout.addComponents(label, buttons);
        mainLayout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
        mainLayout.setComponentAlignment(buttons, Alignment.MIDDLE_CENTER);

        center();
        setResizable(false);
        setDraggable(false);
        setModal(true);
        setClosable(true);
        setWidth("300px");
        setContent(mainLayout);
    }

    private void deleteTabButtonClick(Button.ClickEvent e) {

        if(tabsService.deleteTab(tabs, vaadinSecurity.getAuthentication().getName())){
            Notification.show("Success", "Tabs has been deleted", Notification.Type.TRAY_NOTIFICATION);
        } else {
            Notification.show("Error", "You don\'t have permissions to delete this tab", Notification.Type.ERROR_MESSAGE);
        }

        grid.getDataProvider().refreshAll();
        close();

    }

    private void cancelButtonClick(Button.ClickEvent e) {
        close();
    }

}
