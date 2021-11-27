package com.jt.provider.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.stereotype.Service;


@Service
public class ResourceService {

    /**
     * @SentinelResource 描述方法时，可以在sentinel控制台创建一个资源链路,
     * 这个链路的名称默认为value属性的值，当出现限流时，客户端默认看到的是一个
     * 500异常。假如希望对限流结果进行自定义处理，可以考虑使用blockHandlerClass
     * 属性指定一个限流处理类，然后再通过blockHandler属性指定具体异常处理方法,
     * 这个异常处理方法必须与@SentinelResource注解描述的方法，返回值类型相同
     * ，同时必须是static方法，方法中参数可以是BlockException类型
     * @return
     */
    @SentinelResource(value = "doGetResource",
                      blockHandlerClass = ResourceBlockHandle.class,
                      blockHandler = "handleBlock")
    public String doGetResource(){
        return "My resource";
    }
}
