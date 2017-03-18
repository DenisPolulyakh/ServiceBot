package com.neznayka.www.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Polulyakh Denis
 *
 */
public class ResponseData {
    String status;
    String offset;
    String records;
    String total;
    List<DictionaryData> rows;

    public ResponseData(String status, String  offset, String records, String total, List<DictionaryData> rows) {
        this.status = status;
        this. offset =  offset;
        this.records = records;
        this.total = total;
        this.rows = rows;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOffset() {
        return  offset;
    }

    public void setOffset(String  offset) {
        this. offset =  offset;
    }

    public String getRecords() {
        return records;
    }

    public void setRecords(String records) {
        this.records = records;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<DictionaryData> getRows() {
        return rows;
    }

    public void setRows(List<DictionaryData> rows) {
        this.rows = rows;
    }
}
