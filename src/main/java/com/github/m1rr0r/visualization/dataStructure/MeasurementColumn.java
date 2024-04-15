package com.github.m1rr0r.visualization.dataStructure;

public class MeasurementColumn extends Column {
    public MeasurementColumn() {};
    public MeasurementColumn(Column column) {
        this.setName(column.getName());
        this.setType(column.getType());
    }
}