package com.github.m1rr0r.visualization.dataStructure;

import org.springframework.stereotype.Component;

@Component
public class CategoryColumn extends Column {
    public CategoryColumn() {};
    public CategoryColumn(Column column) {
        this.setName(column.getName());
        this.setType(column.getType());
    }

}
