package com.github.m1rr0r.visualization.sourcesConnections.jdbc;

import com.github.m1rr0r.visualization.dataStructure.ChartColumns;
import com.github.m1rr0r.visualization.dataStructure.Column;
import com.github.m1rr0r.visualization.sourcesConnections.DataSource;
import com.github.m1rr0r.visualization.sourcesConnections.jdbc.request.Request;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

public class MysqlDataSource implements DataSource {
    private MysqlConnection connection;
    private Request request;
    private Statement statement;
    private ResultSet resultSet;
    private ResultSetMetaData resultSetMetaData;
    private HashMap<String, String> meta;
    private ChartColumns chartColumns;

    public void open() throws SQLException, ClassNotFoundException {
        this.statement = this.connection.getConnection().createStatement();
        if(resultSet != null) resultSet.close();
        this.resultSet = this.statement.executeQuery(request.getScript());
        this.resultSetMetaData = this.resultSet.getMetaData();
    }

    @Override
    public void setChartColumns(ChartColumns chartColumns) {
        this.chartColumns = chartColumns;
        this.request.setChartColumns(chartColumns);
    }

    public ResultSet getResultSet() {
        return this.resultSet;
    }

    public void setConnection(MysqlConnection connection) {
        this.connection = connection;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    @Override
    public void close() throws IOException {
        try {
            this.resultSet.close();
            this.statement.close();
            this.connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Column> getColumnsMeta() throws SQLException {
        List<Column> columns = new ArrayList<>();
        int columnsCount = this.resultSetMetaData.getColumnCount();
        IntStream.range(1, columnsCount+1).forEach(i -> {
            try {
                Column column = new Column();
                column.setName(this.resultSetMetaData.getColumnName(i));
                column.setType(this.resultSetMetaData.getColumnTypeName(i));
                columns.add(column);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        return columns;
    }

    @Override
    public String getColumnValue(Column column) throws SQLException {
        return this.resultSet.getString(column.getName());
    }
    public String getColumnValue(int columnNumber) throws SQLException {
        return this.resultSet.getString(columnNumber);
    }

    @Override
    public boolean getNextRow() throws SQLException {
        return this.resultSet.next();
    }

    public Request getRequest() {
        return request;
    }
}