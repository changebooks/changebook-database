package io.github.changebooks.database.support;

import io.github.changebooks.database.attribute.AttributeDatabase;
import io.github.changebooks.database.attribute.AttributeMapper;
import io.github.changebooks.database.setting.SettingDatabase;
import io.github.changebooks.database.setting.SettingMapper;
import io.github.changebooks.database.sw.SwitchDatabase;
import io.github.changebooks.database.sw.SwitchMapper;

/**
 * 通过默认的方法，或重写的子方法，创建实例
 *
 * @author changebooks@qq.com
 */
public class DatabaseSupport {
    /**
     * 扩展表
     *
     * @param attributeMapper {@link AttributeMapper} MyBatis Mapper
     * @return {@link AttributeDatabase} 实例
     */
    public AttributeDatabase attributeDatabase(AttributeMapper attributeMapper) {
        return new AttributeDatabase(attributeMapper);
    }

    /**
     * 开关表
     *
     * @param switchMapper {@link SwitchMapper} MyBatis Mapper
     * @return {@link SwitchDatabase} 实例
     */
    public SwitchDatabase switchDatabase(SwitchMapper switchMapper) {
        return new SwitchDatabase(switchMapper);
    }

    /**
     * 配置表
     *
     * @param settingMapper {@link SettingMapper} MyBatis Mapper
     * @return {@link SettingDatabase} 实例
     */
    public SettingDatabase settingDatabase(SettingMapper settingMapper) {
        return new SettingDatabase(settingMapper);
    }

}
