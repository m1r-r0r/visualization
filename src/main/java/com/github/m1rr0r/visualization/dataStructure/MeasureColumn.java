package com.github.m1rr0r.visualization.dataStructure;

public class MeasureColumn extends Column {
    public MeasureColumn() {};
    public MeasureColumn(Column column) {
        this.setName(column.getName());
        this.setType(column.getType());
    }
}
