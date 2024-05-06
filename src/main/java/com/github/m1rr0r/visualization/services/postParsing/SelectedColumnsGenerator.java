package com.github.m1rr0r.visualization.services.postParsing;

import com.github.m1rr0r.visualization.dataStructure.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class SelectedColumnsGenerator {
    private String selectedColumns;

    public void setSelectedColumns(String selectedColumns) {
        this.selectedColumns = selectedColumns;
    }

    public List<Column> getSelectedColumnsList() {
        return Arrays.stream(selectedColumns.replaceAll("column=","").split("&"))
            .map(e -> {
                Column column = new Column();
                column.setName(e);
                return  column;})
            .toList();
    }

    public String[] getSelectedColumnNames() {
        return getSelectedColumnsList().stream().map(Column::getName).toArray(String[]::new);
    }
}