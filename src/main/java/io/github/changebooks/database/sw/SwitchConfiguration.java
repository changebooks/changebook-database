package io.github.changebooks.database.sw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 指定 Spring 和 Mybatis 包
 *
 * @author changebooks@qq.com
 */
@Configuration
@ComponentScan(basePackages = {"io.github.changebooks.database.sw"})
@MapperScan(basePackages = {"io.github.changebooks.database.sw"})
public class SwitchConfiguration {
}
