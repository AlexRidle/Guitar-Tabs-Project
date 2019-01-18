package com.project.MyProject.UI.view;

import com.project.MyProject.UI.window.CreateTabWindow;
import com.project.MyProject.UI.window.DeleteTabWindow;
import com.project.MyProject.UI.window.EditTabWindow;
import com.project.MyProject.UI.window.TabWindow;
import com.project.MyProject.dto.comment.CommentDto;
import com.project.MyProject.entity.Comment;
import com.project.MyProject.entity.Tabs;
import com.project.MyProject.repository.CommentRepository;
import com.project.MyProject.repository.TabsRepository;
import com.project.MyProject.service.CommentService;
import com.project.MyProject.service.TabsService;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Set;

@SpringUI
@Theme("valo")
public class MainView extends UI {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SpringNavigator navigator;

    @Autowired
    private TabsRepository tabsRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private TabsService tabsService;

    private Authentication auth;
    private SecurityContext sc;
    private TextField username;
    private PasswordField password;
    private Grid<Tabs> grid = new Grid<>();
    private VerticalLayout loginForm;
    private VerticalLayout logoutForm;
    private Button loginButton = new Button("Login", this::loginButtonClick);
    private Button logoutButton = new Button("Logout", this::logoutButtonClick);
    private Button openTab = new Button("Open", this::openTabButtonClick);
    private Button addTab = new Button("Add", this::addTabButtonClick);
    private Button editTab = new Button("Edit", this::editTabButtonClick);
    private Button deleteTab = new Button("Delete", this::deleteTabButtonClick);

    private Label helloLabel = new Label();

    @Override
    protected void init(VaadinRequest request) {
        setSizeFull();

        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.addColumn(Tabs::getArtist).setCaption("Artist");
        grid.addColumn(Tabs::getTitle).setCaption("Title");
        grid.addColumn(Tabs::getTabsBody).setCaption("Tabs");
        List<Tabs> tabs = tabsRepository.findAll();
        grid.setItems(tabs);
        grid.addSelectionListener(selection -> {
            if(sc != null && !sc.getAuthentication().getName().equals("anonymousUser")) {
                final int selectedItemsSize = selection.getAllSelectedItems().size();
                editTab.setEnabled(selectedItemsSize == 1);
                openTab.setEnabled(selectedItemsSize == 1);
                deleteTab.setEnabled(selectedItemsSize > 0);
                addTab.setEnabled(true);
            } else {
                final int selectedItemsSize = selection.getAllSelectedItems().size();
                openTab.setEnabled(selectedItemsSize == 1);
                editTab.setEnabled(false);
                deleteTab.setEnabled(false);
                addTab.setEnabled(false);
            }
        });

        username = new TextField();
        username.setWidth("300px");
        username.setPlaceholder("Username");

        password = new PasswordField();
        password.setWidth("300px");
        password.setPlaceholder("Password");

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.addComponents(openTab,addTab,editTab,deleteTab);

        VerticalLayout tabsList = new VerticalLayout();
        tabsList.addComponent(buttons);
        tabsList.addComponent(grid);
        tabsList.setMargin(new MarginInfo(true, true, true, true));
        tabsList.setExpandRatio(grid, 1);
        tabsList.setSizeFull();

        loginForm = new VerticalLayout(username, password, loginButton);
        loginForm.setMargin(new MarginInfo(true, true, true, true));
        loginForm.setSizeUndefined();
        loginForm.setVisible(false);

        logoutForm = new VerticalLayout(helloLabel, logoutButton);
        logoutForm.setMargin(new MarginInfo(true, true, true, true));
        logoutForm.setSizeUndefined();
        logoutForm.setVisible(false);

        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.addComponents(tabsList, loginForm, logoutForm);
        mainLayout.setComponentAlignment(loginForm, Alignment.TOP_RIGHT);
        mainLayout.setExpandRatio(tabsList, 1);
        mainLayout.setSizeFull();

        if (SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName().equals("anonymousUser")) {
            loginForm.setVisible(true);
        } else {
            logoutForm.setVisible(true);
        }

        deleteTab.setEnabled(false);
        editTab.setEnabled(false);
        openTab.setEnabled(false);
        addTab.setEnabled(sc != null && sc.getAuthentication().getName().equals("anonymousUser"));
        setContent(mainLayout);
    }

    private void loginButtonClick(Button.ClickEvent e) {
        auth = new UsernamePasswordAuthenticationToken(username.getValue(),password.getValue());
        try {
            authenticationManager.authenticate(auth);
            SecurityContextHolder.getContext().setAuthentication(auth);
            sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);
            username.setValue("");
            password.setValue("");
            addTab.setEnabled(true);
        } catch(org.springframework.security.authentication.BadCredentialsException error){
            Notification.show("Invalid login or password!", Notification.Type.ERROR_MESSAGE);
        }

        if (SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName().equals("anonymousUser")) {
            loginForm.setVisible(true);
            addTab.setEnabled(true);
        } else {
            helloLabel.setValue("Hello, " + SecurityContextHolder.getContext().getAuthentication().getName() + "!");
            loginForm.setVisible(false);
            logoutForm.setVisible(true);
        }

    }

    private void logoutButtonClick(Button.ClickEvent e) {
        sc = null;
        loginForm.setVisible(true);
        logoutForm.setVisible(false);
        editTab.setEnabled(false);
        deleteTab.setEnabled(false);
        openTab.setEnabled(false);
        addTab.setEnabled(false);
    }

    private void openTabButtonClick(Button.ClickEvent e) {
        Tabs tabs = grid.getSelectedItems().iterator().next();
        List<CommentDto> comments = commentService.getCommentsOfTab(tabs.getId());
        TabWindow tabWindow = new TabWindow(tabs, comments);
        UI.getCurrent().addWindow(tabWindow);
    }

    private void addTabButtonClick(Button.ClickEvent e) {
        CreateTabWindow createTabWindow = new CreateTabWindow(tabsService);
        UI.getCurrent().addWindow(createTabWindow);
    }

    private void editTabButtonClick(Button.ClickEvent e) {
        Tabs tabs = grid.getSelectedItems().iterator().next();
        EditTabWindow editTabWindow = new EditTabWindow(tabs, tabsService);
        UI.getCurrent().addWindow(editTabWindow);
    }

    private void deleteTabButtonClick(Button.ClickEvent e) {
        Set<Tabs> tabs = grid.getSelectedItems();
        DeleteTabWindow deleteTabWindow = new DeleteTabWindow(tabs, grid, tabsRepository);
        UI.getCurrent().addWindow(deleteTabWindow);
    }

}
