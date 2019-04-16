package com.example.anpuservice.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collections;
import java.util.StringTokenizer;

@Configuration
@EnableTransactionManagement  // 开启注解事务管理，等同于xml配置方式的 <tx:annotation-driven />
@MapperScan(basePackages = {"com.example.anpuservice.mapper"})
public class DataSourceConfig implements EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(final Environment environment) {
        this.environment = environment;
    }

    @Bean
    @Primary
    public DataSource dataSource() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(environment.getProperty("spring.datasource.url"));
        datasource.setDriverClassName(environment.getProperty("spring.datasource.driverClassName"));
        datasource.setUsername(environment.getProperty("spring.datasource.username"));
        datasource.setPassword(environment.getProperty("spring.datasource.password"));
        datasource.setMinIdle(Integer.parseInt(environment.getProperty("spring.datasource.druid.min-idle")));
        datasource.setMaxWait(Long.parseLong(environment.getProperty("spring.datasource.druid.max-wait")));
        datasource.setMaxActive(Integer.valueOf(environment.getProperty("spring.datasource.druid.max-active")));
        datasource.setMinEvictableIdleTimeMillis(
                Long.parseLong(environment.getProperty("spring.datasource.druid.min-evictable-idle-time-millis")));
        datasource.setConnectionInitSqls(Collections.list(new StringTokenizer(environment.getProperty("spring.datasource.connectionInitSqls"), ";")));

        try {
            datasource.setFilters(environment.getProperty("spring.datasource.druid.filters"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return datasource;
    }
}
