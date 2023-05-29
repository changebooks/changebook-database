package io.github.changebooks.database.sw;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启 Spring 和 Mybatis 引包
 *
 * @author changebooks@qq.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({SwitchConfiguration.class})
public @interface EnableDatabaseSwitch {
}
