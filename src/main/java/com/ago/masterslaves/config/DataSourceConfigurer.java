package com.ago.masterslaves.config;

import com.ago.masterslaves.constant.DataSourceEnum;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName:DataSourceConfigurer
 * @Describe: 数据源配置类，在该类中生成多个数据源实例并将其注入到 ApplicationContext 中
 * @Data:2020/3/1916:33
 * @Author:Ago
 * @Version 1.0
 */
@Configuration
public class DataSourceConfigurer {

    @Value("${mybatis.mapper-locations}")
    private String mapperLocation;

    @Bean("master")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.dynamic.datasource.master")
    public DataSource master() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean("Slaves")
    @ConfigurationProperties(prefix = "spring.datasource.dynamic.datasource.slave")
    public DataSource read() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean("dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicDataSourceRoute dynamicRoutingDataSource = new DynamicDataSourceRoute();
        Map<Object,Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DataSourceEnum.MASTER,master());
        dataSourceMap.put(DataSourceEnum.SLAVES,read());
        //将master作为默认指定数据源
        dynamicRoutingDataSource.setDefaultTargetDataSource(master());
        //将slaves master 加入制定数据源集
        dynamicRoutingDataSource.setTargetDataSources(dataSourceMap);
        //将数据源key 放入数据源上下文中,终于判断数据源切换是否有效
        DynamicDataSourceContextHolder.dataSourceKeys.addAll(dataSourceMap.keySet());

        // 将slaves数据源放入集合中, 用于轮询
        DynamicDataSourceContextHolder.slavesSourceKey.addAll(dataSourceMap.keySet());
        DynamicDataSourceContextHolder.slavesSourceKey.remove(DataSourceEnum.MASTER);

        return dynamicRoutingDataSource;
    }
    /**
     * 配置 SqlSessionFactoryBean
     *  @ConfigurationProperties 在这里是为了将 MyBatis 的 mapper 位置和持久层接口的别名设置到
     *  Bean 的属性中，如果没有使用 *.xml 则可以不用该配置，否则将会产生 invalid bond statement 异常
     */
    @Bean
    @ConfigurationProperties(prefix = "mybatis")
    public SqlSessionFactoryBean sqlSessionFactoryBean() throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        // 配置数据源，此处配置为关键配置，如果没有将 dynamicDataSource 作为数据源则不能实现切换
        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
//        PathMatchingResourcePatternResolver resolve = new PathMatchingResourcePatternResolver();
//        Resource[] resources = resolve.getResources(mapperLocation);
//        sqlSessionFactoryBean.setMapperLocations(resources);
        return sqlSessionFactoryBean;
    }

    /**
     * 注入 DataSourceTransactionManager 用于事务管理
     * @return
     */
    public PlatformTransactionManager transactionManager(){
        return new DataSourceTransactionManager(dynamicDataSource());
    }
}
