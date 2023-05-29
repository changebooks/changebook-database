package io.github.changebooks.database.attribute;

import io.github.changebooks.code.base.Assert;
import io.github.changebooks.code.base.Check;
import io.github.changebooks.code.util.AssertUtils;

import java.util.*;

/**
 * 常用工具
 *
 * @author changebooks@qq.com
 */
public final class AttributeUtils {
    /**
     * 比较新老数据，获取新增和修改列表
     *
     * @param baseId 主表id
     * @param newMap 新数据，将写库，[ 属性id, 新值 ]
     * @param oldMap 老数据，已存在，[ 属性id, 老记录 ]
     * @return 新增和修改列表
     */
    public static List<AttributeModel> compare(Long baseId, Map<Integer, String> newMap, Map<Integer, AttributeModel> oldMap) {
        AssertUtils.isPositive(baseId, "baseId");
        AssertUtils.nonEmpty(newMap, "newMap");

        List<AttributeModel> result = new ArrayList<>();

        for (Map.Entry<Integer, String> entry : newMap.entrySet()) {
            if (Check.isNull(entry)) {
                continue;
            }

            Integer attributeId = entry.getKey();
            AssertUtils.isPositive(attributeId, "attributeId");

            String newValue = entry.getValue();
            AttributeModel oldRecord = Check.nonNull(oldMap) ? oldMap.get(attributeId) : null;

            if (ignore(newValue, oldRecord)) {
                continue;
            }

            AttributeModel record = new AttributeModel();
            record.setAttributeValue(newValue);

            if (Check.nonNull(oldRecord)) {
                // Update
                Assert.checkArgument(baseId.equals(oldRecord.getBaseId()),
                        String.format("%d must be equal than %d", baseId, oldRecord.getBaseId()));

                Long id = oldRecord.getId();
                Integer version = oldRecord.getVersion();
                AssertUtils.isPositive(id, "id");
                AssertUtils.isPositive(version, "version");

                record.setId(id);
                record.setVersion(version);
                record.setNewRecord(false);
            } else {
                // Insert
                record.setBaseId(baseId);
                record.setAttributeId(attributeId);
                record.setNewRecord(true);
            }

            result.add(record);
        }

        return result;
    }

    /**
     * 忽略？
     *
     * @param newValue  新值
     * @param oldRecord 老记录
     * @return 放弃新增和修改？
     */
    public static boolean ignore(String newValue, AttributeModel oldRecord) {
        if (Check.isNull(newValue)) {
            // New Value is Null

            if (Check.isNull(oldRecord)) {
                // Old Record is Null
                return true;
            }

            // Old Value is Null
            return Check.isNull(oldRecord.getAttributeValue());
        } else {
            // New Value non Null

            if (Check.isNull(oldRecord)) {
                // Old Record is Null
                return false;
            }

            // New Value eq Old Value
            return newValue.equals(oldRecord.getAttributeValue());
        }
    }

    /**
     * AttributeModel list to [ attributeId, AttributeModel ] map
     *
     * @param list AttributeModel
     * @return [ attributeId, AttributeModel ]
     */
    public static Map<Integer, AttributeModel> listToMap(List<AttributeModel> list) {
        if (Check.isEmpty(list)) {
            return null;
        }

        Map<Integer, AttributeModel> result = new HashMap<>(list.size());

        for (AttributeModel record : list) {
            if (Check.nonNull(record)) {
                Integer attributeId = record.getAttributeId();
                if (Check.isPositive(attributeId)) {
                    result.put(attributeId, record);
                }
            }
        }

        return result;
    }

    /**
     * 获取全部主表id
     *
     * @param list AttributeModel
     * @return baseId list or null
     */
    public static List<Long> baseId(List<AttributeModel> list) {
        if (Check.isEmpty(list)) {
            return null;
        }

        Set<Long> result = new HashSet<>();

        for (AttributeModel record : list) {
            if (Check.nonNull(record)) {
                Long baseId = record.getBaseId();
                if (Check.isPositive(baseId)) {
                    result.add(baseId);
                }
            }
        }

        return new ArrayList<>(result);
    }

}
