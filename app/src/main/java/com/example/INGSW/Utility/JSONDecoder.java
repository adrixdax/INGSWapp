package com.example.INGSW.Utility;

import com.example.INGSW.Component.DB.Classes.Contact;
import com.example.INGSW.Component.DB.Classes.Notify;
import com.example.INGSW.Component.DB.Classes.Reviews;
import com.example.INGSW.Component.DB.Classes.UserLists;
import com.example.INGSW.Component.Films.Film;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

import teaspoon.annotations.OnBackground;


public class JSONDecoder {

    private static final ObjectMapper mapper = new ObjectMapper();

    @OnBackground
    private static List<Film> jsonFilmList(String json) throws JsonProcessingException {
        return Arrays.asList(mapper.readValue(json, Film[].class));
    }

    @OnBackground
    private static List<Notify> jsonNotify(String json) throws JsonProcessingException {
        return Arrays.asList(mapper.readValue(json, Notify[].class));
    }

    @OnBackground
    private static List<UserLists> jsonUserLists(String json) throws JsonProcessingException {
        return Arrays.asList(mapper.readValue(json, UserLists[].class));
    }

    @OnBackground
    private static List<Reviews> jsonReviews(String json) throws JsonProcessingException {
        return Arrays.asList(mapper.readValue(json, Reviews[].class));
    }

    @OnBackground
    private static List<Contact> jsonContact(String json) throws JsonProcessingException {
        return Arrays.asList(mapper.readValue(json, Contact[].class));
    }

    @OnBackground
    public static Object getJsonToDecode(String json, Class c) throws JsonProcessingException {
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        if (c.getSimpleName().equals("Notify"))
            return jsonNotify(json);
        else if (c.getSimpleName().equals("Film"))
            return jsonFilmList(json);
        else if (c.getSimpleName().equals("UserLists"))
            return jsonUserLists(json);
        else if (c.getSimpleName().equals("Reviews"))
            return jsonReviews(json);
        else if (c.getSimpleName().equals("Contact"))
            return jsonContact(json);
        return "";
    }
}
