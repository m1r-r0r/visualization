package com.github.m1rr0r.visualization.controllers;

import com.github.m1rr0r.visualization.sourcesConnections.jdbc.MysqlConnection;
import com.github.m1rr0r.visualization.sourcesConnections.jdbc.MysqlDataSource;
import com.github.m1rr0r.visualization.sourcesConnections.jdbc.request.RequestChartData;
import com.github.m1rr0r.visualization.sourcesConnections.jdbc.request.RequestSourceTables;
import com.github.m1rr0r.visualization.sourcesConnections.jdbc.request.RequestTableRows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@Controller
@Lazy
public class DataSourceController {
    private RequestChartData requestChartData;
    @Autowired
    private MysqlConnection connection;
    @Autowired
    @Qualifier("initDataSource")
    private MysqlDataSource initDataSource;
    @Autowired
    @Qualifier("chartDataSource")
    private MysqlDataSource chartDataSource;

    @GetMapping("/new-mysql-connection")
    public String newMysqlConnection() {
        initDataSource.setConnection(connection);
        initDataSource.setRequest(new RequestSourceTables());
        requestChartData = new RequestChartData();

        chartDataSource.setRequest(requestChartData);
        chartDataSource.setConnection(connection);
        return "new-mysql-connection";
    }

    @PostMapping("/new-mysql-connection")
    public String setMysqlConnection(@RequestParam String password,
                                @RequestParam String username,
                                @RequestParam String db_name) {
        connection.getConnectionParams().setPASSWORD(password);
        connection.getConnectionParams().setUSERNAME(username);
        connection.getConnectionParams().setDBNAME(db_name);
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
            initDataSource.setRequest(requestTableRows);
            return "redirect:/show-fields";
        }
        ArrayList<String> tableList = new ArrayList<>();
        initDataSource.open();
        while(initDataSource.getNextRow()) {
            tableList.add(initDataSource.getColumnValue(1));
        }
        model.addAttribute("tableList", tableList);
        initDataSource.close();
        return "show-mysql-tables";
    }
}
