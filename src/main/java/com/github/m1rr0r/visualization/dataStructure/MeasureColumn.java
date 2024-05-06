package com.github.m1rr0r.visualization.dataStructure;

import org.springframework.stereotype.Component;

@Component
public class MeasureColumn extends Column {
    public MeasureColumn() {};
    public MeasureColumn(Column column) {
        this.setName(column.getName());
        this.setType(column.getType());
    }
}
