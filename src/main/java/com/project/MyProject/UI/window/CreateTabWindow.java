package com.project.MyProject.UI.window;

import com.project.MyProject.dto.tabs.CreateTabsDto;
import com.project.MyProject.dto.tabs.TabsDto;
import com.project.MyProject.dto.user.LoginDto;
import com.project.MyProject.entity.Tabs;
import com.project.MyProject.service.TabsService;
import com.vaadin.data.Binder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.validator.StringLengthValidator;
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

    Binder<CreateTabsDto> binder = new Binder<>();
    CreateTabsDto createTabsDto = new CreateTabsDto();

    public CreateTabWindow(TabsService tabsService, VaadinSecurity vaadinSecurity, final ListDataProvider<Tabs> dataProvider) {
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

        setBinder();
        setContent(layout);
    }

    private void setBinder(){
        binder.readBean(createTabsDto);
        binder.forField(author)
                .withValidator(new StringLengthValidator("Should be not null",1,100))
                .bind(CreateTabsDto::getArtist,CreateTabsDto::setArtist);
        binder.forField(title)
                .withValidator(new StringLengthValidator("Should be not null",1,100))
                .bind(CreateTabsDto::getTitle,CreateTabsDto::setTitle);
        binder.forField(tabBody)
                .withValidator(new StringLengthValidator("Should be not null",1,65535))
                .bind(CreateTabsDto::getTabsBody,CreateTabsDto::setTabsBody);
    }

    private void createTabButtonClick(Button.ClickEvent e) {

        try {
            if (binder.isValid()) {
                binder.writeBean(createTabsDto);
                createTabsDto.setHidden(String.valueOf(radioButtonGroup.getValue()).equals("Private"));
                createTabsDto.setUsername(vaadinSecurity.getAuthentication().getName());
                tabsService.createTabs(createTabsDto);
                Notification.show("Success", "Tab has been created", Notification.Type.TRAY_NOTIFICATION);
                close();
            } else {
                Notification.show("Enter valid values!", Notification.Type.WARNING_MESSAGE);
            }
        }catch(Exception e1){
            Notification.show("An error occurred", Notification.Type.WARNING_MESSAGE);
        }


    }

}
