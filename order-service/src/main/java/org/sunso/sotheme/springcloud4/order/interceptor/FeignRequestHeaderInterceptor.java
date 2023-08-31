package org.sunso.sotheme.springcloud4.order.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Component
public class FeignRequestHeaderInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        String userAgent = getRequestHeaderValue("User-Agent");
        log.info("request header [User-Agent] value is [{}]", userAgent);
        template.header("User-Agent", userAgent + "--orderService");
        template.header("Content-Type", "application/json;charset=UTF-8");
    }

    private String getRequestHeaderValue(String headerName) {
        HttpServletRequest request = getHttpServletRequest();
        if (request == null) {
            log.error("getRequestHeaderValue find request is null");
            return null;
        }
        return request.getHeader(headerName);
    }

    private HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes attributes = getServletRequestAttributes();
        if (attributes == null) {
            log.error("getHttpServletRequest find ServletRequestAttributes is null");
            return null;
        }
        return attributes.getRequest();
    }

    private ServletRequestAttributes getServletRequestAttributes() {
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            log.info("getServletRequestAttributes from currentRequestAttributes");
            attributes = (ServletRequestAttributes) RequestContextHolder
                    .currentRequestAttributes();
        }
        return attributes;
    }
}
