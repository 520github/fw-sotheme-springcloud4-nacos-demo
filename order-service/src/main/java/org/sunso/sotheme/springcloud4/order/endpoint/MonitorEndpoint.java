package org.sunso.sotheme.springcloud4.order.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id="monitor")
public class MonitorEndpoint {

    @ReadOperation
    public String getData(String data) {
        return "getData:" + data;
    }

    @WriteOperation
    public String postData(String data) {
        return "postData:" + data;
    }

    @DeleteOperation
    public String deleteData(String data) {
        return "deleteData:" + data;
    }
}
