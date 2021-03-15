package com.example.INGSW.Component.DB.Classes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class UserLists {


    @JsonProperty("idUserList")
    private int idUserList;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("type")
    private String type;
    @JsonProperty("idUser")
    private String idUser;

    @JsonCreator
    public UserLists(@JsonProperty("idUserList") int idUserList,
                     @JsonProperty("title") String title,
                     @JsonProperty("description") String description,
                     @JsonProperty("type") String type,
                     @JsonProperty("idUser") String idUser){
        this.idUserList = idUserList;
        this.title = title;
        this.description = description;
        this.type = type;
        this.idUser = idUser;
    }

    public int getIdUserList() {
        return idUserList;
    }

    @JsonSetter("idUserList")
    public void setIdUserList(int idUserList) {
        this.idUserList = idUserList;
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

    public String getType() {
        return type;
    }

    @JsonSetter("type")
    public void setType(String type) {
        this.type = type;
    }

    public String getIdUser() {
        return idUser;
    }

    @JsonSetter("idUser")
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
