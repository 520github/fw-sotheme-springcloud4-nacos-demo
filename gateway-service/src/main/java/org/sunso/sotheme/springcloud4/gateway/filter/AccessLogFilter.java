package org.sunso.sotheme.springcloud4.gateway.filter;

import com.alibaba.nacos.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.WebUtils;
import org.sunso.sotheme.springcloud4.gateway.util.IpUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Slf4j
@Component
public class AccessLogFilter implements GlobalFilter, Ordered {
    private final List<HttpMessageReader<?>> messageReaders = HandlerStrategies.withDefaults().messageReaders();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request =exchange.getRequest();
        String requestPath = request.getPath().pathWithinApplication().value();
        String requestIp = getRequestIp(request);
        log.info("AccessLogFilter requestPath[{}], requestIp[{}] ", requestPath, requestIp);
        Route route = getGatewayRoute(exchange);

        MediaType mediaType = request.getHeaders().getContentType();
        if(MediaType.APPLICATION_FORM_URLENCODED.isCompatibleWith(mediaType) || MediaType.APPLICATION_JSON.isCompatibleWith(mediaType)){
            return requestBodyLog(exchange, chain);
        }else{
            return requestParameterLog(exchange, chain);
        }
    }

    @Override
    public int getOrder() {
        return -100;
    }

    private String getRequestIp(ServerHttpRequest request) {
        return IpUtils.getRealIpAddress(request);
    }

    private Mono<Void> requestParameterLog(ServerWebExchange exchange, GatewayFilterChain chain) {
        StringBuilder builder = new StringBuilder();
        MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
        for (Map.Entry<String, List<String>> entry : queryParams.entrySet()) {
            builder.append(entry.getKey()).append("=").append(StringUtils.join(entry.getValue(), ",")).append("&");
        }
        log.info("request parameter[{}]", builder);

        //获取响应体
        ServerHttpResponseDecorator decoratedResponse = responseDecorate(exchange);
        return chain.filter(exchange.mutate().response(decoratedResponse).build())
                .then(Mono.fromRunnable(() -> {
                    log.info("write requestParameterLog log");
                }));
    }

    private Mono requestBodyLog(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerRequest serverRequest = ServerRequest.create(exchange, messageReaders);
        Mono<String> modifiedBody = serverRequest.bodyToMono(String.class)
                .flatMap(body -> {
                    log.info("body [{}]", body);
                    return Mono.just(body);
                });
        BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody, String.class);
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(exchange.getRequest().getHeaders());
        headers.remove(HttpHeaders.CONTENT_LENGTH);

        CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);

        return bodyInserter.insert(outputMessage, new BodyInserterContext())
                .then(Mono.defer(() -> {
                    // 重新封装请求
                    ServerHttpRequest decoratedRequest = requestDecorate(exchange, headers, outputMessage);

                    // 记录响应日志
                    ServerHttpResponseDecorator decoratedResponse = responseDecorate(exchange);
                    return chain.filter(exchange.mutate().request(decoratedRequest).response(decoratedResponse)
                            .build()).then(Mono.fromRunnable(() ->{
                        // 打印日志
                        log.info("write requestBodyLog log");
                    }));
                }));
    }

    private Route getGatewayRoute(ServerWebExchange exchange) {
        return exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
    }

    private ServerHttpRequestDecorator requestDecorate(ServerWebExchange exchange, HttpHeaders headers,
                                                       CachedBodyOutputMessage outputMessage) {
        return new ServerHttpRequestDecorator(exchange.getRequest()) {
            @Override
            public HttpHeaders getHeaders() {
                long contentLength = headers.getContentLength();
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.putAll(super.getHeaders());
                if (contentLength > 0) {
                    httpHeaders.setContentLength(contentLength);
                } else {
                    // TODO: this causes a 'HTTP/1.1 411 Length Required' // on
                    // httpbin.org
                    httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                }
                return httpHeaders;
            }
            @Override
            public Flux<DataBuffer> getBody() {
                return outputMessage.getBody();
            }
        };
    }

    private ServerHttpResponseDecorator responseDecorate(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        DataBufferFactory bufferFactory = response.bufferFactory();
        return new ServerHttpResponseDecorator(response) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    Date responseTime = new Date();
                    String originalResponseContentType = exchange.getAttribute(ServerWebExchangeUtils.ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR);
                    if (HttpStatus.OK == this.getStatusCode()
                            && originalResponseContentType != null
                            && originalResponseContentType.contains("application/json")) {
                        Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                        return super.writeWith(fluxBody.buffer().map(dataBuffers -> {

                            // 合并多个流集合，解决返回体分段传输
                            DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
                            DataBuffer join = dataBufferFactory.join(dataBuffers);
                            byte[] content = new byte[join.readableByteCount()];
                            join.read(content);

                            // 释放掉内存
                            DataBufferUtils.release(join);
                            String responseResult = new String(content, StandardCharsets.UTF_8);

                            log.info("responseResult[{}]", responseResult);

                            return bufferFactory.wrap(content);

                        }));
                    }
                }
                return super.writeWith(body);
            }
        };
    }
}
