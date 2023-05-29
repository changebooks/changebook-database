package io.github.changebooks.database.setting;

import io.github.changebooks.code.base.JsonParser;

import java.io.Serializable;
import java.util.Date;

/**
 * Model
 *
 * @author changebooks@qq.com
 */
public final class SettingModel implements Serializable {
    /**
     * 配置id
     */
    private Integer settingId;

    /**
     * 值
     */
    private String settingValue;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

    @Override
    public String toString() {
        return JsonParser.toJson(this);
    }

    public Integer getSettingId() {
        return settingId;
    }

    public void setSettingId(Integer settingId) {
        this.settingId = settingId;
    }

    public String getSettingValue() {
        return settingValue;
    }

    public void setSettingValue(String settingValue) {
        this.settingValue = settingValue;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}
