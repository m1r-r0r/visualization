package com.github.m1rr0r.visualization.sourcesConnections.jdbc.request.sqlTransforms;

import com.github.m1rr0r.visualization.dataStructure.MeasurementColumn;
import com.github.m1rr0r.visualization.dataStructure.Transform;

public class MeasurementSqlTransform implements Transform<String, MeasurementColumn> {
    @Override
    public String transformColumn(MeasurementColumn column) {
        return "sum(" + column.getName() + ") as " + column.getName();
    }
}
