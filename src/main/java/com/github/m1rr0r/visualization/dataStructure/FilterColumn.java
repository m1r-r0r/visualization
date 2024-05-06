package com.github.m1rr0r.visualization.dataStructure;

import org.springframework.stereotype.Component;

@Component
public class FilterColumn extends Column {
    private String minValue;
    private String maxValue;

    public FilterColumn() {};
    public FilterColumn(Column column) {
        this.setName(column.getName());
        this.setType(column.getType());
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }
    public String getMinValue() {
        return this.minValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }
    public String getMaxValue() {
        return this.maxValue;
    }
}