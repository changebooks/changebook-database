# changebook-database
### 数据库

### pom.xml
```
<dependency>
  <groupId>io.github.changebooks</groupId>
  <artifactId>changebook-database</artifactId>
  <version>1.0.1</version>
</dependency>
```

### mybatis.xml
```
<configuration>
    <plugins>
        <plugin interceptor="io.github.changebooks.database.debug.DebugInterceptor">
            <property name="databaseType" value="mysql"/>
        </plugin>
    </plugins>
</configuration>
```

### Data Source Name
```
dsn:
  url: jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai&useAffectedRows=true&allowMultiQueries=true
  username: root
  password: 123456
```

### 单库分表
```
sharding-num:
  table-size: 8
```

### 分库分表
```
sharding:
  db-size: 8
  table-size: 8
```

### 开启 Jdbc
```
@Configuration
@EnableConfigurationProperties({DsnProperties.class, ShardingNumProperties.class, ShardingProperties.class})
public class JdbcSupportImpl extends JdbcSupport {

    @Bean
    @Override
    public JdbcExecutor jdbcExecutor(JdbcDriver jdbcDriver) {
        return super.jdbcExecutor(jdbcDriver);
    }

    @Bean
    @Override
    public JdbcDriver jdbcDriver(DsnProperties dsnProperties) {
        return super.jdbcDriver(dsnProperties);
    }

    @Bean
    @Override
    public ShardingNum shardingNum(ShardingNumProperties shardingNumProperties) {
        return super.shardingNum(shardingNumProperties);
    }

    @Bean
    @Override
    public Sharding sharding(ShardingProperties shardingProperties) {
        return super.sharding(shardingProperties);
    }

}
```

### Attribute 表结构
```
CREATE TABLE ***_attribute
(
    id              bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
    base_id         bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '主表id',
    attribute_id    int(11) unsigned    NOT NULL DEFAULT '0' COMMENT '属性id',
    attribute_value varchar(255)                 DEFAULT NULL COMMENT '值',
    version         int(11) unsigned    NOT NULL DEFAULT '1' COMMENT '版本',
    created_at      datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at      datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_base_id_attribute_id (base_id, attribute_id),
    KEY idx_attribute_id_attribute_value (attribute_id, attribute_value)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT = '***扩展表';
```

### Switch 表结构
```
CREATE TABLE ***_switch
(
    id           bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
    base_id      bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '主表id',
    switch_id    int(11) unsigned    NOT NULL DEFAULT '0' COMMENT '开关id',
    switch_value int(11) unsigned    NOT NULL DEFAULT '0' COMMENT '值，0-关、1-开',
    created_at   datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at   datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_base_id_switch_id (base_id, switch_id),
    KEY idx_switch_id_switch_value (switch_id, switch_value)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT = '***开关表';
```

### Setting 表结构
```
CREATE TABLE setting
(
    setting_id    int(11) unsigned NOT NULL DEFAULT '0' COMMENT '配置id',
    setting_value varchar(255)              DEFAULT NULL COMMENT '值',
    created_at    datetime         NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at    datetime         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (setting_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT = '配置表';
```

### 开启 Database
```
@Configuration
@EnableDatabaseAttribute
@EnableDatabaseSwitch
@EnableDatabaseSetting
public class DatabaseSupportImpl extends DatabaseSupport {

    @Bean
    @Override
    public AttributeDatabase attributeDatabase(AttributeMapper attributeMapper) {
        return super.attributeDatabase(attributeMapper);
    }

    @Bean
    @Override
    public SwitchDatabase switchDatabase(SwitchMapper switchMapper) {
        return super.switchDatabase(switchMapper);
    }

    @Bean
    @Override
    public SettingDatabase settingDatabase(SettingMapper settingMapper) {
        return super.settingDatabase(settingMapper);
    }

}
```
