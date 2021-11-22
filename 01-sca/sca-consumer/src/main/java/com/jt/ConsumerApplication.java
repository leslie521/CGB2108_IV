package com.jt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

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

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }
}
