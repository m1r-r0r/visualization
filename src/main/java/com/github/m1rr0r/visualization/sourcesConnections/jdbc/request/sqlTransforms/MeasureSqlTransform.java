package com.github.m1rr0r.visualization.sourcesConnections.jdbc.request.sqlTransforms;

import com.github.m1rr0r.visualization.dataStructure.MeasureColumn;
import com.github.m1rr0r.visualization.dataStructure.Transform;

public class MeasureSqlTransform implements Transform<String, MeasureColumn> {
    @Override
    public String transformColumn(MeasureColumn column) {
        return column.getName();
    }
}
