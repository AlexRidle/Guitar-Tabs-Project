package com.project.MyProject.dbo;

import javax.persistence.*;

@Entity
public class BandDbo {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String bandName;
    private String genre;

    @JoinColumn(name = "user_id")
    private String author;

    public BandDbo() {
    }

    public BandDbo(String bandName, String genre, UserDbo userDbo) {
        this.author = userDbo.getUsername();
        this.bandName = bandName;
        this.genre = genre;
    }

    public String getAuthor(){
        return author != null ? author : "<none>";
    }

    public void setAuthor(String author) {
        this.author = author;
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
