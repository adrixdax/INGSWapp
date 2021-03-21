package com.example.INGSW.Component.DB.Classes;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Reviews {

    @JsonProperty("id_review")
    private int id_review;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("val")
    private float val;
    @JsonProperty("idFilm")
    private int idFilm;
    @JsonProperty("iduser")
    private String iduser;

    public Reviews(@JsonProperty("id_review") int id_review,
                   @JsonProperty("title") String title,
                   @JsonProperty("description") String description,
                   @JsonProperty("val") float val,
                   @JsonProperty("idFilm") int idFilm,
                   @JsonProperty("iduser") String iduser) {
        this.id_review = id_review;
        this.title = title;
        this.description = description;
        this.val = val;
        this.idFilm = idFilm;
        this.iduser = iduser;
    }

    public int getId_review() {
        return id_review;
    }

    @JsonSetter("id_review")
    public void setId_review(int id_review) {
        this.id_review = id_review;
    }

    public String getTitle() {
        return title;
    }

    @JsonSetter("title")
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    @JsonSetter("desc")
    public void setDescription(String description) {
        this.description = description;
    }


    public float getVal() {
        return val;
    }

    @JsonSetter("val")
    public void setVal(float val) {
        this.val = val;
    }

    public int getIdFilm() {
        return idFilm;
    }

    @JsonSetter("idFilm")
    public void setIdFilm(int idFilm) {
        this.idFilm = idFilm;
    }

    public String getIduser() {
        return iduser;
    }

    @JsonSetter("iduser")
    public void setIduser(String iduser) {
        this.iduser = iduser;
    }
}
