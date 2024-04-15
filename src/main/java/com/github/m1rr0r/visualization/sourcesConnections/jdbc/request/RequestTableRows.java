package com.github.m1rr0r.visualization.sourcesConnections.jdbc.request;

public class RequestTableRows extends Request {
    private String tableName;
    private int rowsLimit;

    public RequestTableRows(String tableName, int rowsLimit) {
        this.rowsLimit = rowsLimit;
        this.tableName = tableName;
        this.setScript("select * from " + tableName + " limit " + rowsLimit);
    }

    public RequestTableRows() {
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setRowsLimit(int rowsLimit) {
        this.rowsLimit = rowsLimit;
    }

    public String getScript() {
        return "select * from " + tableName + " limit " + rowsLimit;
    }
}