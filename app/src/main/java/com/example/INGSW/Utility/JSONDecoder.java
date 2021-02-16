package com.example.INGSW.Utility;

import com.example.INGSW.Component.ListOfFilm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

public class JSONDecoder {

    private static final ObjectMapper mapper = new ObjectMapper();

    private static List<ListOfFilm> jsonFilmList(String json) throws JsonProcessingException {
        return Arrays.asList(mapper.readValue(json, ListOfFilm[].class));
    }

    public static Object getJsonToDecode(String json) throws JsonProcessingException {
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        return jsonFilmList(json);
    }
}
