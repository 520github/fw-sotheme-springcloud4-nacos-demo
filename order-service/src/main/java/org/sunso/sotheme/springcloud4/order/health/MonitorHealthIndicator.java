package org.sunso.sotheme.springcloud4.order.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class MonitorHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        int checkCode = check();
        if (checkCode != 200) {
            return Health.down().withDetail("errorCode", checkCode).build();
        }
        return Health.up().build();
    }

    private int check() {
        return 400;
    }
}
