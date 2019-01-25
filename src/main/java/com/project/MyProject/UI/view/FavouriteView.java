package com.project.MyProject.UI.view;

import com.project.MyProject.dto.tabs.TabsDto;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@SpringView(name = FavouriteView.NAME)
@AllArgsConstructor
@Component(FavouriteView.NAME)
@UIScope
public class FavouriteView extends HorizontalLayout implements View {
    public static final String NAME = "favourites";

    @PostConstruct
    void init() {
        this.setCaption("Favourites");
        addComponent(new Label("This is second view"));
    }

    protected void setEventListeners() {
    }
}
