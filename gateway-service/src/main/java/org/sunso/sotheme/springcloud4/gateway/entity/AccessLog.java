package org.sunso.sotheme.springcloud4.gateway.entity;

import lombok.Data;

import java.util.Date;

@Data
public class AccessLog {

    private String targetServer;
    private String requestPath;
    private String requestMethod;
    private String schema;
    private String requestBody;
    private String responseData;
    private String ip;
    private Date requestTime;
    private Date responseTime;
    private long executeTime;


    public static AccessLog newInstance() {
        return new AccessLog();
    }

    public long fetchExecuteTime() {
        if (responseTime == null) {
            return -1;
        }
        if (requestTime == null) {
            return -2;
        }
        return responseTime.getTime() - requestTime.getTime();
    }
}
