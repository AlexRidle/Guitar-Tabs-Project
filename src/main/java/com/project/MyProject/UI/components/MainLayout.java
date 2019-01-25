package com.project.MyProject.UI.components;

import com.project.MyProject.UI.view.FavouriteView;
import com.project.MyProject.UI.view.MainView;
import com.project.MyProject.UI.window.CreateTabWindow;
import com.project.MyProject.UI.window.DeleteTabWindow;
import com.project.MyProject.UI.window.EditTabWindow;
import com.project.MyProject.UI.window.TabWindow;
import com.project.MyProject.dto.comment.CommentDto;
import com.project.MyProject.entity.Tabs;
import com.project.MyProject.repository.TabsRepository;
import com.project.MyProject.service.CommentService;
import com.project.MyProject.service.TabsService;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.vaadin.spring.security.VaadinSecurity;

import java.util.List;
import java.util.Set;

@Component
@UIScope
public class MainLayout extends HorizontalLayout {

    private VaadinSecurity vaadinSecurity;

    private final Panel viewConteiner = new Panel();
    private final CssLayout menu = new CssLayout();
    private UI ui;

    private TextField username;
    private PasswordField password;
    private VerticalLayout loginForm;
    private VerticalLayout logoutForm;
    private Button loginButton = new Button("Login", this::loginButtonClick);
    private Button logoutButton = new Button("Logout", this::logoutButtonClick);
    private Label helloLabel = new Label();

    protected MainLayout(VaadinSecurity vaadinSecurity) {
        this.vaadinSecurity = vaadinSecurity;

        Label title = new Label("Menu");
        title.addStyleName(ValoTheme.MENU_TITLE);
        Button tabs = new Button("Tabs", e-> {
                getUI().getNavigator().navigateTo(MainView.NAME);
        });
        tabs.addStyleNames(ValoTheme.BUTTON_LINK,ValoTheme.MENU_ITEM);
        Button favourites = new Button("Favourites", e-> {
            if (vaadinSecurity.isAuthenticated()) {
                getUI().getNavigator().navigateTo(FavouriteView.NAME);
            } else {
                Notification.show("Please, log in", Notification.Type.WARNING_MESSAGE);
            }
        });
        favourites.addStyleNames(ValoTheme.BUTTON_LINK,ValoTheme.MENU_ITEM);
        menu.addComponents(title,tabs,favourites);
        menu.addStyleName(ValoTheme.MENU_ROOT);
        menu.setWidth("30%");
        menu.setHeight("100%");



        username = new TextField();
        username.setWidth("300px");
        username.setPlaceholder("Username");
        password = new PasswordField();
        password.setWidth("300px");
        password.setPlaceholder("Password");

        loginForm = new VerticalLayout(username, password, loginButton);
        loginForm.setMargin(new MarginInfo(true, true, true, true));
        loginForm.setSizeUndefined();
        loginForm.setVisible(false);

        logoutForm = new VerticalLayout(helloLabel, logoutButton);
        logoutForm.setMargin(new MarginInfo(true, true, true, true));
        logoutForm.setSizeUndefined();
        logoutForm.setVisible(false);



        this.addComponents(menu, viewConteiner, loginForm, logoutForm);
        this.setComponentAlignment(loginForm, Alignment.TOP_RIGHT);
        this.setComponentAlignment(logoutForm, Alignment.TOP_RIGHT);
        this.setComponentAlignment(viewConteiner, Alignment.TOP_LEFT);
        this.getComponent(1).setSizeFull();
        this.getComponent(0).setWidth("150px");
        this.setExpandRatio(viewConteiner,1);
        this.setSizeFull();
        if (!vaadinSecurity.isAuthenticated()) {
            loginForm.setVisible(true);
        } else {
            logoutForm.setVisible(true);
        }

    }

    private void loginButtonClick(Button.ClickEvent e) {
        try {
            vaadinSecurity.login(username.getValue(),password.getValue());
            username.setValue("");
            password.setValue("");
        } catch (Exception error) {
            Notification.show("Invalid login or password!", Notification.Type.ERROR_MESSAGE);
            error.printStackTrace();
        }
        if (!vaadinSecurity.isAuthenticated()) {
            loginForm.setVisible(true);
        } else {
            helloLabel.setValue("Hello, " + vaadinSecurity.getAuthentication().getName() + "!");
            loginForm.setVisible(false);
            logoutForm.setVisible(true);
        }

    }

    private void logoutButtonClick(Button.ClickEvent e) {
        vaadinSecurity.logout();
    }

    public void setUI(final UI ui){
        this.ui = ui;
        getUI().getNavigator().navigateTo(MainView.NAME);
    }

    public void showView(final com.vaadin.ui.Component component) {
        viewConteiner.setContent(component);
    }

}
