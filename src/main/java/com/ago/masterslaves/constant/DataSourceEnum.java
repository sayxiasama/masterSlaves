package com.ago.masterslaves.constant;

import java.util.function.Supplier;

/**
 * @describe：  数据库类.
 */
public enum DataSourceEnum implements Supplier<DataSourceEnum> {
    MASTER,SLAVES;

    @Override
    public DataSourceEnum get() {
        return MASTER;
    }
}
