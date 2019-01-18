package com.project.MyProject.UI.window;

import com.project.MyProject.dto.comment.CommentDto;
import com.project.MyProject.entity.Comment;
import com.project.MyProject.entity.Tabs;
import com.project.MyProject.repository.CommentRepository;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TabWindow extends Window {

    private VerticalLayout layout = new VerticalLayout();
    private HorizontalLayout commentPanel = new HorizontalLayout();
    private Grid<CommentDto> grid = new Grid<>();
    private Button addComment = new Button("Add", this::addCommentButtonClick);
    private Button editComment = new Button("Edit", this::editCommentButtonClick);
    private Button deleteComment = new Button("Delete", this::deleteCommentButtonClick);
    private TextArea tabBody = new TextArea();
    private TextArea commentBody = new TextArea();

    public TabWindow(Tabs tabs, List<CommentDto> comments) {
        super(tabs.getArtist() + " - " + tabs.getTitle());

        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.addColumn(CommentDto::getUsername).setCaption("User");
        grid.addColumn(CommentDto::getCommentBody).setCaption("Comment");
        grid.setItems(comments);

        tabBody.setValue(tabs.getTabsBody());
        tabBody.setSizeFull();

        commentBody.setSizeFull();
        commentPanel.addComponentsAndExpand(addComment, editComment, deleteComment);

        layout.addComponents(tabBody, grid, commentBody, commentPanel);
        center();

        setWidth("800px");
        setContent(layout);
    }

    private void addCommentButtonClick(Button.ClickEvent e) {


    }

    private void editCommentButtonClick(Button.ClickEvent e) {

    }

    private void deleteCommentButtonClick(Button.ClickEvent e) {

    }
}
