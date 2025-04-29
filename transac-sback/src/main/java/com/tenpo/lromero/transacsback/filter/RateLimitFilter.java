package com.tenpo.lromero.transacsback.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitFilter implements Filter {

    private static final long WINDOW_TIME = 60;
    private static final int MAX_REQUESTS = 3;

    private Map<String, ClientRequestInfo> clients = new ConcurrentHashMap<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String clientIp = httpRequest.getRemoteAddr();
        String path = httpRequest.getRequestURI();

        // Ignorar swagger-ui y OpenAPI docs
        // Allow preflight requests
        if (path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs")
                || httpRequest.getMethod().equals("OPTIONS")) {
            chain.doFilter(request, response);
            return;
        }

        ClientRequestInfo info = clients.getOrDefault(clientIp, new ClientRequestInfo(Instant.now(), 0));

        Instant now = Instant.now();
        if (now.isAfter(info.windowStart.plusSeconds(WINDOW_TIME))) {
            info = new ClientRequestInfo(now, 0);
        }

        if (info.requestCount >= MAX_REQUESTS) {
            ((HttpServletResponse) response).setStatus(429); // HttpServletResponse.SC_TOO_MANY_REQUESTS
            response.getWriter().write("Rate limit exceeded. Try again later.");
            return;
        } else {
            info.requestCount++;
            clients.put(clientIp, info);
        }

        chain.doFilter(request, response);
    }

    private static class ClientRequestInfo {
        Instant windowStart;
        int requestCount;

        ClientRequestInfo(Instant windowStart, int requestCount) {
            this.windowStart = windowStart;
            this.requestCount = requestCount;
        }
    }
}