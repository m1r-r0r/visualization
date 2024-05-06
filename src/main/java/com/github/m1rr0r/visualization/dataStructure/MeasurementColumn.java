package com.github.m1rr0r.visualization.dataStructure;

import org.springframework.stereotype.Component;

@Component
public class MeasurementColumn extends Column {
    public MeasurementColumn() {};
    public MeasurementColumn(Column column) {
        this.setName(column.getName());
        this.setType(column.getType());
    }
}