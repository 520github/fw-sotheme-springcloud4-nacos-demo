package org.sunso.sotheme.springcloud4.common.dto.data;

import lombok.Data;

import java.util.Date;

@Data
public class DataDTO {
    private String serverId;
    private String serverName;
    private String eventId;
    private String eventName;
    private Date eventTime;

}
