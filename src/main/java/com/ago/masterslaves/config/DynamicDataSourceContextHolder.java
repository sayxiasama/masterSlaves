package com.ago.masterslaves.config;

import com.ago.masterslaves.constant.DataSourceEnum;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName: DynamicDataSourceContextHolder(动态数据源上下文保持器)
 * @Describe:
 * 该类负责处理多数据源切换
 * @Data: 2020/3/1915:58
 * @Author: Ago
 * @Version 1.0
 */
public class DynamicDataSourceContextHolder {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);

    /* 用户切换数据源时不会被其他程序修改 */
    private static Lock lock = new ReentrantLock();

    /* 用户轮询的计数器 */
    private static int count = 0;

    /* 为每个线程维护变量，以避免影响其他线程 */
    private static final ThreadLocal<Object> CONTEXT_HOLDER =  ThreadLocal.withInitial(DataSourceEnum.MASTER);

    /* 所有数据源集合 */
    public static List<Object> dataSourceKeys = Lists.newArrayList();

    /* 所有从库数据源 */
    public static List<Object> slavesSourceKey = Lists.newArrayList();


    /**
     * 选择数据源
     * @param key
     */
    public static void setKey(String key){
        CONTEXT_HOLDER.set(key);
    }

    /**
     * 默认选择主数据源
     */
    public static void userMaster(){
        CONTEXT_HOLDER.set(DataSourceEnum.MASTER);
    }

    /**
     * 轮询的方式使用从库
     */
    public static void userSlavesDataSource(){
        lock.lock();
        try {
            int dataSourceIndex = count % slavesSourceKey.size();
            CONTEXT_HOLDER.set(String.valueOf(slavesSourceKey.get(dataSourceIndex)));
            count ++;
        } catch (Exception e) {
            logger.error(" change slaves fail , change master");
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    public static Object getDatasourceKey(){
        return  CONTEXT_HOLDER.get();
    }

    public static void clearDataSource(){
        CONTEXT_HOLDER.remove();
    }

    public static boolean containDataSourceKey(String key){
        return dataSourceKeys.contains(key);
    }
}
