package com.banking.gatewayserver.filters;

import com.banking.gatewayserver.filters.util.TraceFilterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Order(1)
@Component
public class TraceRequestFilter implements GlobalFilter {

    private final Logger logger = LoggerFactory.getLogger("TraceRequestFilter");

    private final TraceFilterUtil traceFilterUtil;

    @Autowired
    TraceRequestFilter(TraceFilterUtil traceFilterUtil){
        this.traceFilterUtil = traceFilterUtil;
    }

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders headers = exchange.getRequest().getHeaders();

        String correlationId = traceFilterUtil.getCorrelationId(headers);
        if(null == correlationId){
            correlationId = UUID.randomUUID().toString();
            exchange.mutate().request(
                    exchange.getRequest().mutate().header(
                            TraceFilterUtil.CORRELATION_ID, correlationId).build())
                    .build();
        }
        return chain.filter(exchange);
    }

   /* @Bean
    public GlobalFilter preGlobalFilter() {
        return (exchange, chain) -> {
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
                String correlationId = traceFilterUtil.getCorrelationId(requestHeaders);
                if(null == correlationId){
                    logger.debug("Added the correlation id to the outbound headers. {}", correlationId);
                    exchange.getResponse().getHeaders().add(
                            TraceFilterUtil.CORRELATION_ID, correlationId);
                }
            }));
        };
    }*/


}
