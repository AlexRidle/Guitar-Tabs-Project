//package com.project.MyProject.UI.view;
//
//import com.vaadin.data.provider.ConfigurableFilterDataProvider;
//import com.vaadin.data.provider.DataProvider;
//import com.vaadin.icons.VaadinIcons;
//import com.vaadin.navigator.View;
//import com.vaadin.navigator.ViewChangeListener;
//import com.vaadin.server.Page;
//import com.vaadin.server.VaadinSession;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.Grid;
//import com.vaadin.ui.HorizontalLayout;
//import com.vaadin.ui.Label;
//import com.vaadin.ui.TextField;
//import com.vaadin.ui.VerticalLayout;
//import com.vaadin.ui.components.grid.MultiSelectionModel;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public abstract class AbstractViewGrid<T> extends HorizontalLayout implements View {
//
//    protected List<T> dtoList = new ArrayList<>();
//    protected DataProvider<T, String> dataProvider;
//    protected Grid<T> grid = new Grid<>();
//    protected MultiSelectionModel<T> selectionModel;
//    protected ConfigurableFilterDataProvider<T, Void, String> wrapper;
//
//    @Override
//    public void enter(ViewChangeListener.ViewChangeEvent event) {
//        grid.setSizeFull();
//    }
//
//    protected abstract void settingSelectionModel();
//
//    protected abstract void setEventListeners();
//}
