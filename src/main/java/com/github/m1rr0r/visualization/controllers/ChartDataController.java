package com.github.m1rr0r.visualization.controllers;

import com.github.m1rr0r.visualization.dataStructure.ChartColumns;
import com.github.m1rr0r.visualization.services.chartScript.ChartDataGenerator;
import com.github.m1rr0r.visualization.services.chartScript.ChartScriptGenerator;
import com.github.m1rr0r.visualization.services.postParsing.ChartParamsGenerator;
import com.github.m1rr0r.visualization.services.postParsing.SelectedColumnsGenerator;
import com.github.m1rr0r.visualization.sourcesConnections.DataSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.sql.SQLException;

@Controller
public class ChartDataController {
    public static DataSource chartDataSource;
    private ChartParamsGenerator chartParamsGenerator;
    public static ChartColumns chartColumns;
    public static SelectedColumnsGenerator selectedColumnsGenerator;
    private ChartDataGenerator chartDataGenerator;
    private ChartScriptGenerator chartScriptGenerator;

    @PostMapping("/visualization")
    public String sendDataToChart(Model model, @RequestParam String paramsToPost) throws SQLException, ClassNotFoundException, IOException {
        chartParamsGenerator = new ChartParamsGenerator();
        chartParamsGenerator.generateChartParams(paramsToPost);
        chartColumns = chartParamsGenerator.getChartColumns();
        chartDataSource.setChartColumns(chartColumns);
        chartDataSource.open();

        chartDataGenerator = new ChartDataGenerator();
        chartDataGenerator.setChartColumns(chartColumns);
        chartDataGenerator.setDataSource(chartDataSource);
        chartDataGenerator.generateData();

        chartScriptGenerator = new ChartScriptGenerator();
        chartScriptGenerator.setChartColumns(chartColumns);
        chartScriptGenerator.setChartType(chartParamsGenerator.getChartType());
        chartScriptGenerator.setChartData(chartDataGenerator.getChartData());
        chartScriptGenerator.setMeasureSet(chartDataGenerator.getMeasureSet());
        chartScriptGenerator.generateChartScript();

        model.addAttribute("jsChartCode", chartScriptGenerator.getChartScript());
        model.addAttribute("paramsToPost", paramsToPost);
        model.addAttribute("selectedColumnNames", selectedColumnsGenerator.getSelectedColumnNames());
        chartDataSource.close();
        return "visualization";
    }
}
