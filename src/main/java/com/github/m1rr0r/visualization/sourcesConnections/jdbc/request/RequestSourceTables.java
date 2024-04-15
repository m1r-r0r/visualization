package com.github.m1rr0r.visualization.sourcesConnections.jdbc.request;

public class RequestSourceTables extends Request {
    public RequestSourceTables() {
        this.setScript("show tables");
    }
}
