package com.github.m1rr0r.visualization.sourcesConnections;

import com.github.m1rr0r.visualization.dataStructure.ChartColumns;
import com.github.m1rr0r.visualization.dataStructure.Column;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.Closeable;
import java.sql.SQLException;
import java.util.List;

import static org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS;

@Component
public interface DataSource extends Closeable {
    List<Column> getColumnsMeta() throws SQLException;
    String getColumnValue(Column column) throws SQLException;
    boolean getNextRow() throws SQLException;
    void open() throws SQLException, ClassNotFoundException;
    void setChartColumns(ChartColumns chartColumns);
}