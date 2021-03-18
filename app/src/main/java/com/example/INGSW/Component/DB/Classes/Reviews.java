package com.example.INGSW.Component.DB.Classes;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Reviews {

    @JsonProperty("id_review")
    private int id_review;
    @JsonProperty("title")
    private String title;
    @JsonProperty("desc")
    private String desc;
    @JsonProperty("val")
    private int val;
    @JsonProperty("idFilm")
    private int idFilm;
    @JsonProperty("iduser")
    private String iduser;

    public Reviews(@JsonProperty("id_review") int id_review,
                   @JsonProperty("title") String title,
                   @JsonProperty("desc") String desc,
                   @JsonProperty("val") int val,
                   @JsonProperty("idFilm") int idFilm,
                   @JsonProperty("iduser") String iduser) {
        this.id_review = id_review;
        this.title = title;
        this.desc = desc;
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

    public String getDesc() {
        return desc;
    }

    @JsonSetter("desc")
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getVal() {
        return val;
    }

    @JsonSetter("val")
    public void setVal(int val) {
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
