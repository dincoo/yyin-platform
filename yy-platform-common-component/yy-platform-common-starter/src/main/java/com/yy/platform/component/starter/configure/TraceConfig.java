package com.yy.platform.component.starter.configure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "yy.platform.trace")
@RefreshScope
@Component
public class TraceConfig {

    /**
     * 是否开启日志链路追踪
     */
    private Boolean enable = false;
}
