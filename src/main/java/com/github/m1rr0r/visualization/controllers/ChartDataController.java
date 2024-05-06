package com.github.m1rr0r.visualization.controllers;

import com.github.m1rr0r.visualization.dataStructure.ChartColumns;
import com.github.m1rr0r.visualization.services.chartScript.ChartDataGenerator;
import com.github.m1rr0r.visualization.services.chartScript.ChartScriptGenerator;
import com.github.m1rr0r.visualization.services.postParsing.ChartParamsGenerator;
import com.github.m1rr0r.visualization.services.postParsing.SelectedColumnsGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.sql.SQLException;

@Controller
@Lazy
public class ChartDataController {
    @Autowired
    private ChartParamsGenerator chartParamsGenerator;
    @Autowired
    public SelectedColumnsGenerator selectedColumnsGenerator;
    @Autowired
    private ChartDataGenerator chartDataGenerator;
    @Autowired
    private ChartScriptGenerator chartScriptGenerator;

    @PostMapping("/visualization")
    public String sendDataToChart(Model model, @RequestParam String paramsToPost) throws SQLException, ClassNotFoundException, IOException {
        chartParamsGenerator.generateChartParams(paramsToPost);
        ChartColumns chartColumns = chartParamsGenerator.getChartColumns();
        chartDataGenerator.setChartColumns(chartColumns);
        chartDataGenerator.generateData();

        chartScriptGenerator.setChartColumns(chartColumns);
        chartScriptGenerator.setChartType(chartParamsGenerator.getChartType());
        chartScriptGenerator.setChartData(chartDataGenerator.getChartData());
        chartScriptGenerator.setMeasureSet(chartDataGenerator.getMeasureSet());
        chartScriptGenerator.generateChartScript();

        model.addAttribute("jsChartCode", chartScriptGenerator.getChartScript());
        model.addAttribute("paramsToPost", paramsToPost);
        model.addAttribute("selectedColumnNames", selectedColumnsGenerator.getSelectedColumnNames());
        return "visualization";
    }
}
