package com.jt.consumer.controller;

import com.netflix.loadbalancer.IRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 定义服务消费端Controller,在这个Controller对象
 * 的方法中实现对远端服务sca-provider的调用
 */
@RestController
public class ConsumerController {

    /**
     * 从spring容器获取一个RestTemplate对象,
     * 基于此对象实现远端服务调用
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 在此方法中通过一个RestTemplate对象调用远端sca-provider中的服务
     * @return
     * 访问此方法的url: http://localhost:8090/consumer/doRestEcho1
     */
    //打开浏览器
    //访问:http://localhost:8090/consumer/doRestEcho1
    @GetMapping("/consumer/toRestEcho01")
    public String toRestEcho1(){
        //1.定义要调用的远端服务的url
        String url = "http://localhost:8081/provider/echo/8090";
        //2.基于restTemplate对象中的相关方法进行服务调用
        return restTemplate.getForObject(url, String.class);
    }

    /**
     * 借助此对象，可以基于服务名从nacos获取多个服务实例，
     * 并且基于一定的负载均衡算法进行远端服务调用。
     */
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    //实现类RibbonLoadBalancerClient
    @Value("${spring.application.name:8090}")
    private String appName;

    //Consumer-->Provider
    //访问http://localhost:8090/consumer/doRestEcho2
    @GetMapping("/consumer/doRestEcho02")
    public String doRestEcho02(){
        //1.基于服务名获取服务实例
        ServiceInstance serviceInstance = loadBalancerClient.choose("sca-provider");
        //2.基于服务实例构建要访问的服务的url
        String url = String.format("http://%s:%s/provider/echo/%s",
                serviceInstance.getHost(),serviceInstance.getPort(),appName);
        return restTemplate.getForObject(url,String.class);
//        String ip=serviceInstance.getHost();
//        int port=serviceInstance.getPort();
        //String url="http://"+ip+":"+port+"/provider/echo/8090";
        //基于String类的format方法进行字符串拼接，这里的%s表示占位符，传值时注意顺序

        //String url=String.format("http://%s:%s/provider/echo/%s",ip,port,appName);
        //return restTemplate.getForObject(url, String.class);
//        String url=String.format("http://%s:%s/provider/echo/{msg}",ip,port);
//        return restTemplate.getForObject(url,//远端服务的url
//                String.class,appName);//远端服务url对应的返回值类型(ResponseType)
    }

    @Autowired
    //@Qualifier("loadBalancedRestTemplate")
    private RestTemplate loadBalancedRestTemplate;

    @GetMapping("/consumer/doRestEcho03")
    public String doRestEcho03() {
        String serviceName = "sca-provider";
        //1.此请求会被一个拦截器拦截下来，请求被拦截到以后就会先基于服务名找到服务实例(负载均衡)。
        String url = String.format("http://%s/provider/echo/{msg}", serviceName);
        return loadBalancedRestTemplate.getForObject(url, String.class,appName);
        //这里的appName是传给了url中的{msg},这是rest风格的一种写法
    }
}
