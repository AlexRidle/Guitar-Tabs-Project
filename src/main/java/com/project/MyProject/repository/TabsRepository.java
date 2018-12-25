package com.project.MyProject.repository;

import com.project.MyProject.entity.Tabs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TabsRepository extends JpaRepository<Tabs, Long> {

    List<Tabs> findAllByHiddenIsFalse();

    List<Tabs> findByUserId(Long userId);
    List<Tabs> findByUserIdAndHiddenIsFalse(Long userId);

    List<Tabs> findByArtist(String artist);
    List<Tabs> findByArtistAndHiddenIsFalse(String artist);

    List<Tabs> findByTitle(String title);
    List<Tabs> findByTitleAndHiddenIsFalse(String title);

    List<Tabs> findByArtistAndTitle(String artist, String title);
    List<Tabs> findByArtistAndTitleAndHiddenIsFalse(String artist, String title);
}
