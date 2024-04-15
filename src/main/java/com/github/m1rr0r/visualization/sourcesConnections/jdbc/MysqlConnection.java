package com.github.m1rr0r.visualization.sourcesConnections.jdbc;

import java.io.Closeable;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class MysqlConnection implements Closeable {
    private MysqlConnectionParams connectionParams;
    private Connection connection;

    public void setConnectionParams(MysqlConnectionParams connectionParams) {
        this.connectionParams = connectionParams;
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
