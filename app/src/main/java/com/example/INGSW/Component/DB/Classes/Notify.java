package com.example.INGSW.Component.DB.Classes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Notify {

    private int id_Notify;
    private String id_sender;
    private String id_receiver;
    private String type;
    private int id_recordref;
    private String state;
    private long dateOfSend;

    @JsonCreator
    public Notify(@JsonProperty("id_Notify") int id_Notify,
                  @JsonProperty("id_sender") String id_sender,
                  @JsonProperty("id_receiver") String id_receiver,
                  @JsonProperty("type") String type,
                  @JsonProperty("id_recordref") int id_recordref,
                  @JsonProperty("status") String status,
                  @JsonProperty("dateOfSend") long dateOfSend) {
        this.id_Notify=id_Notify;
        this.id_sender=id_sender;
        this.id_receiver=id_receiver;
        this.type=type;
        this.id_recordref=id_recordref;
        this.state=status;
        this.dateOfSend=dateOfSend;
    }

    public Notify(@JsonProperty("type") String type){
        this.type=type;
    }

    public int getId_Notify() {
        return id_Notify;
    }

    public void setId_Notify(int id_Notify) {
        this.id_Notify = id_Notify;
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

    public long getDateOfSend() {
        return dateOfSend;
    }

    public void setDateOfSend(long dateOfSend) {
        this.dateOfSend = dateOfSend;
    }
}
