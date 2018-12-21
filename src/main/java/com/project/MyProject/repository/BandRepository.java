package com.project.MyProject.repository;

import com.project.MyProject.dbo.BandDbo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BandRepository extends CrudRepository<BandDbo, Integer> {

    List<BandDbo> findByGenre(String genre);

}