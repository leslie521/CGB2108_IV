package com.jt.resource.aspect;

import com.jt.resource.annotation.RequiredLog;
import com.jt.resource.pojo.Log;
import com.jt.resource.service.RemoteLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @Aspect 在Spring框架中，有@Aspect注解描述的类，为切面类型，此类中可以包含：
 * 1)切入点(切入扩展逻辑的位置~例如权限控制,日志记录,事务处理的位置),在
 * @Aspect描述的类中,通常使用@Pointcut注解进行定义.使用切入点描述的方法为切入点方法.
 *
 * 2)通知方法(在切入点对应的目标方法执行前后要执行逻辑需要写到这样的方法中),在
 * @Aspect描述的类中,通过@Before,@After,@Aroud,@AfterReturning,@AfterThrowing
 * 这样的注解进行描述.
 * a: @Before切入点方法执行之前执行
 * b: @After 切入点方法执行之后执行(不管切入点方法是否执行成功了,它都会执行)
 * c: @Aroud 切入点方法执行之前和之后都可以执行(最重要)
 * d: @AfterReturning 切入点方法成功执行之后执行
 * e: @AfterThrowing 切入点方法执行时出了异常会执行
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    /**
     * 为什么由指定注解描述的方法为一个切入点方法，这里需要对切入点进行定义？
     * Spring框架中可以基于Pointcut注解定义切入点，切入点表示可以有很多种，
     * 例如注解方式的切入点表达式@annotation(com.jt.resource.annotation.RequiredLog)
     * 此时，假如项目中使用RequiredLog注解描述方法，那这个方法就是切入点方法。
     */
    @Pointcut("@annotation(com.jt.resource.annotation.RequiredLog)")
    public void doLog(){
        //此方法中不需要写任何内容,只负责承载@Pointcut注解
    }

    /**
     * @Around注解描述的方法，是一个环绕通知方法，在方法内部可以手动去调用执行链(
     * 这个指链中包含其它切面以及对应目标方法)
     * @param jp 连接点(@Around通知中连接点类型必须为ProceedingJoinPoint)
     * @return  目标方法执行结果
     * @throws Throwable
     */
    @Around("doLog()")
    public Object doAround(ProceedingJoinPoint jp)throws Throwable{
        //1.输出方法开始执行的时间点
        long before=System.currentTimeMillis();
        System.out.println("before:"+before);
        //2.执行目标业务执行链
        try {
            Object result = jp.proceed();
            System.out.println("目标方法执行结果：" + result);
            //3.输出方法结束执行的时间点
            long after = System.currentTimeMillis();
            System.out.println("After:" + after);
            saveUserLog(jp,1, null, (after-before));
            return result;
        }catch (Throwable e){
            long throwing=System.currentTimeMillis();
            saveUserLog(jp,0, e.getMessage(), (throwing-before));
            throw e;
        }
    }
    //基于目标逻辑去下面的方法。
    private void saveUserLog(ProceedingJoinPoint jp,int status,String error,long time) throws NoSuchMethodException, IOException {
        //1.获取用户行为日志
        //1.1获取ip地址(要想获取ip首先要获取request对象)
        ServletRequestAttributes requestAttributes=
                (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        String ip= requestAttributes.getRequest().getRemoteAddr();
        //1.2获取登录用户名
        String username= (String)SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        //1.3获取操作名：@RequiredLog注解中value属性的值
        //1.3.1获取目标类
        Class targetClass=jp.getTarget().getClass();
        //1.3.2获取目标方法
        MethodSignature signature = (MethodSignature)jp.getSignature();
        Method targetMethod=
                targetClass.getDeclaredMethod(signature.getName(),signature.getParameterTypes());
        //1.3.3获取注解
        RequiredLog annotation = targetMethod.getAnnotation(RequiredLog.class);
        //1.3.4获取操作名
        String operation=annotation.value();
        //1.4获取目标方法名
        String targetMethodName=targetClass.getName()+"."+targetMethod.getName();
        //1.5获取执行方法时传递的实际参数
        String params=new ObjectMapper().writeValueAsString(jp.getArgs());
        // 2.封装用户行为日志
        Log userLog=new Log();
        userLog.setIp(ip);
        userLog.setUsername(username);
        userLog.setCreatedTime(new Date());
        userLog.setOperation(operation);
        userLog.setMethod(targetMethodName);//pkg.className.methodName
        userLog.setParams(params);
        userLog.setStatus(status);
        userLog.setError(error);
        userLog.setTime(time);
        //3.记录用户行为日志(最后要传递给sso-system工程)
        log.info("用户行为日志 {}",userLog);
        remoteLogService.insertLog(userLog);
    }
    @Autowired
    private RemoteLogService remoteLogService;
}
