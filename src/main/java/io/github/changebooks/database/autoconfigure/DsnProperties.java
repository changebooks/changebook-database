package io.github.changebooks.database.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Data Source Name
 *
 * @author changebooks@qq.com
 */
@ConfigurationProperties(prefix = "dsn")
public class DsnProperties {
    /**
     * 域名或Ip、数据库名、参数
     * 如，jdbc:mysql://127.0.0.1:3306/database?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai&useAffectedRows=true&allowMultiQueries=true
     */
    private String url;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
