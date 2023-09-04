package org.sunso.sotheme.springcloud4.gateway.controller;

import com.alibaba.cloud.nacos.registry.NacosAutoServiceRegistration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

@RestController
@RequestMapping("/auto/monitor/")
@Slf4j
public class ServiceMonitorController {

    @Autowired
    private NacosAutoServiceRegistration nacosAutoServiceRegistration;
    @Autowired
    private ApplicationContext context;

    /**
     * 关闭服务之前的等待时间
     */
    @Value("${biz.service.stop.waitTime:15000}")
    private int stopWaitTime;

    @GetMapping("service/check")
    public ResponseEntity<String> serviceCheck() {
        return ResponseEntity.ok("ok");
    }

    @PostMapping("service/stop")
    public ResponseEntity<Boolean> serviceStop(ServerWebExchange serverWebExchange) {
        if (!"localhost".equalsIgnoreCase(serverWebExchange.getRequest().getURI().getHost())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
        new Thread(() -> {
            try {
                long beginTime = System.currentTimeMillis();
                log.info("准备开始关闭服务");
                nacosAutoServiceRegistration.stop();
                Thread.sleep(stopWaitTime);
                SpringApplication.exit(context);
                log.info("成功关闭服务，耗时[{}]秒", (System.currentTimeMillis()-beginTime)/1000);
            }catch (Exception e) {
                log.error("关闭服务出现异常", e);
            }
        }).start();
        return ResponseEntity.ok(true);
    }
}
