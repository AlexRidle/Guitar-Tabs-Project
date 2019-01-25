package com.project.MyProject.UI.window;

import com.project.MyProject.dto.tabs.CreateTabsDto;
import com.project.MyProject.dto.tabs.UpdateTabDto;
import com.project.MyProject.entity.Tabs;
import com.project.MyProject.service.TabsService;
import com.vaadin.data.Binder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import org.springframework.security.core.context.SecurityContextHolder;
import org.vaadin.spring.security.VaadinSecurity;

import java.util.Arrays;
import java.util.List;

public class EditTabWindow extends Window {

    private VaadinSecurity vaadinSecurity;

    private TabsService tabsService;

    private VerticalLayout layout;
    private HorizontalLayout songAuthorAndTitle;
    private Button editTab = new Button("Edit", this::editTabButtonClick);
    private List<String> data;
    private RadioButtonGroup radioButtonGroup;
    private Tabs tabs;
    private TextField author;
    private TextField title;
    private TextArea tabBody;

    private ListDataProvider<Tabs> dataProvider;
    private Binder<UpdateTabDto> binder = new Binder<>();
    private UpdateTabDto updateTabDto = new UpdateTabDto();

    public EditTabWindow(Tabs tabs, TabsService tabsService, VaadinSecurity vaadinSecurity, final ListDataProvider<Tabs> dataProvider) {
        super(tabs.getArtist() + " - " + tabs.getTitle());
        this.tabsService = tabsService;
        this.tabs = tabs;
        this.vaadinSecurity = vaadinSecurity;
        this.dataProvider = dataProvider;

        data = Arrays.asList("Public", "Private");
        radioButtonGroup = new RadioButtonGroup<>("Tab type", data);
        radioButtonGroup.setSelectedItem(
                tabs.isHidden() ? data.get(1) : data.get(0)
        );

        author = new TextField();
        author.setWidth("382px");
        author.setPlaceholder("Author");
        author.setValue(tabs.getArtist());

        title = new TextField();
        title.setWidth("382px");
        title.setPlaceholder("Title");
        title.setValue(tabs.getTitle());

        tabBody = new TextArea();
        tabBody.setSizeFull();
        tabBody.setValue(tabs.getTabsBody());

        songAuthorAndTitle = new HorizontalLayout();
        songAuthorAndTitle.addComponents(author, title);
        layout = new VerticalLayout();
        layout.addComponents(songAuthorAndTitle,tabBody,radioButtonGroup,editTab);

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
        binder.readBean(updateTabDto);
        binder.forField(author)
                .withValidator(new StringLengthValidator("Should be not null",1,100))
                .bind(UpdateTabDto::getArtist,UpdateTabDto::setArtist);
        binder.forField(title)
                .withValidator(new StringLengthValidator("Should be not null",1,100))
                .bind(UpdateTabDto::getTitle,UpdateTabDto::setTitle);
        binder.forField(tabBody)
                .withValidator(new StringLengthValidator("Should be not null",1,65535))
                .bind(UpdateTabDto::getTabsBody,UpdateTabDto::setTabsBody);
    }

    private void editTabButtonClick(Button.ClickEvent e) {

        try {
            if (binder.isValid()) {
                binder.writeBean(updateTabDto);
                updateTabDto.setArtist(updateTabDto.getArtist());
                updateTabDto.setTitle(updateTabDto.getTitle());
                updateTabDto.setTabsBody(updateTabDto.getTabsBody());
                updateTabDto.setHidden(String.valueOf(radioButtonGroup.getValue()).equals("Private"));
                String response = tabsService.updateTabs(updateTabDto, tabs.getId(), vaadinSecurity.getAuthentication().getName());
                if(response.equals("Tab with id " + tabs.getId() + " has been updated")){
                    close();
                    dataProvider.refreshAll();
                    Notification.show("Success", response, Notification.Type.TRAY_NOTIFICATION);
                } else {
                    Notification.show("An error occurred", response, Notification.Type.ERROR_MESSAGE);
                }
            } else {
                Notification.show("Enter valid values!", Notification.Type.WARNING_MESSAGE);
            }
        }catch(Exception e1){
            Notification.show("An error occurred", Notification.Type.WARNING_MESSAGE);
        }
    }

}
