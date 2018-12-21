package com.project.MyProject.dbo;

import javax.persistence.*;

@Entity
public class BandDbo {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String bandName;
    private String genre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserDbo author;

    public BandDbo() {
    }

    public BandDbo(String bandName, String genre, UserDbo userDbo) {
        this.author = userDbo;
        this.bandName = bandName;
        this.genre = genre;
    }

    public UserDbo getAuthor() {
        return author;
    }

    public String getAuthorName(){
        return author != null ? author.getUsername() : "<none>";
    }

    public void setAuthor(UserDbo author) {
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
