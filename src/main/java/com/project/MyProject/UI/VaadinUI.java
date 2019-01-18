//package com.project.MyProject.UI;
//
//import com.vaadin.annotations.Theme;
//import com.vaadin.navigator.PushStateNavigation;
//import com.vaadin.navigator.View;
//import com.vaadin.navigator.ViewDisplay;
//import com.vaadin.server.VaadinRequest;
//import com.vaadin.spring.annotation.SpringUI;
//import com.vaadin.spring.annotation.SpringViewDisplay;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.Component;
//import com.vaadin.ui.CssLayout;
//import com.vaadin.ui.HorizontalLayout;
//import com.vaadin.ui.Label;
//import com.vaadin.ui.Panel;
//import com.vaadin.ui.UI;
//import com.vaadin.ui.themes.ValoTheme;
//
//@SpringUI(path = "vaadin")
//@Theme("valo")
//@PushStateNavigation
//@SpringViewDisplay
//public class VaadinUI extends UI implements ViewDisplay {
//
//    private final HorizontalLayout mainLayout = new HorizontalLayout();
//    private final Panel viewConteiner = new Panel();
//    private final CssLayout menu = new CssLayout();
//
//    @Override
//    protected void init(final VaadinRequest vaadinRequest) {
//        Button users = new Button("Users", e -> {
//            getUI().getNavigator().navigateTo(UsersView.NAME);
//        });
//        users.addStyleNames(ValoTheme.BUTTON_LINK, ValoTheme.MENU_ITEM);
//
//        menu.addComponents(tittle, rooms, users);
//        menu.addStyleName(ValoTheme.MENU_ROOT);
//        menu.setWidth("150px");
//        mainLayout.setSizeFull();
//        viewConteiner.setSizeFull();
//        mainLayout.addComponents(menu, viewConteiner);
//        mainLayout.setWidth("100%");
//        setContent(mainLayout);
//    }
//
//    @Override
//    public void showView(final View view) {
//        viewConteiner.setContent((Component) view);
//    }
//}
