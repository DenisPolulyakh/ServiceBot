package com.neznayka.www.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Денис on 18.03.2017.
 */
public class DictionaryData {
    int id;
    List<String> tags;
    String message;


    public DictionaryData() {
    }

    public DictionaryData(int id, List tags, String message) {
        this.id = id;
        this.tags = tags;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getTags() {
        return tags;
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
