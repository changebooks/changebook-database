package io.github.changebooks.database.support;

import io.github.changebooks.code.jdbc.JdbcDriver;
import io.github.changebooks.code.jdbc.JdbcExecutor;
import io.github.changebooks.code.jdbc.Sharding;
import io.github.changebooks.code.jdbc.ShardingNum;
import io.github.changebooks.code.util.AssertUtils;
import io.github.changebooks.code.util.StringUtils;
import io.github.changebooks.database.autoconfigure.DsnProperties;
import io.github.changebooks.database.autoconfigure.ShardingNumProperties;
import io.github.changebooks.database.autoconfigure.ShardingProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通过默认的方法，或重写的子方法，创建实例
 *
 * @author changebooks@qq.com
 */
public class JdbcSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcSupport.class);

    /**
     * Jdbc执行
     *
     * @param jdbcDriver {@link JdbcDriver} 驱动
     * @return {@link JdbcExecutor} 实例
     */
    public JdbcExecutor jdbcExecutor(JdbcDriver jdbcDriver) {
        return new JdbcExecutor(jdbcDriver);
    }

    /**
     * Jdbc驱动
     *
     * @param dsnProperties {@link DsnProperties} 配置
     * @return {@link JdbcDriver} 实例
     */
    public JdbcDriver jdbcDriver(DsnProperties dsnProperties) {
        String url = StringUtils.trimSpace(dsnProperties.getUrl());
        String username = StringUtils.trimSpace(dsnProperties.getUsername());
        String password = dsnProperties.getPassword();

        LOGGER.info("JdbcSupport jdbcDriver, url: {}, username: {}", url, username);
        return new JdbcDriver(url, username, password);
    }

    /**
     * 单库分表
     *
     * @param shardingNumProperties {@link ShardingNumProperties} 配置
     * @return {@link ShardingNum} 实例
     */
    public ShardingNum shardingNum(ShardingNumProperties shardingNumProperties) {
        Integer tableSize = shardingNumProperties.getTableSize();
        AssertUtils.nonNull(tableSize, "tableSize");

        LOGGER.info("JdbcSupport shardingNum, tableSize: {}", tableSize);
        return new ShardingNum(tableSize);
    }

    /**
     * 分库分表
     *
     * @param shardingProperties {@link ShardingProperties} 配置
     * @return {@link Sharding} 实例
     */
    public Sharding sharding(ShardingProperties shardingProperties) {
        Integer dbSize = shardingProperties.getDbSize();
        Integer tableSize = shardingProperties.getTableSize();

        AssertUtils.nonNull(dbSize, "dbSize");
        AssertUtils.nonNull(tableSize, "tableSize");

        LOGGER.info("JdbcSupport sharding, dbSize: {}, tableSize: {}", dbSize, tableSize);
        return new Sharding(dbSize, tableSize);
    }

}
