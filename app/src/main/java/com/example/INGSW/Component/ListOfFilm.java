package com.example.INGSW.Component;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.List;

public class ListOfFilm {

    private int id_Film;
    private String film_Title;
    private String plot;
    private String posterPath;
    private String relase_Date;
    private int runtime;
    private String[] genres;
    private Cast[] cast;

    private List<ListOfFilm> listOfFilm=null;

    public List<ListOfFilm> getListOfFilm() {
        return listOfFilm;
    }

    public void setListOfFilm(List<ListOfFilm> listOfFilm) {
        this.listOfFilm = listOfFilm;
    }

    @JsonCreator
    public ListOfFilm(@JsonProperty("id_Film") int id_Film, @JsonProperty("film_Title") String film_Title, @JsonProperty("plot") String plot, @JsonProperty("posterPath") String posterPath, @JsonProperty("release_Date") String relase_Date, @JsonProperty("runtime") int runtime, @JsonProperty("genres") String[] genres, @JsonProperty("cast") Cast[] cast) {
        this.id_Film = id_Film;
        this.film_Title = film_Title;
        this.plot = plot;
        this.posterPath = posterPath;
        this.relase_Date = relase_Date;
        this.runtime = runtime;
        this.genres = genres;
        this.cast = cast;
    }

    public ListOfFilm(String imageUrl, String title, String plot, String relaseDate, int duration, String[] category) {
        this.posterPath = imageUrl;
        this.film_Title = title;
        this.plot = plot;
        this.relase_Date = relaseDate;
        this.runtime = duration;
        this.genres = category;
    }

    public ListOfFilm(String imageUrl, String title) {
        this.posterPath = imageUrl;
        this.film_Title = title;
    }

    public ListOfFilm(List<ListOfFilm> listOfFilm){
        listOfFilm=listOfFilm;
    }


    public ListOfFilm(String imageUrl) {
        this.posterPath = imageUrl;
    }

    public int getId_Film() {
        return id_Film;
    }

    @JsonSetter("id_Film")
    public void setId_Film(int id_Film) {
        this.id_Film = id_Film;
    }

    public String getFilm_Title() {
        return film_Title;
    }

    @JsonSetter("film_Title")
    public void setFilm_Title(String film_Title) {
        this.film_Title = film_Title;
    }

    public String getPlot() {
        return plot;
    }

    @JsonSetter("plot")
    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getPosterPath() {
        return posterPath;
    }

    @JsonSetter("posterPath")
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getRelase_Date() {
        return relase_Date;
    }

    @JsonSetter("release_Date")
    public void setRelase_Date(String relase_Date) {
        this.relase_Date = relase_Date;
    }

    public int getRuntime() {
        return runtime;
    }

    @JsonSetter("runtime")
    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String[] getGenres() {
        return genres;
    }

    @JsonSetter("genres")
    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public Cast[] getCast() {
        return cast;
    }

    @JsonSetter("cast")
    public void setCast(Cast[] cast) {
        this.cast = cast;
    }
}
