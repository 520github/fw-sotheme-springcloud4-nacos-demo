package org.sunso.sotheme.springcloud4.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class MyKeyResolver implements KeyResolver {
    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        String remoteAddress = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
        return Mono.just(remoteAddress);
    }
}
