package com.project.MyProject.UI;

import com.project.MyProject.entity.Tabs;
import com.project.MyProject.repository.TabsRepository;
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
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@SpringUI
@Theme("valo")
public class MainView extends UI {

    @Autowired
    private SpringNavigator navigator;

    @Autowired
    private TabsRepository tabsRepository;

    private TextField username;
    private PasswordField password;
    private Button loginButton = new Button("Login", this::loginButtonClick);
    private Button logoutButton = new Button("Logout", this::logoutButtonClick);
    private Label helloLabel = new Label();

    @Override
    protected void init(VaadinRequest request) {
        setSizeFull();

        Grid<Tabs> grid = new Grid<>();
        grid.setSizeFull();
        grid.addColumn(Tabs::getArtist).setCaption("Artist");
        grid.addColumn(Tabs::getTitle).setCaption("Title");
        grid.addColumn(Tabs::getTabsBody).setCaption("Tabs");
        List<Tabs> tabs = tabsRepository.findAllByHiddenIsFalse();
        grid.setItems(tabs);

        username = new TextField();
        username.setWidth("300px");
        username.setPlaceholder("Username");

        password = new PasswordField();
        password.setWidth("300px");
        password.setPlaceholder("Password");

        VerticalLayout tabsList = new VerticalLayout(grid);
        tabsList.setMargin(new MarginInfo(true, true, true, true));
        tabsList.setSizeFull();

        VerticalLayout loginForm = new VerticalLayout(username, password, loginButton);
        loginForm.setMargin(new MarginInfo(true, true, true, true));
        loginForm.setSizeUndefined();
        loginForm.setVisible(false);

        VerticalLayout logoutForm = new VerticalLayout(helloLabel, logoutButton);
        logoutForm.setMargin(new MarginInfo(true, true, true, true));
        helloLabel.setValue("Hello, "
                + SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName()
                + "!");
        logoutForm.setSizeUndefined();
        logoutForm.setVisible(false);


        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(tabsList, loginForm, logoutForm);
        horizontalLayout.setComponentAlignment(loginForm, Alignment.TOP_RIGHT);
        horizontalLayout.setExpandRatio(tabsList, 1);
        horizontalLayout.setSizeFull();

        if (SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName().equals("anonymousUser")) {
            loginForm.setVisible(true);
        } else {
            logoutForm.setVisible(true);
        }

        setContent(horizontalLayout);
    }

    private void loginButtonClick(Button.ClickEvent e) {
        Authentication auth = new UsernamePasswordAuthenticationToken(username.getValue(),password.getValue());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private void logoutButtonClick(Button.ClickEvent e) {
    }

}
