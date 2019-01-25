package com.project.MyProject.UI.window;

import com.project.MyProject.dto.tabs.CreateTabsDto;
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
import org.vaadin.spring.security.VaadinSecurity;

import java.util.Arrays;
import java.util.List;

public class CreateTabWindow extends Window {

    private VaadinSecurity vaadinSecurity;

    private TabsService tabsService;

    private VerticalLayout layout;
    private HorizontalLayout songAuthorAndTitle;
    private Button addTab = new Button("Create", this::createTabButtonClick);
    private List<String> data;
    private RadioButtonGroup radioButtonGroup;
    private TextField author;
    private TextField title;
    private TextArea tabBody;

    public CreateTabWindow(TabsService tabsService, VaadinSecurity vaadinSecurity) {
        super("Add tab");
        this.tabsService = tabsService;
        this.vaadinSecurity = vaadinSecurity;

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
        CreateTabsDto createTabsDto = new CreateTabsDto();
        createTabsDto.setArtist(author.getValue());
        createTabsDto.setTitle(title.getValue());
        createTabsDto.setTabsBody(tabBody.getValue());
        createTabsDto.setHidden(String.valueOf(radioButtonGroup.getValue()).equals("Private"));
        createTabsDto.setUsername(vaadinSecurity.getAuthentication().getName());
        tabsService.createTabs(createTabsDto);
        Notification.show("Success", "Tab has been created", Notification.Type.TRAY_NOTIFICATION);
        close();
    }

}
