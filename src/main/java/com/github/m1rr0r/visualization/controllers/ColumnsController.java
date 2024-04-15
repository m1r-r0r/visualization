package com.github.m1rr0r.visualization.controllers;

import com.github.m1rr0r.visualization.dataStructure.Column;
import com.github.m1rr0r.visualization.services.postParsing.SelectedColumnsGenerator;
import com.github.m1rr0r.visualization.sourcesConnections.DataSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.github.m1rr0r.visualization.controllers.ChartDataController.selectedColumnsGenerator;

@Controller
public class ColumnsController {
    public static DataSource initDataSource;


    @GetMapping("/show-fields")
    public String showFields(Model model) throws SQLException, ClassNotFoundException, IOException {
        initDataSource.open();
        List<Column> columnList = initDataSource.getColumnsMeta();
        int columnCount  = columnList.size();
        //column counter for thymeleaf
        int[] columnCounter = IntStream.range(0, columnCount).toArray();
        //fields and meta
        String[] columnNames = columnList.stream().map(Column::getName).toArray(String[]::new);
        String[] columnTypes = columnList.stream().map(Column::getType).toArray(String[]::new);
        List<String[]> rowExample = new ArrayList<>();
        //getExamples
        while(initDataSource.getNextRow()) {
            String[] rowValues = new String[columnList.size()];
            rowExample.add(rowValues);
            for(Column column : columnList) {
                rowValues[columnList.indexOf(column)] = initDataSource.getColumnValue(column);
            }
        }
        initDataSource.close();
        //send
        model.addAttribute("columnsName", columnNames);
        model.addAttribute("columnsType", columnTypes);
        model.addAttribute("rowsExample", rowExample);
        model.addAttribute("columnCounter", columnCounter);
        return "show-fields";
    }

    @PostMapping("/show-fields")
    public String getSelectedColumns(@RequestBody String selectedColumns, Model model) {
        selectedColumnsGenerator = new SelectedColumnsGenerator();
        selectedColumnsGenerator = new SelectedColumnsGenerator();
        selectedColumnsGenerator.setSelectedColumns(selectedColumns);
        return "redirect:/visualization";
    }

    @GetMapping("/visualization")
    public String chartParamsInit(Model model) {
        model.addAttribute("selectedColumnNames", selectedColumnsGenerator.getSelectedColumnNames());
        model.addAttribute("paramsToPost", "none");
        return "visualization";
    }
}
