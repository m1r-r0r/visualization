package com.github.m1rr0r.visualization.sourcesConnections.jdbc;

import com.github.m1rr0r.visualization.sourcesConnections.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@ComponentScan("com.github.m1rr0r.visualization.sourcesConnections.jdbc")
public class MysqlSpringConfiguration {
    @Bean
    @Lazy
    public MysqlDataSource initDataSource() {
        return new MysqlDataSource();
    }

    @Bean
    @Lazy
    public MysqlDataSource chartDataSource() {
        return new MysqlDataSource();
    }
}


