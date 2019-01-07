package com.project.MyProject.repository;

import com.project.MyProject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository  extends JpaRepository<User, Long> {

    User findByUsername(String name);

    @Modifying
    @Query(value = "DELETE FROM favourites WHERE tabs_id=:tabs_id AND user_id=:user_id", nativeQuery = true)
    @Transactional
    void removeTabsFromFavourites(@Param("tabs_id") long tabsId, @Param("user_id") long userId);

    @Query(value = "SELECT * FROM `user` " +
            "JOIN `favourites` ON `user`.`id` = `favourites`.`user_id` " +
            "WHERE `tabs_id`=:tabs_id", nativeQuery = true)
    User findUserByTabsId(@Param("tabs_id") long tabsId);
}