//package com.project.MyProject.UI;
//
//import com.project.MyProject.UI.components.MainLayout;
//import com.vaadin.annotations.Theme;
//import com.vaadin.navigator.PushStateNavigation;
//import com.vaadin.navigator.View;
//import com.vaadin.navigator.ViewDisplay;
//import com.vaadin.server.VaadinRequest;
//import com.vaadin.spring.annotation.SpringUI;
//import com.vaadin.spring.annotation.SpringViewDisplay;
//import com.vaadin.ui.Component;
//import com.vaadin.ui.UI;
//import lombok.AllArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.vaadin.spring.security.VaadinSecurity;
//
//@SpringUI
//@Theme("darktheme")
//@PushStateNavigation
//@SpringViewDisplay
//@AllArgsConstructor
//public class VaadinUI extends UI implements ViewDisplay {
//
//    private MainLayout mainLayout;
//
//    @Override
//    protected void init(final VaadinRequest vaadinRequest) {
//        setContent(mainLayout);
//        mainLayout.setUI(this);
//    }
//
//    @Override
//    public void showView(final View view) {
//        mainLayout.showView((Component) view);
//    }
//
//}
