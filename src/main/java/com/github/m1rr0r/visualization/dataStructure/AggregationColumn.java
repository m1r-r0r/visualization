package com.github.m1rr0r.visualization.dataStructure;

import org.springframework.stereotype.Component;

@Component
public class AggregationColumn extends Column{

    private AggregationType aggregationType;

    public AggregationColumn() {};

    public AggregationColumn(Column column) {
        this.setName(column.getName());
        this.setType(column.getType());
    }

    public AggregationType getAggregationType() {
        return this.aggregationType;
    }

    public void setAggregationType(AggregationType aggregationType) {
        this.aggregationType = aggregationType;
    }
}
