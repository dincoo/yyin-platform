package com.yy.platform.component.starter.orm.druid;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableConfigurationProperties(DruidProperties.class)
public class DruidConfig {
    @Bean
    public DataSource datasource(DruidProperties druidProperties) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(druidProperties.getDriverClassName());
        druidDataSource.setUrl(druidProperties.getUrl());
        druidDataSource.setUsername(druidProperties.getUsername());
        druidDataSource.setPassword(druidProperties.getPassword());
        druidDataSource.setMinIdle(druidProperties.getMinIdle());
        druidDataSource.setLogAbandoned(druidProperties.isLogAbandoned());
        druidDataSource.setRemoveAbandoned(druidProperties.isRemoveAbandoned());
        druidDataSource.setTestWhileIdle(druidProperties.isTestWhileIdle());
        List<Filter> filters = new ArrayList<>();
        filters.add(getStatFilter(druidProperties));
        druidDataSource.setProxyFilters(filters);
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(druidProperties.getMaxPoolPreparedStatementPerConnectionSize());
        return druidDataSource;
    }
    public StatFilter getStatFilter(DruidProperties druidProperties){
        StatFilter statFilter = new StatFilter();
        statFilter.setLogSlowSql(true);
        statFilter.setMergeSql(true);
        statFilter.setSlowSqlMillis(druidProperties.getSlowSqlMillis());
        return statFilter;
    }

    // 配置事物管理器
    @Bean(name="transactionManager")
    public DataSourceTransactionManager transactionManager(DruidProperties druidProperties){
        return new DataSourceTransactionManager(datasource(druidProperties));
    }

}
