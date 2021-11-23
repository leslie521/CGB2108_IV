package com.jt.service.factory;

import com.jt.consumer.service.RemoteProviderService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 基于此对象处理RemoteProviderService接口调用时出现的服务中断,超时等问题
 */
@Component
public class ProviderFallbackFactory implements FallbackFactory<RemoteProviderService>{
    /**
     * 此方法会在RemoteProviderService接口服务调用时,出现了异常后执行.
     * @param throwable 用于接收异常
     */
    @Override
    public RemoteProviderService create(Throwable throwable) {
        return (msg)->{
            return "服务维护中,稍等片刻再访问";
        };
    }
}
