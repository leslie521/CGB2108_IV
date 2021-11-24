package com.jt.consumer.service;

import com.jt.consumer.service.RemoteProviderService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 基于此对象处理RemoteProviderService接口调用时出现的服务中断,超时等问题
 * 服务回调工厂，当远程服务不可用或者远程服务调用超时，可以通过
 * 配置的方式，调用本地服务返回一个结果
 */
@Component
public class ProviderFallbackFactory implements FallbackFactory<RemoteProviderService>{

    @Override
    public RemoteProviderService create(Throwable throwable) {
        return new RemoteProviderService() {
            @Override
            public String echoMessage(String msg) {
                //...给运维人员发短信...,
                return "服务维护中,稍等片刻再访问";
            }
        };
    }

    /**
     * 此方法会在RemoteProviderService接口服务调用时,出现了异常后执行.
     * @param throwable 用于接收异常
     */
//    @Override
//    public RemoteProviderService create(Throwable throwable) {
//        return (msg)->{ // Lamda表达式方式
//            return "服务维护中,稍等片刻再访问";
//        };
//    }
}
