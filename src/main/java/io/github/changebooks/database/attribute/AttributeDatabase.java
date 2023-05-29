package io.github.changebooks.database.attribute;

import io.github.changebooks.code.base.Check;
import io.github.changebooks.code.util.AssertUtils;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Database
 *
 * @author changebooks@qq.com
 */
public class AttributeDatabase {

    private AttributeMapper attributeMapper;

    public AttributeDatabase(AttributeMapper attributeMapper) {
        AssertUtils.nonNull(attributeMapper, "attributeMapper");

        this.attributeMapper = attributeMapper;
    }

    /**
     * 通过“主表id”，获取全部属性
     *
     * @param tableName 表名
     * @param baseId    主表id
     * @return [ 属性id, 属性值 ]
     */
    public Map<Integer, String> getPairs(String tableName, Long baseId) {
        List<AttributeModel> list = findAll(tableName, baseId);
        if (Check.isEmpty(list)) {
            return null;
        }

        Map<Integer, String> result = new HashMap<>(list.size());

        for (AttributeModel record : list) {
            if (Check.nonNull(record)) {
                Integer attributeId = record.getAttributeId();
                String attributeValue = record.getAttributeValue();
                if (Check.isPositive(attributeId) && Check.nonNull(attributeValue)) {
                    result.put(attributeId, attributeValue);
                }
            }
        }

        return result;
    }

    /**
     * 通过“主表id”和“属性id”，获取属性值
     *
     * @param tableName   表名
     * @param baseId      主表id
     * @param attributeId 属性id
     * @return 属性值
     */
    public String getValue(String tableName, Long baseId, Integer attributeId) {
        AttributeModel record = find(tableName, baseId, attributeId);
        return Check.nonNull(record) ? record.getAttributeValue() : null;
    }

    /**
     * 批量保存
     *
     * @param tableName    表名
     * @param baseId       主表id
     * @param attributeMap [ 属性id, 属性值 ]
     * @return 影响行数
     */
    public int batchSave(String tableName, Long baseId, Map<Integer, String> attributeMap) {
        AssertUtils.nonEmpty(attributeMap, "attributeMap");

        List<AttributeModel> oldList = findAll(tableName, baseId);
        Map<Integer, AttributeModel> oldMap = AttributeUtils.listToMap(oldList);

        List<AttributeModel> list = AttributeUtils.compare(baseId, attributeMap, oldMap);
        if (Check.nonEmpty(list)) {
            return batchSave(tableName, list);
        } else {
            return 0;
        }
    }

    /**
     * 批量保存
     *
     * @param tableName 表名
     * @param list      列表
     * @return 影响行数
     */
    public int batchSave(String tableName, List<AttributeModel> list) {
        int totalRows = 0;

        if (Check.isEmpty(list)) {
            return totalRows;
        }

        for (AttributeModel record : list) {
            int affectedRows = replace(tableName, record);
            if (affectedRows > 0) {
                totalRows += affectedRows;
            } else {
                throw new AttributeNonAffectedRowsException(record);
            }
        }

        return totalRows;
    }

    /**
     * 新增或修改一条记录
     *
     * @param tableName 表名
     * @param record    记录
     * @return 影响行数
     */
    public int replace(String tableName, AttributeModel record) {
        AssertUtils.nonNull(record, "record");

        Boolean newRecord = record.getNewRecord();
        AssertUtils.nonNull(newRecord, "newRecord");

        Long id = record.getId();
        Long baseId = record.getBaseId();
        Integer attributeId = record.getAttributeId();
        String attributeValue = record.getAttributeValue();
        Integer version = record.getVersion();

        if (Check.isTrue(newRecord)) {
            return insert(tableName, baseId, attributeId, attributeValue);
        } else {
            return update(tableName, id, version, attributeValue);
        }
    }

    /**
     * 通过“主表id”，获取全部记录
     *
     * @param tableName 表名
     * @param baseId    主表id
     * @return list
     */
    public List<AttributeModel> findAll(String tableName, Long baseId) {
        AssertUtils.nonEmpty(tableName, "tableName");
        AssertUtils.isPositive(baseId, "baseId");

        return attributeMapper.findAll(tableName, baseId);
    }

    /**
     * 通过“主表id”和“属性id”，获取一条记录
     *
     * @param tableName   表名
     * @param baseId      主表id
     * @param attributeId 属性id
     * @return 记录
     */
    public AttributeModel find(String tableName, Long baseId, Integer attributeId) {
        AssertUtils.nonEmpty(tableName, "tableName");
        AssertUtils.isPositive(baseId, "baseId");
        AssertUtils.isPositive(attributeId, "attributeId");

        return attributeMapper.find(tableName, baseId, attributeId);
    }

    /**
     * 通过“属性id”和“属性值”，查询列表
     *
     * @param tableName      表名
     * @param attributeId    属性id
     * @param attributeValue 属性值
     * @param startRow       起始行数
     * @param pageSize       每页条数
     * @return list
     */
    public List<AttributeModel> findList(String tableName, Integer attributeId, String attributeValue, Long startRow, Integer pageSize) {
        AssertUtils.nonEmpty(tableName, "tableName");
        AssertUtils.isPositive(attributeId, "attributeId");
        AssertUtils.nonEmpty(attributeValue, "attributeValue");
        AssertUtils.nonNegative(startRow, "startRow");
        AssertUtils.isPositive(pageSize, "pageSize");

        return attributeMapper.findList(tableName, attributeId, attributeValue, startRow, pageSize);
    }

    /**
     * 新增一条记录
     *
     * @param tableName      表名
     * @param baseId         主表id
     * @param attributeId    属性id
     * @param attributeValue 属性值
     * @return 影响行数
     */
    public int insert(String tableName, Long baseId, Integer attributeId, @Nullable String attributeValue) {
        AssertUtils.nonEmpty(tableName, "tableName");
        AssertUtils.isPositive(baseId, "baseId");
        AssertUtils.isPositive(attributeId, "attributeId");

        return attributeMapper.insert(tableName, baseId, attributeId, attributeValue);
    }

    /**
     * 通过“自增id”和“版本”，修改一条记录
     *
     * @param tableName      表名
     * @param id             自增id
     * @param version        版本
     * @param attributeValue 属性值
     * @return 影响行数
     */
    public int update(String tableName, Long id, Integer version, @Nullable String attributeValue) {
        AssertUtils.nonEmpty(tableName, "tableName");
        AssertUtils.isPositive(id, "id");
        AssertUtils.isPositive(version, "version");

        return attributeMapper.update(tableName, attributeValue, id, version);
    }

}
