package com.jt;

import com.jt.common.cache.DefaultCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
//@Import(DefaultCache.class)//推荐配置用此方式进行导入
public class ProviderApplication {

    @Bean
    public DefaultCache defaultCache(){
        return new DefaultCache();
    }

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class,args);
    }
}
