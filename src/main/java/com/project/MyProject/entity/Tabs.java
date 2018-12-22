package com.project.MyProject.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "TABS")
public class Tabs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private Long userId;

    @NonNull
    private boolean hidden;

    @NonNull
    private String artist;

    @NonNull
    private String title;

    @NonNull
    private String tabsBody;
}
