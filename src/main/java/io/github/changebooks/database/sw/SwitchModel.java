package io.github.changebooks.database.sw;

import io.github.changebooks.code.base.JsonParser;

import java.io.Serializable;
import java.util.Date;

/**
 * Model
 *
 * @author changebooks@qq.com
 */
public final class SwitchModel implements Serializable {
    /**
     * 自增id
     */
    private Long id;

    /**
     * 主表id
     */
    private Long baseId;

    /**
     * 开关id
     */
    private Integer switchId;

    /**
     * 值，0-关、1-开
     */
    private Integer switchValue;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBaseId() {
        return baseId;
    }

    public void setBaseId(Long baseId) {
        this.baseId = baseId;
    }

    public Integer getSwitchId() {
        return switchId;
    }

    public void setSwitchId(Integer switchId) {
        this.switchId = switchId;
    }

    public Integer getSwitchValue() {
        return switchValue;
    }

    public void setSwitchValue(Integer switchValue) {
        this.switchValue = switchValue;
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
