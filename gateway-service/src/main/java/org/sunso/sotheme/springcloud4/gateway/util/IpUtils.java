package org.sunso.sotheme.springcloud4.gateway.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

@Slf4j
public class IpUtils {
    private static final String UNKNOWN = "unknown";
    private static final String LOCALHOST = "127.0.0.1";
    private static final String LOCALHOST_IP_6 ="0:0:0:0:0:0:0:1";
    private static final String SEPARATOR = ",";

    private static final String HEADER_X_FORWARDED_FOR = "x-forwarded-for";
    private static final String HEADER_PROXY_CLIENT_IP = "Proxy-Client-IP";
    private static final String HEADER_WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";

    /**
     * 获取真实客户端IP
     * @param serverHttpRequest
     * @return
     */
    public static String getRealIpAddress(ServerHttpRequest serverHttpRequest) {
        String ipAddress;
        try {

            // 1.根据常见的代理服务器转发的请求ip存放协议，从请求头获取原始请求ip。值类似于203.98.182.163, 203.98.182.163
            ipAddress = serverHttpRequest.getHeaders().getFirst(HEADER_X_FORWARDED_FOR);
            if (isTrimEmpty(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = serverHttpRequest.getHeaders().getFirst(HEADER_PROXY_CLIENT_IP);
            }
            if (isTrimEmpty(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = serverHttpRequest.getHeaders().getFirst(HEADER_WL_PROXY_CLIENT_IP);
            }

            // 2.如果没有转发的ip，则取当前通信的请求端的ip
            if (isTrimEmpty(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                InetSocketAddress inetSocketAddress = serverHttpRequest.getRemoteAddress();
                if(inetSocketAddress != null) {
                    ipAddress = inetSocketAddress.getAddress().getHostAddress();
                }
                // 如果是127.0.0.1，则取本地真实ip
                if (LOCALHOST.equals(ipAddress) || LOCALHOST_IP_6.equals(ipAddress)) {
                    InetAddress localAddress = getLocalHostLANAddress();
                    if(localAddress.getHostAddress() != null) {
                        ipAddress = localAddress.getHostAddress();
                    }
                }
            }

            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            // "***.***.***.***"
            if (ipAddress != null) {
                ipAddress = ipAddress.split(SEPARATOR)[0].trim();
            }
        } catch (Exception e) {
            log.error("解析请求IP失败", e);
            ipAddress = "";
        }
        return ipAddress == null ? "" : ipAddress;
    }

    private static boolean isTrimEmpty(String str) {
        if (str == null) {
            return true;
        }
        if (str.trim().length() == 0) {
            return true;
        }
        return false;
    }

    // 正确的IP拿法，即优先拿site-local地址
    private static InetAddress getLocalHostLANAddress() throws UnknownHostException {
        try {
            InetAddress candidateAddress = null;
            // 遍历所有的网络接口
            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements();) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                // 在所有的接口下再遍历IP
                for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements();) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    if (!inetAddr.isLoopbackAddress()) {// 排除loopback类型地址
                        if (inetAddr.isSiteLocalAddress()) {
                            // 如果是site-local地址，就是它了
                            return inetAddr;
                        } else if (candidateAddress == null) {
                            // site-local类型的地址未被发现，先记录候选地址
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress;
            }
            // 如果没有发现 non-loopback地址.只能用最次选的方案
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            if (jdkSuppliedAddress == null) {
                throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
            }
            return jdkSuppliedAddress;
        } catch (Exception e) {
            UnknownHostException unknownHostException = new UnknownHostException(
                    "Failed to determine LAN address: " + e);
            unknownHostException.initCause(e);
            throw unknownHostException;
        }
    }
}
