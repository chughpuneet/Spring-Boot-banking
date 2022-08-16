package com.banking.gatewayserver.filters.util;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TraceFilterUtil {
    public static final String CORRELATION_ID = "banking-correlation-id";

    public String getCorrelationId(HttpHeaders headers){
        List<String> correlationIdHeaders = headers.get(CORRELATION_ID);

        if(null != correlationIdHeaders){
            return correlationIdHeaders.stream().findFirst().get();
        }else{
            return null;
        }
    }
}
