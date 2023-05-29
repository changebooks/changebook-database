package io.github.changebooks.database.attribute;

import io.github.changebooks.code.base.JsonParser;

import java.io.Serializable;
import java.util.Date;

/**
 * Model
 *
 * @author changebooks@qq.com
 */
public final class AttributeModel implements Serializable {
    /**
     * 自增id
     */
    private Long id;

    /**
     * 主表id
     */
    private Long baseId;

    /**
     * 属性id
     */
    private Integer attributeId;

    /**
     * 值
     */
    private String attributeValue;

    /**
     * 版本
     */
    private Integer version;

    /**
     * 新记录？
     * true-新增、false-修改
     */
    private Boolean newRecord;

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

    public Integer getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Integer attributeId) {
        this.attributeId = attributeId;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Boolean getNewRecord() {
        return newRecord;
    }

    public void setNewRecord(Boolean newRecord) {
        this.newRecord = newRecord;
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
