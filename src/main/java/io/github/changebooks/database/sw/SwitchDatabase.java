package io.github.changebooks.database.sw;

import io.github.changebooks.code.base.Check;
import io.github.changebooks.code.util.AssertUtils;
import io.github.changebooks.code.util.BooleanCast;

import java.util.*;

/**
 * Database
 *
 * @author changebooks@qq.com
 */
public class SwitchDatabase {

    private SwitchMapper switchMapper;

    public SwitchDatabase(SwitchMapper switchMapper) {
        AssertUtils.nonNull(switchMapper, "switchMapper");

        this.switchMapper = switchMapper;
    }

    /**
     * 通过“主表id”，获取全部开关
     *
     * @param tableName 表名
     * @param baseId    主表id
     * @return [ 开关id, 值 ]
     */
    public Map<Integer, Boolean> getPairs(String tableName, Long baseId) {
        AssertUtils.nonEmpty(tableName, "tableName");
        AssertUtils.isPositive(baseId, "baseId");

        List<SwitchModel> list = switchMapper.findAll(tableName, baseId);
        if (Check.isNull(list)) {
            return null;
        }

        Map<Integer, Boolean> result = new HashMap<>(list.size());

        for (SwitchModel record : list) {
            if (Check.nonNull(record)) {
                Integer switchId = record.getSwitchId();
                Integer switchValue = record.getSwitchValue();
                if (Check.isPositive(switchId) && Check.nonNull(switchValue)) {
                    result.put(switchId, BooleanCast.fromInt(switchValue));
                }
            }
        }

        return result;
    }

    /**
     * 通过“开关id”和“值”，查询“主表id”列表
     *
     * @param tableName   表名
     * @param switchId    开关id
     * @param switchValue 值，false-关、true-开
     * @param startRow    起始行数
     * @param pageSize    每页条数
     * @return list
     */
    public List<Long> findList(String tableName, Integer switchId, Boolean switchValue, Long startRow, Integer pageSize) {
        AssertUtils.nonEmpty(tableName, "tableName");
        AssertUtils.isPositive(switchId, "switchId");
        AssertUtils.nonNull(switchValue, "switchValue");
        AssertUtils.nonNegative(startRow, "startRow");
        AssertUtils.isPositive(pageSize, "pageSize");

        List<SwitchModel> list = switchMapper.findList(tableName, switchId, BooleanCast.toInt(switchValue), startRow, pageSize);
        if (Check.isEmpty(list)) {
            return null;
        }

        Set<Long> result = new HashSet<>();

        for (SwitchModel record : list) {
            if (Check.nonNull(record)) {
                Long baseId = record.getBaseId();
                if (Check.isPositive(baseId)) {
                    result.add(baseId);
                }
            }
        }

        return new ArrayList<>(result);
    }

    /**
     * 通过“主表id”和“开关id”，获取“值”
     *
     * @param tableName 表名
     * @param baseId    主表id
     * @param switchId  开关id
     * @return 值，false-关、true-开
     */
    public Boolean getValue(String tableName, Long baseId, Integer switchId) {
        AssertUtils.nonEmpty(tableName, "tableName");
        AssertUtils.isPositive(baseId, "baseId");
        AssertUtils.isPositive(switchId, "switchId");

        SwitchModel record = switchMapper.find(tableName, baseId, switchId);
        if (Check.isNull(record)) {
            return null;
        } else {
            Integer switchValue = record.getSwitchValue();
            return BooleanCast.fromInt(switchValue);
        }
    }

    /**
     * 通过“主表id”和“开关id”，新增或修改“值”
     *
     * @param tableName   表名
     * @param baseId      主表id
     * @param switchId    开关id
     * @param switchValue 值，false-关、true-开
     * @return 成功？
     */
    public boolean setValue(String tableName, Long baseId, Integer switchId, Boolean switchValue) {
        AssertUtils.nonEmpty(tableName, "tableName");
        AssertUtils.isPositive(baseId, "baseId");
        AssertUtils.isPositive(switchId, "switchId");
        AssertUtils.nonNull(switchValue, "switchValue");

        Boolean nowValue = getValue(tableName, baseId, switchId);
        if (Check.isNull(nowValue)) {
            return insert(tableName, baseId, switchId, switchValue);
        } else {
            if (nowValue.equals(switchValue)) {
                return true;
            } else {
                return update(tableName, baseId, switchId, switchValue);
            }
        }
    }

    /**
     * 新增一条记录
     *
     * @param tableName   表名
     * @param baseId      主表id
     * @param switchId    开关id
     * @param switchValue 值，false-关、true-开
     * @return 成功？
     */
    public boolean insert(String tableName, Long baseId, Integer switchId, Boolean switchValue) {
        AssertUtils.nonEmpty(tableName, "tableName");
        AssertUtils.isPositive(baseId, "baseId");
        AssertUtils.isPositive(switchId, "switchId");
        AssertUtils.nonNull(switchValue, "switchValue");

        int affectedRows = switchMapper.insert(tableName, baseId, switchId, BooleanCast.toInt(switchValue));
        return affectedRows > 0;
    }

    /**
     * 通过“主表id”和“开关id”，修改一条记录
     *
     * @param tableName   表名
     * @param baseId      主表id
     * @param switchId    开关id
     * @param switchValue 值，false-关、true-开
     * @return 成功？
     */
    public boolean update(String tableName, Long baseId, Integer switchId, Boolean switchValue) {
        AssertUtils.nonEmpty(tableName, "tableName");
        AssertUtils.isPositive(baseId, "baseId");
        AssertUtils.isPositive(switchId, "switchId");
        AssertUtils.nonNull(switchValue, "switchValue");

        int affectedRows = switchMapper.update(tableName, BooleanCast.toInt(switchValue), baseId, switchId);
        if (affectedRows > 0) {
            return true;
        } else {
            Boolean nowValue = getValue(tableName, baseId, switchId);
            return switchValue.equals(nowValue);
        }
    }

}
