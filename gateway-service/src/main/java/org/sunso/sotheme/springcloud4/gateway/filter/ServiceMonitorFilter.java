package org.sunso.sotheme.springcloud4.gateway.filter;

import com.alibaba.cloud.nacos.registry.NacosAutoServiceRegistration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class ServiceMonitorFilter implements GlobalFilter, Ordered {

	@Autowired
	private NacosAutoServiceRegistration nacosAutoServiceRegistration;

	@Autowired
	private ApplicationContext context;

	/**
	 * 关闭服务之前的等待时间
	 */
	@Value("${biz.service.stop.waitTime:15000}")
	private int stopWaitTime;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		log.info("ServiceMonitorFilter.....................");
		String host = exchange.getRequest().getURI().getHost();
		String requestUri = exchange.getRequest().getURI().getPath();
		ServerHttpResponse response = exchange.getResponse();
		if (requestUri.equals("/auto/monitor/service/check")) {
			return response.writeWith(Flux.just(response.bufferFactory().wrap("ok".getBytes())));
		}
		else if (requestUri.equals("/auto/monitor/service/stop")) {
			return stopService(response, host);
		}
		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		//return Ordered.HIGHEST_PRECEDENCE;
		return Ordered.LOWEST_PRECEDENCE;
	}

	private Mono<Void> stopService(ServerHttpResponse response, String serverName) {
		if (!"localhost".equalsIgnoreCase(serverName)) {
			response.setStatusCode(HttpStatus.UNAUTHORIZED);
			return response.writeWith(Flux.just(response.bufferFactory().wrap("Unauthorized".getBytes())));
		}
		new Thread(() -> {
			try {
				long beginTime = System.currentTimeMillis();
				log.info("准备开始关闭服务");
				nacosAutoServiceRegistration.stop();
				Thread.sleep(stopWaitTime);
				SpringApplication.exit(context);
				log.info("成功关闭服务，耗时[{}]秒", (System.currentTimeMillis() - beginTime) / 1000);
			}
			catch (Exception e) {
				log.error("关闭服务出现异常", e);
			}
		}).start();
		return response.writeWith(Flux.just(response.bufferFactory().wrap("ok".getBytes())));
	}

}
