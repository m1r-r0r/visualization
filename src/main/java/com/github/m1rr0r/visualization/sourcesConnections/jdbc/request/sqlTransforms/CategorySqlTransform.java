package com.github.m1rr0r.visualization.sourcesConnections.jdbc.request.sqlTransforms;

import com.github.m1rr0r.visualization.dataStructure.CategoryColumn;
import com.github.m1rr0r.visualization.dataStructure.Transform;

public class CategorySqlTransform implements Transform<String, CategoryColumn> {
    @Override
    public String transformColumn(CategoryColumn column) {
        return column.getName();
    }
}
