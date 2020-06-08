package com.yy.platform.component.starter.orm.druid;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.datasource")
public class DruidProperties {
    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private int minIdle = 5;
    private boolean testWhileIdle = true;
    private boolean logAbandoned = true;
    private boolean removeAbandoned = true;
    private int maxPoolPreparedStatementPerConnectionSize = 20;
    private long slowSqlMillis = 3000;


}
