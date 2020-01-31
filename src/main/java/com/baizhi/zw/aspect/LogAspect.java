package com.baizhi.zw.aspect;

import com.baizhi.zw.annotation.LogAnnotation;
import com.baizhi.zw.entity.Log;
import com.baizhi.zw.service.LogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.UUID;

@Component
@Aspect
public class LogAspect {
    @Autowired
    LogService logService;
    @Autowired
    HttpSession session;
    @Around(value = "@annotation(com.baizhi.zw.annotation.LogAnnotation)")
    public Object addLog(ProceedingJoinPoint proceedingJoinPoint){
        // 时间
        Date date = new Date();
        // 人物
        String name = (String) session.getAttribute("username");
        // 获取自定义注解中的值
        // Signature 中保存了该类该方法所有的信息
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        LogAnnotation annotation = signature.getMethod().getAnnotation(LogAnnotation.class);
        String flag = null;
        try {
            // proceed 表示该方法执行结果
            Object proceed = proceedingJoinPoint.proceed();
            flag="1";
             return proceed;
        } catch (Throwable throwable) {
            flag="2";
            throwable.printStackTrace();
        }finally {
            Log log = new Log(UUID.randomUUID().toString().replace("-",""),name,annotation.value(),date,flag);
            logService.insertOne(log);
        }
        return null;
    }
}
