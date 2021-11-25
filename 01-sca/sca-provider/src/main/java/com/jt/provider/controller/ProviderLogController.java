package com.jt.provider.controller;

import com.jt.ProviderApplication;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.logging.LogLevel;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 基于此controller演示配置中心的作用.
 * 在这个controller中我们会基于日志对象
 * 进行日志输出测试.
 */
/**
 * @Slf4j 注解描述类会在类中自动创建一个org.slf4j.Logger对象。
 * @RefreshScope 注解描述类时，会在配置中心数据发生变化后，再次访问此注解描述的对象，
 * 会重新构建对象，初始化属性。
 */
@RefreshScope
@Slf4j
@RestController
public class ProviderLogController {
    /*创建一个日志对象
    org.slf4j.Logger (Java中的日志API规范,基于这个规范有Log4J,Logback等日志库)
    org.slf4j.LoggerFactory
    log对象在哪个类中创建,getLogger方法中的就传入哪个类的字节码对象
    记住:以后只要Java中使用日志对象,你就采用下面之中方式创建即可.
    假如在log对象所在的类上使用了@Slf4j注解,log不再需要我们手动创建,lombok会帮我们创建*/
//    private static Logger log =
//            LoggerFactory.getLogger(ProviderLogController.class);

    @GetMapping("/provider/log/doLog01")
    public String doLog01(){
        System.out.println("===doLog01===");
        log.trace("===trace===");
        log.debug("===debug===");
        log.info("===info===");
        log.warn("===warn===");
        log.error("===error===");
        return "log config test";
    }

    public ProviderLogController(){
        System.out.println("===ProviderLogController()===");
    }

    @Value("${logging.level.com.jt:info}")
    private LogLevel logLevel;//构建对象时属性进行初始化

    @GetMapping("/provider/log/doLog02")
    public String doLog02(){
        return "log.level: " + logLevel;
    }
}
