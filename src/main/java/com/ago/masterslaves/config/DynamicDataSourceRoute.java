package com.ago.masterslaves.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @ClassName:DynamicDataSourceRoute
 * @Describe:  数据库路由
 * @Data:2020/3/1915:55
 * @Author:Ago
 * @Version 1.0
 */
public class DynamicDataSourceRoute extends AbstractRoutingDataSource {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceRoute.class);

    @Override
    protected Object determineCurrentLookupKey() {

        logger.info(" into dynamicDataSourceRote ----------------------------------{}",DynamicDataSourceContextHolder.getDatasourceKey());

        return DynamicDataSourceContextHolder.getDatasourceKey();
    }
}
