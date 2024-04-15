package com.github.m1rr0r.visualization.sourcesConnections.jdbc.request.sqlTransforms;

import com.github.m1rr0r.visualization.dataStructure.FilterColumn;
import com.github.m1rr0r.visualization.dataStructure.Transform;

public class FilterSqlTransform implements Transform<String, FilterColumn> {
    @Override
    public String transformColumn(FilterColumn column) {
        return column.getName()
                + " between '" + column.getMinValue()
                + "' and '" + column.getMaxValue() + "'";
    }
}
