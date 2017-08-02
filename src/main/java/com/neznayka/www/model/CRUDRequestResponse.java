package com.neznayka.www.model;


import com.neznayka.www.hibernate.Message;

import java.util.List;

public class CRUDRequestResponse {
    private Pager pager;
    private String status;
    private Message message;
    private int id;
    private List<Message> rows;

    public CRUDRequestResponse() {
    }

    public CRUDRequestResponse(String status) {
        this.status = status;
    }

    public CRUDRequestResponse(Message message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public List<Message> getRows() {
        return rows;
    }

    public void setRows(List<Message> rows) {
        this.rows = rows;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }
}
