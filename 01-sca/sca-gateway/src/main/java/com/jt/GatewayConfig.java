package com.jt;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

//@Configuration
public class GatewayConfig {
        public GatewayConfig(){
            GatewayCallbackManager.setBlockHandler(new BlockRequestHandler() {
                @Override
                public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange, Throwable throwable) {
                    Map<String,Object> map=new HashMap<>();
                    map.put("state",429);
                    map.put("message","two many request");
                    String jsonStr= JSON.toJSONString(map);
                    return ServerResponse.ok().body(Mono.just(jsonStr),String.class);
            }
        });
    }
}
