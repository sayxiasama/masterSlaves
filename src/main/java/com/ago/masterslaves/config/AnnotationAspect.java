package com.ago.masterslaves.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @ClassName:AnnotationApsect
 * @Describe:
 * @Data:2020/3/2315:53
 * @Author:Ago
 * @Version 1.0
 */
@Aspect
@Component
public class AnnotationAspect {
    private static final Logger logger = LoggerFactory.getLogger(AnnotationAspect.class);
    public static  final String[] QUERY_PREFIX = {"select"};

    @Pointcut("@within(com.ago.masterslaves.bean.SwitchDataSourceAnnotation)")
    public void switchDataSource(){}


    @Around("switchDataSource()")
    public Object getSwitchDataSourceResult(ProceedingJoinPoint point) throws Throwable {
        boolean isQuery = isQuery(point.getSignature().getName());
        if(isQuery){
            DynamicDataSourceContextHolder.userSlavesDataSource();
            logger.info("switch DataSource to slaves -----------------",DynamicDataSourceContextHolder.getDatasourceKey());
        }
        Object o = point.proceed();
        DynamicDataSourceContextHolder.clearDataSource();
        return o;
    }
    public boolean isQuery(String methodName){
        for (String prefix : QUERY_PREFIX) {
            if(methodName.startsWith(prefix)){
                return true;
            }
        }
        return false;
    }
}
