package com.gg4703.toolkit.support;

import java.util.List;

public class ExcelSheet {
    private String sheetName;
    private String[] headers;
    private List<String[]> datasList;

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String[] getHeaders() {
        return headers;
    }

    public void setHeaders(String[] headers) {
        this.headers = headers;
    }

    public List<String[]> getDatasList() {
        return datasList;
    }

    public void setDatasList(List<String[]> datasList) {
        this.datasList = datasList;
    }

}

