package com.github.m1rr0r.visualization.services.postParsing;

import com.github.m1rr0r.visualization.dataStructure.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ChartParamsGenerator {
    private String chartType;
    @Autowired
    private ChartColumns chartColumns;

    public void generateChartParams(String postChartColumns) {
//        chartColumns = new ChartColumns();
        String[] documentBlocks = postChartColumns.split("&block=");

        chartType = documentBlocks[1].split("&el=")[1].split("&attr=")[1];

        List<FilterColumn> filterColumns = new ArrayList<>();
        List<String[]> filterBlock = Arrays.stream(documentBlocks[2].split("&el=")).skip(1)
                .map(e -> e.split("&attr=")).toList();
            if(!filterBlock.isEmpty()) filterColumns = filterBlock.stream().map(e -> {
                FilterColumn filterColumn = new FilterColumn();
                filterColumn.setName(e[0]);
                filterColumn.setMinValue(e[2]);
                filterColumn.setMaxValue(e[3]);
                return filterColumn;
            }).toList();
            chartColumns.setFilterColumns(filterColumns);

        MeasurementColumn measurementColumn = Arrays.stream(documentBlocks[3].split("&el=")).skip(1)
            .map(e -> e.split("&attr="))
            .map(e -> {
                MeasurementColumn column = new MeasurementColumn();
                column.setName(e[0]);
                return column;})
            .findFirst().orElse(null);
            chartColumns.setMeasurementColumn(measurementColumn);

        MeasureColumn measureColumn = Arrays.stream(documentBlocks[4].split("&el=")).skip(1)
            .map(e -> e.split("&attr="))
            .map(e -> {
                MeasureColumn column = new MeasureColumn();
                column.setName(e[0]);
                return column;})
            .findFirst().orElse(null);
            chartColumns.setMeasureColumn(measureColumn);

        CategoryColumn categoryColumn = Arrays.stream(documentBlocks[5].split("&el=")).skip(1)
            .map(e -> e.split("&attr="))
            .map(e -> {
                CategoryColumn column = new CategoryColumn();
                column.setName(e[0]);
                return column;
            })
            .findFirst().orElse(null);
            chartColumns.setCategoryColumn(categoryColumn);
    }

    public String getChartType() {
        return chartType;
    }

    public ChartColumns getChartColumns() {
        return chartColumns;
    }
}