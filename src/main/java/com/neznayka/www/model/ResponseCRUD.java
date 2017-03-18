package com.neznayka.www.model;


import java.util.List;

public class ResponseCRUD {
    private String status;
    private String message;
    private List<String> tags;
    private int id;

    public ResponseCRUD(String status, String message, List<String> tags, int id) {
        this.status = status;
        this.message = message;
        this.tags = tags;
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
