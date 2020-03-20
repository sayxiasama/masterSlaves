package com.ago.masterslaves.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName:DynaminDataSourceAspect
 * @Describe:
 * @Data:2020/3/2010:36
 * @Author:Ago
 * @Version 1.0
 */
public class DynamicDataSourceAspect {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    private static final String[] QUERY_PREFIX = {"select"};

    @Pointcut("execution(* com.ago.masterslaves.mapper.*(..))")
    public void doAspect() {
    }

    @Before("doAspect()")
    public void switchDataSource(JoinPoint point) {
        boolean isQueryMethod = isQueryMethod(point.getSignature().getName());
        if (isQueryMethod) {
            DynamicDataSourceContextHolder.userSlavesDataSource();
            logger.info("Switch DataSource to [{}] in Method [{}]", DynamicDataSourceContextHolder.getDatasourceKey(), point.getSignature());
        }
    }

    @After("doAspect")
    public void restoreDataSource(JoinPoint point) {
        DynamicDataSourceContextHolder.clearDataSource();
        logger.info("Restore DataSource to [{}] in Method [{}]",DynamicDataSourceContextHolder.getDatasourceKey(), point.getSignature());
    }

    private boolean isQueryMethod(String methodName) {
        for (String prefix : QUERY_PREFIX) {
            if (methodName.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }
}
