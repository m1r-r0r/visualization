package com.github.m1rr0r.visualization.sourcesConnections.jdbc.request;

import com.github.m1rr0r.visualization.dataStructure.ChartColumns;

public abstract class Request {
    private String script;
    public String getScript() {
        return this.script;
    }
    public void setScript(String script) {
        this.script = script;
    }
    public void setChartColumns(ChartColumns chartColumns) {};
}