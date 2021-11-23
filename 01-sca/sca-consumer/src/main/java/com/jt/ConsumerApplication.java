package com.jt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableFeignClients
@SpringBootApplication
public class ConsumerApplication {

    /**
     * Spring Web模块中提供了一个RestTemplate对象，基于此对象
     * 可以完成远程(Remote)服务的调用,在当前项目(服务)sca-consumer中，我们
     * 要使用RestTemplate对象，调用远程sca-provider服务，但spring默认启动时
     * 并没有帮我们创建这个对象，所以在这里我们自己创建，并交给spring管理。
     * @return
     * sca-consumer----->restTemplate-->sca-provider
     */
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    /**
     * 当我们使用@LoadBalanced注解描述RestTemplate对象时，
     * 假如此时再基于RestTemplate对象对远程服务进行访问时，
     * 此请求就会被一个拦截器拦截下来，请求被拦截到以后
     * 就会先基于服务名找到服务实例(负载均衡)。
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate loadBalancedRestTemplate(){
        return new RestTemplate();
    }

    /**
     * 基于Java代码方式配置负载均衡策略
     * @return
     */
//    @Bean
//    public IRule ribbonRule(){
//        return new RandomRule();
//    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }
}
