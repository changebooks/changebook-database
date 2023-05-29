package io.github.changebooks.database.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 分库分表
 *
 * @author changebooks@qq.com
 */
@ConfigurationProperties(prefix = "sharding")
public class ShardingProperties {
    /**
     * 库数，2的幂次方
     */
    private Integer dbSize;

    /**
     * 每库表数，2的幂次方
     */
    private Integer tableSize;

    public Integer getDbSize() {
        return dbSize;
    }

    public void setDbSize(Integer dbSize) {
        this.dbSize = dbSize;
    }

    public Integer getTableSize() {
        return tableSize;
    }

    public void setTableSize(Integer tableSize) {
        this.tableSize = tableSize;
    }

}
