package com.example.ingsw.component.db.classes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Reviews {

    @JsonProperty("idReviews")
    private int idReviews;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("val")
    private double val;
    @JsonProperty("idRecordRef")
    private int idRecordRef;
    @JsonProperty("iduser")
    private String iduser;
    @JsonProperty("typeOfReview")
    private String typeOfReview;
    @JsonProperty("obscured")
    private boolean obscured;

    @JsonCreator
    public Reviews(@JsonProperty("idReview") int idReview,
                   @JsonProperty("title") String title,
                   @JsonProperty("description") String description,
                   @JsonProperty("val") double val,
                   @JsonProperty("idRecordRef") int idRecordRef,
                   @JsonProperty("iduser") String iduser,
                   @JsonProperty("typeOfReview") String typeOfReview,
                   @JsonProperty("obscured") boolean obscured) {
        this.idReviews = idReview;
        this.title = title;
        this.description = description;
        this.val = val;
        this.idRecordRef = idRecordRef;
        this.iduser = iduser;
        this.typeOfReview = typeOfReview;
        this.obscured = obscured;
    }

    public int getIdReviews() {
        return idReviews;
    }

    @JsonSetter("idReviews")
    public void setIdReviews(int idReviews) {
        this.idReviews = idReviews;
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

    @JsonSetter("description")
    public void setDescription(String description) {
        this.description = description;
    }


    public double getVal() {
        return val;
    }

    @JsonSetter("val")
    public void setVal(double val) {
        this.val = val;
    }

    public int getIdRecordRef() {
        return idRecordRef;
    }

    @JsonSetter("idRecordRef")
    public void setIdRecordRef(int idRecordRef) {
        this.idRecordRef = idRecordRef;
    }

    public String getIduser() {
        return iduser;
    }

    @JsonSetter("iduser")
    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getTypeOfReview() {
        return typeOfReview;
    }

    @JsonSetter("typeOfReview")
    public void setTypeOfReview(String typeOfReview) {
        this.typeOfReview = typeOfReview;
    }

    public boolean isObscured() {
        return obscured;
    }

    @JsonSetter("obscured")
    public void setObscured(boolean obscured) {
        this.obscured = obscured;
    }
}
