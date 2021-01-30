package com.example.INGSW.Component;

public class ListOfFilm {

    private String imageUrl;
    private String title;
    private String plot;
    private String relaseDate;
    private String duration;
    private String category;



    public ListOfFilm(String imageUrl, String title, String plot, String relaseDate, String duration, String category) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.plot = plot;
        this.relaseDate = relaseDate;
        this.duration = duration;
        this.category = category;
    }

    public ListOfFilm(String imageUrl, String title) {
        this.imageUrl = imageUrl;
        this.title = title;
    }

    
    public ListOfFilm(String imageUrl){
        this.imageUrl = imageUrl;;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getRelaseDate() {
        return relaseDate;
    }

    public void setRelaseDate(String relaseDate) {
        this.relaseDate = relaseDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
