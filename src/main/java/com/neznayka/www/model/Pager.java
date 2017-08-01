package com.neznayka.www.model;

public class Pager {
    private int offset;
    private int records;
    private int total;

    public Pager() {
    }

    public Pager(int offset, int records, int total) {
        this.offset = offset;
        this.records = records;
        this.total = total;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getRecords() {
        return records;
    }

    public void setRecords(int records) {
        this.records = records;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
