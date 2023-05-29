package io.github.changebooks.database.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 单库分表
 *
 * @author changebooks@qq.com
 */
@ConfigurationProperties(prefix = "sharding-num")
public class ShardingNumProperties {
    /**
     * 表数，2的幂次方
     */
    private Integer tableSize;

    public Integer getTableSize() {
        return tableSize;
    }

    public void setTableSize(Integer tableSize) {
        this.tableSize = tableSize;
    }

}
