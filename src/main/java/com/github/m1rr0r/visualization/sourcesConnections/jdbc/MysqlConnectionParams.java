package com.github.m1rr0r.visualization.sourcesConnections.jdbc;

import com.github.m1rr0r.visualization.sourcesConnections.ConnectionParams;

public class MysqlConnectionParams extends ConnectionParams {
    private String DBNAME;
    private String USERNAME;
    private String PASSWORD;

    public String getDBNAME() {
        return DBNAME;
    }

    public void setDBNAME(String DBNAME) {
        this.DBNAME = DBNAME;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }
}
