package com.github.m1rr0r.visualization.sourcesConnections.jdbc.request.sqlTransforms;

import com.github.m1rr0r.visualization.dataStructure.Column;
import com.github.m1rr0r.visualization.dataStructure.Transform;

public class ColumnSqlTransform implements Transform<String, Column> {
    @Override
    public String transformColumn(Column column) {
        return column.getName();
    }
}