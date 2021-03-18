package com.example.INGSW.Utility;

import com.example.INGSW.Component.DB.Classes.Notify;
import com.example.INGSW.Component.DB.Classes.UserLists;
import com.example.INGSW.Component.Films.Film;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

public class JSONDecoder {

    private static final ObjectMapper mapper = new ObjectMapper();

    private static List<Film> jsonFilmList(String json) throws JsonProcessingException {
        return Arrays.asList(mapper.readValue(json, Film[].class));
    }

    private static List<Notify> jsonNotify(String json) throws JsonProcessingException{
        return Arrays.asList(mapper.readValue(json,Notify[].class));
    }

    private static List<UserLists> jsonUserLists(String json) throws JsonProcessingException{
        return Arrays.asList(mapper.readValue(json,UserLists[].class));
    }

    public static Object getJsonToDecode(String json,Class c) throws JsonProcessingException {
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        if (c.getSimpleName().equals("Notify"))
            return jsonNotify(json);
        else if (c.getSimpleName().equals("Film"))
            return jsonFilmList(json);
        else if (c.getSimpleName().equals("UserLists"))
            return jsonUserLists(json);
        return "";
    }
}
