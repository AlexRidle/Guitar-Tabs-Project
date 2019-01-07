package com.project.MyProject.repository;

import com.project.MyProject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository  extends JpaRepository<User, Long> {

    User findByUsername(String name);

    @Query(value = "DELETE FROM `FAVOURITES` WHERE `tabs_id`=:tabs_id", nativeQuery = true)
    void removeTabsFromFavourites(@Param("tabs_id") long tabsId);

    @Query(value = "SELECT * FROM `USER` " +
            "JOIN `FAVOURITES` ON `USER`.`id` = `FAVOURITES`.`user_id` " +
            "WHERE `tabs_id`=:tabs_id", nativeQuery = true)
    User findUserByTabsId(@Param("tabs_id") long tabsId);
}
