package com.github.m1rr0r.visualization.controllers;

import com.github.m1rr0r.visualization.sourcesConnections.jdbc.MysqlConnection;
import com.github.m1rr0r.visualization.sourcesConnections.jdbc.MysqlConnectionParams;
import com.github.m1rr0r.visualization.sourcesConnections.jdbc.MysqlDataSource;
import com.github.m1rr0r.visualization.sourcesConnections.jdbc.request.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.github.m1rr0r.visualization.controllers.ChartDataController.chartDataSource;
import static com.github.m1rr0r.visualization.controllers.ColumnsController.initDataSource;

@Controller
public class DataSourceController {
    private MysqlConnectionParams connectionParams;
    private RequestChartData requestChartData;

    @GetMapping("/new-mysql-connection")
    public String newMysqlConnection() {
        MysqlConnection connection = new MysqlConnection();
        connectionParams = new MysqlConnectionParams();
        connection.setConnectionParams(connectionParams);

        //tables & fields select dataSource
        initDataSource = new MysqlDataSource();
        ((MysqlDataSource)initDataSource).setConnection(connection);
        ((MysqlDataSource)initDataSource).setRequest(new RequestSourceTables());

        //chartData dataSet
        requestChartData = new RequestChartData();

        chartDataSource = new MysqlDataSource();
        ((MysqlDataSource)chartDataSource).setRequest(requestChartData);
        ((MysqlDataSource)chartDataSource).setConnection(connection);
        return "new-mysql-connection";
    }

    @PostMapping("/new-mysql-connection")
    public String setMysqlConnection(@RequestParam String password,
                                @RequestParam String username,
                                @RequestParam String db_name) {
        connectionParams.setPASSWORD(password);
        connectionParams.setUSERNAME(username);
        connectionParams.setDBNAME(db_name);
        return "redirect:/show-mysql-tables";
    }

    @GetMapping("/show-mysql-tables")
    public String showMysqlTables(@RequestParam(name = "tables", required = false) String tables,
                             Model model) throws SQLException, ClassNotFoundException, IOException {
        if(tables != null) {
            requestChartData.setTableName(tables);
            RequestTableRows requestTableRows = new RequestTableRows();
            requestTableRows.setRowsLimit(2);
            requestTableRows.setTableName(tables);
            ((MysqlDataSource)initDataSource).setRequest(requestTableRows);
            //initDataSource.initDataSource();
            return "redirect:/show-fields";
        }
        ArrayList<String> tableList = new ArrayList<>();
        initDataSource.open();
        while(initDataSource.getNextRow()) {
            tableList.add( ((MysqlDataSource) initDataSource).getColumnValue(1));
        }
        model.addAttribute("tableList", tableList);
        initDataSource.close();
        return "show-mysql-tables";
    }
}
