package com.github.m1rr0r.visualization.dataStructure;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChartColumns {
    private List<AggregationColumn> aggregationColumns;
    private CategoryColumn categoryColumn;
    private List<FilterColumn> filterColumns;
    private MeasureColumn measureColumn;
    private MeasurementColumn measurementColumn;

    public void setAggregationColumns(List<AggregationColumn> aggregationColumns) {
        this.aggregationColumns = aggregationColumns;
    }

    public void setCategoryColumn(CategoryColumn categoryColumn) {
        this.categoryColumn = categoryColumn;
    }
    public void setFilterColumns(List<FilterColumn> filterColumns) {
        this.filterColumns = filterColumns;
    }
    public void setMeasureColumn(MeasureColumn measureColumn) {
        this.measureColumn = measureColumn;
    }
    public void setMeasurementColumn(MeasurementColumn measurementColumn) {
        this.measurementColumn = measurementColumn;
    }
    
    public void addAggregationColumn(AggregationColumn aggregationColumn) {
        this.aggregationColumns.add(aggregationColumn);
    }
    public List<AggregationColumn> getAggregationColumns() {
        return this.aggregationColumns;
    }

    public void addCategoryColumn(CategoryColumn categoryColumn) {
        this.categoryColumn = categoryColumn;
    }
    public CategoryColumn getCategoryColumn() {
        return this.categoryColumn;
    }

    public void addFilterColumn(FilterColumn filterColumn) {
        this.filterColumns.add(filterColumn);
    }
    public List<FilterColumn> getFilterColumns() {
        return this.filterColumns;
    }

    public void addMeasureColumn(MeasureColumn measureColumn) {
        this.measureColumn = measureColumn;
    }
    public MeasureColumn getMeasureColumn() {
        return this.measureColumn;
    }

    public void addMeasurementColumn(MeasurementColumn measurementColumn) {
        this.measurementColumn = measurementColumn;
    }
    public MeasurementColumn getMeasurementColumn() {
        return this.measurementColumn;
    }
}
