package com.project.MyProject.dbo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BandDbo {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String bandName;

    private String genre;

    public BandDbo() {
    }

    public BandDbo(String bandName, String genre) {
        this.bandName = bandName;
        this.genre = genre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBandName() {
        return bandName;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

}
