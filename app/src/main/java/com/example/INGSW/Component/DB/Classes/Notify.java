package com.example.INGSW.Component.DB.Classes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Notify {

    int id_notify;
    String id_sender;
    String id_receiver;
    String type;
    int id_recordref;
    String state;

    @JsonCreator
    public Notify(@JsonProperty("id_notify") int id_notify,
                  @JsonProperty("id_sender") String id_sender,
                  @JsonProperty("id_receiver") String id_receiver,
                  @JsonProperty("type") String type,
                  @JsonProperty("id_recordref") int id_recordref,
                  @JsonProperty("status") String status) {
        this.id_notify=id_notify;
        this.id_sender=id_sender;
        this.id_receiver=id_receiver;
        this.type=type;
        this.id_recordref=id_recordref;
        this.state=status;

    }

    public Notify(@JsonProperty("type") String type){
        this.type=type;
    }

    public int getId_notify() {
        return id_notify;
    }

    public void setId_notify(int id_notify) {
        this.id_notify = id_notify;
    }

    public String getId_sender() {
        return id_sender;
    }

    public void setId_sender(String id_sender) {
        this.id_sender = id_sender;
    }

    public String getId_receiver() {
        return id_receiver;
    }

    public void setId_receiver(String id_receiver) {
        this.id_receiver = id_receiver;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId_recordref() {
        return id_recordref;
    }

    public void setId_recordref(int id_recordref) {
        this.id_recordref = id_recordref;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
