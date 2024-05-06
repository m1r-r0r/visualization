package com.github.m1rr0r.visualization.services.chartScript;

import com.github.m1rr0r.visualization.dataStructure.*;
import com.github.m1rr0r.visualization.services.postParsing.ChartParamsGenerator;
import com.github.m1rr0r.visualization.sourcesConnections.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

@Component
@Lazy
public class ChartDataGenerator {
    private DataSource dataSource;
    private Set<String> measureSet;
    private String chartData;
    private ChartColumns chartColumns;
    @Autowired
    private ChartParamsGenerator chartParamsGenerator;

    @Autowired
    @Qualifier("chartDataSource")
    @Lazy
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setChartColumns(ChartColumns chartColumns) {
        this.chartColumns = chartColumns;
    }

    public String getChartData() {
        return chartData;
    }

    public Set<String> getMeasureSet() {
        return measureSet;
    }

    public void generateData() throws SQLException, ClassNotFoundException, IOException {
//        ChartColumns chartColumns = chartParamsGenerator.getChartColumns();
        dataSource.setChartColumns(chartColumns);
        dataSource.open();
        StringBuilder dataBuilder = new StringBuilder();
        measureSet = new LinkedHashSet<>();

        String upperRowCategoryValue = null;
        while(dataSource.getNextRow()) {
            CategoryColumn categoryColumn = chartColumns.getCategoryColumn();
            String categoryValue = dataSource.getColumnValue(categoryColumn);
            if(!categoryValue.equals(upperRowCategoryValue)) {
                if(upperRowCategoryValue != null) dataBuilder.append("},{");
                upperRowCategoryValue = categoryValue;
                String categoryColumnName = categoryColumn.getName();
                dataBuilder.append(categoryColumnName).append(":'").append(categoryValue).append("'");
            }

            String measureValue;
            MeasureColumn measureColumn = chartColumns.getMeasureColumn();
            if(measureColumn == null) measureValue = chartColumns.getMeasurementColumn().getName();
            else measureValue = dataSource.getColumnValue(measureColumn);

            String measurementValue;
            MeasurementColumn measurementColumn = chartColumns.getMeasurementColumn();
            measurementValue = dataSource.getColumnValue(measurementColumn);
            dataBuilder.append(",").append(measureValue).append(":").append(measurementValue);

            measureSet.add(measureValue);
        }
        dataSource.close();
        if(!dataBuilder.isEmpty()) chartData = "var data = [{" + dataBuilder + "}];";
        else chartData = null;
    }
}
