package com.github.m1rr0r.visualization.sourcesConnections.jdbc.request;

import com.github.m1rr0r.visualization.dataStructure.ChartColumns;
import com.github.m1rr0r.visualization.dataStructure.MeasureColumn;
import com.github.m1rr0r.visualization.sourcesConnections.jdbc.request.sqlTransforms.CategorySqlTransform;
import com.github.m1rr0r.visualization.sourcesConnections.jdbc.request.sqlTransforms.FilterSqlTransform;
import com.github.m1rr0r.visualization.sourcesConnections.jdbc.request.sqlTransforms.MeasureSqlTransform;
import com.github.m1rr0r.visualization.sourcesConnections.jdbc.request.sqlTransforms.MeasurementSqlTransform;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RequestChartData extends Request {
    private ChartColumns chartColumns;
    private String tableName;

    public void setChartColumns(ChartColumns chartColumns) {
        this.chartColumns = chartColumns;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    //not implemented
    public String getAggregationStatement() {
        return null;
    }
    public String getCategoryStatement() {
        return (new CategorySqlTransform()).transformColumn(this.chartColumns.getCategoryColumn());
    }
    public String getFilterStatement() {
        FilterSqlTransform filterSqlTransform = new FilterSqlTransform();
        String filterStatement;
        if(this.chartColumns.getFilterColumns().isEmpty()) filterStatement = "1=1";
        else filterStatement = this.chartColumns.getFilterColumns().stream().map(filterSqlTransform::transformColumn)
                .collect(Collectors.joining(" and "));
        return filterStatement;
    }
    public String getMeasureStatement() {
        MeasureSqlTransform measureSqlTransform = new MeasureSqlTransform();
        CategorySqlTransform categorySqlTransform = new CategorySqlTransform();
        MeasureColumn measureColumn = chartColumns.getMeasureColumn();
        if(measureColumn == null) return categorySqlTransform.transformColumn(chartColumns.getCategoryColumn());
        return String.join(",",
                categorySqlTransform.transformColumn(chartColumns.getCategoryColumn()),
                measureSqlTransform.transformColumn(chartColumns.getMeasureColumn()));
    }

    public String getMeasurementStatement() {
        MeasurementSqlTransform measurementSqlTransform = new MeasurementSqlTransform();
        return measurementSqlTransform.transformColumn(this.chartColumns.getMeasurementColumn());
    }

    public String getTableStatement() {
        return this.tableName;
    }

    public String getScript() {
        super.setScript(
        "select "
            + getMeasureStatement()
            + "," + getMeasurementStatement()
            + " from " + getTableStatement()
            + " where " + getFilterStatement()
            + " group by " + getMeasureStatement()
            + " order by " + getCategoryStatement()
        );
        return super.getScript();
    }
}