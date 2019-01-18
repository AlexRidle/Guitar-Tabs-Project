package com.project.MyProject.UI.window;

import com.project.MyProject.dto.tabs.TabsDto;
import com.project.MyProject.service.TabsService;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import java.util.Arrays;
import java.util.List;

public class CreateTabWindow extends Window {

    private TabsService tabsService;

    private VerticalLayout layout;
    private HorizontalLayout songAuthorAndTitle;
    private Button addTab = new Button("Create", this::createTabButtonClick);
    private List<String> data;
    private RadioButtonGroup radioButtonGroup;
    private TextField author;
    private TextField title;
    private TextArea tabBody;

    public CreateTabWindow(TabsService tabsService) {
        super("Add tab");
        this.tabsService = tabsService;

        data = Arrays.asList("Public", "Private");
        radioButtonGroup = new RadioButtonGroup<>("Tab type", data);
        radioButtonGroup.setSelectedItem(data.get(0));

        author = new TextField();
        author.setWidth("382px");
        author.setPlaceholder("Author");

        title = new TextField();
        title.setWidth("382px");
        title.setPlaceholder("Title");

        tabBody = new TextArea();
        tabBody.setSizeFull();

        songAuthorAndTitle = new HorizontalLayout();
        songAuthorAndTitle.addComponents(author, title);
        layout = new VerticalLayout();
        layout.addComponents(songAuthorAndTitle, tabBody, radioButtonGroup, addTab);

        center();
        setResizable(false);
        setDraggable(false);
        setModal(true);
        setClosable(true);
        setWidth("800px");
        setContent(layout);
    }

    private void createTabButtonClick(Button.ClickEvent e) {
        TabsDto tabsDto = new TabsDto();
        tabsDto.setArtist(author.getValue());
        tabsDto.setTitle(title.getValue());
        tabsDto.setTabsBody(tabBody.getValue());
        tabsDto.setHidden(String.valueOf(radioButtonGroup.getValue()).equals("Private"));
        tabsService.createTabs(tabsDto,"user");
        Notification.show("Success", "Tab has been created", Notification.Type.TRAY_NOTIFICATION);
        close();
    }

}
