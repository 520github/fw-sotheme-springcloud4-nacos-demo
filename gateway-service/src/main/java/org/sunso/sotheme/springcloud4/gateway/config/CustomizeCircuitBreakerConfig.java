package org.sunso.sotheme.springcloud4.gateway.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CustomizeCircuitBreakerConfig {

    @Bean
    public ReactiveResilience4JCircuitBreakerFactory defaultCustomizer() {
        CircuitBreakerConfig config = CircuitBreakerConfig
                .custom()
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.TIME_BASED) //滑动窗口的类型为时间窗口
                .slidingWindowSize(30) // 时间窗口的大小为60秒
                .minimumNumberOfCalls(5) //在单位时间窗口内最少需要5次调用才能开始进行统计计算
                .failureRateThreshold(50) //在单位时间窗口内调用失败率达到50%后会启动断路器
                .enableAutomaticTransitionFromOpenToHalfOpen() // 允许断路器自动由打开状态转换为半开状态
                .permittedNumberOfCallsInHalfOpenState(5) //在半开状态下允许进行正常调用的次数
                .waitDurationInOpenState(Duration.ofSeconds(5)) //断路器打开状态转换为半开状态需要等待60秒
                .recordExceptions(Throwable.class)
                .build();

        ReactiveResilience4JCircuitBreakerFactory factory = new ReactiveResilience4JCircuitBreakerFactory(CircuitBreakerRegistry.ofDefaults(), TimeLimiterRegistry.ofDefaults());
        factory
                .configureDefault(id -> new Resilience4JConfigBuilder(id)
                        .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofMillis(100)).build())
                        .circuitBreakerConfig(config).build()
                );

        return factory;
    }

}
