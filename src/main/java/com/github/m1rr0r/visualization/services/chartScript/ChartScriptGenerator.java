package com.github.m1rr0r.visualization.services.chartScript;

import com.github.m1rr0r.visualization.dataStructure.ChartColumns;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Set;

@Component
public class ChartScriptGenerator {
    private String chartScript;
    private String chartData;
    private String chartType;
    private ChartColumns chartColumns;
    private Set<String> measureSet;

    public void setChartType(String chartType) {
        this.chartType = chartType;
    }
    public void setChartData(String chartData) {
        this.chartData = chartData;
    }
    public void setMeasureSet(Set<String> measureSet) {
        this.measureSet = measureSet;
    }
    public void setChartColumns(ChartColumns chartColumns) {
        this.chartColumns = chartColumns;
    }

    public String getChartScript() {
        return chartScript;
    }

    public void generateChartScript() throws SQLException {
        String categoryColumnName = chartColumns.getCategoryColumn().getName();

        StringBuilder chartScriptBuilder = new StringBuilder();
        if(!chartData.isEmpty()) {
            chartScriptBuilder.append("am5.ready(function() {var root=am5.Root.new('chartdiv');\n");
            chartScriptBuilder.append("var chart=root.container.children.push(am5xy.XYChart.new(root, " +
                    "{paddingLeft: 0,layout: root.verticalLayout}));\n");
            chartScriptBuilder.append(chartData).append("\n");
            chartScriptBuilder.append("var xAxis = chart.xAxes.push(am5xy.CategoryAxis.new(root, {categoryField: '");
            chartScriptBuilder.append(categoryColumnName).append("',renderer: am5xy.AxisRendererX.new(root, {})," +
                    "tooltip: am5.Tooltip.new(root, {})}));\n");
            chartScriptBuilder.append("xAxis.data.setAll(data);var yAxis = chart.yAxes.push(am5xy.ValueAxis.new(root, " +
                    "{min: 0, extraMax: 0.1, renderer: am5xy.AxisRendererY.new(root, {})}));\n");

            for(String measure: measureSet) {
                chartScriptBuilder.append("var series_").append(measure).append("= chart.series.push(am5xy.");
                if(chartType.equals("line")) chartScriptBuilder.append("LineSeries");
                else chartScriptBuilder.append("ColumnSeries");
                chartScriptBuilder.append(".new(root, {name: '");

                chartScriptBuilder.append(measure).append("'");
                chartScriptBuilder.append(",xAxis: xAxis,yAxis: yAxis,valueYField: '");
                chartScriptBuilder.append(measure).append("'");
                chartScriptBuilder.append(",categoryXField: '").append(categoryColumnName).append("'");
                chartScriptBuilder.append(",tooltip: am5.Tooltip.new(root, {pointerOrientation: 'horizontal', labelText: " +
                        "'{name} in {categoryX}: {valueY}'})}));\n");
                if(chartType.equals("line")) chartScriptBuilder.append("series_").append(measure).append(".strokes.template." +
                        "setAll({strokeWidth: 3, templateField: 'strokeSettings'});\n");
                else chartScriptBuilder.append("series_").append(measure).append(".columns.template.setAll({tooltipY: am5.percent(10)," +
                        "templateField: 'columnSettings'});\n");
                chartScriptBuilder.append("series_").append(measure).append(".data.setAll(data);");
                if(chartType.equals("line")) {
                    chartScriptBuilder.append("series_").append(measure).append(".bullets." +
                            "push(function () {return am5.Bullet.new(root, {sprite: am5.Circle.new(root, ");
                    chartScriptBuilder.append("{strokeWidth: 3,stroke: ");
                    chartScriptBuilder.append("series_").append(measure).append(".get('stroke'),radius: 5,fill: " +
                            "root.interfaceColors.get('background')})});});\n");
                }
                chartScriptBuilder.append("chart.set('cursor', am5xy.XYCursor.new(root, {})); var legend = " +
                        "chart.children.push(am5.Legend.new(root,{centerX: am5.p50,x: am5.p50}));\n");
            }
            chartScriptBuilder.append("chart.set('cursor', am5xy.XYCursor.new(root, {})); var legend = " +
                    "chart.children.push(am5.Legend.new(root,{centerX: am5.p50,x: am5.p50}));\n");
            chartScriptBuilder.append("legend.data.setAll(chart.series.values);});\n");
        }
        if(!chartData.isEmpty()) chartScript = chartScriptBuilder.toString();
        else chartScript = null;
    }
}
