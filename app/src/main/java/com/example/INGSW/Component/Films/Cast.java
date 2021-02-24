package com.example.INGSW.Component.Films;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Cast {
    private String name;
    private String character;
    private String link;

    public Cast(@JsonProperty("name") String name, @JsonProperty("character") String character, @JsonProperty("link") String link) {
        this.name = name;
        this.character = character;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
