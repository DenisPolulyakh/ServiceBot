package com.neznayka.www.model;

import java.util.ArrayList;
import java.util.List;


public class DictionaryMap {
    private int id;
    private List<String> tags;
    private String message;

    public DictionaryMap() {
    }

    public DictionaryMap(ArrayList tags, String message) {
        this.tags = tags;
        this.message = message;
    }

    public List<String> getTags() {
        return tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTags(List<String> tags) {

        this.tags = tags;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
