package com.github.m1rr0r.visualization.sourcesConnections.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

@Component
//@Scope("prototype")
public class MysqlConnection implements Closeable {
    @Autowired
    @Lazy
    private MysqlConnectionParams connectionParams;
    private Connection connection;

    @Autowired
    @Lazy
    public void setConnectionParams(MysqlConnectionParams connectionParams) {
        this.connectionParams = connectionParams;
    }

    public MysqlConnectionParams getConnectionParams() {
        return connectionParams;
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
                //"jdbc:mysql://localhost:8889/" + db_name;
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(
                this.connectionParams.getDBNAME(),
                this.connectionParams.getUSERNAME(),
                this.connectionParams.getPASSWORD());
        return connection;
    }

    @Override
    public void close() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
