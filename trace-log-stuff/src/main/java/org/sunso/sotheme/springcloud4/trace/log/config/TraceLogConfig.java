package org.sunso.sotheme.springcloud4.trace.log.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "trace.log")
public class TraceLogConfig {
    /**
     * 是否开启日志链路追踪
     */
    private Boolean enable = true;
}
