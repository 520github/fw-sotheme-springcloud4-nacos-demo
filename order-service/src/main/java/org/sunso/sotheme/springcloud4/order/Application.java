
package org.sunso.sotheme.springcloud4.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.sunso.keypoint.trace.log.common.utils.MDCTraceUtils;
import org.sunso.keypoint.trace.log.mdc.autoconfigure.TtlMDCAdapterInitializer;

@SpringBootApplication
@EnableFeignClients
//@ComponentScan("org.sunso.sotheme.springcloud4")
public class Application {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Application.class);
        //springApplication.addInitializers(new TtlMDCAdapterInitializer());
        springApplication.run(args);
        System.out.println("Hello world!" + MDCTraceUtils.createTraceId());
    }
}