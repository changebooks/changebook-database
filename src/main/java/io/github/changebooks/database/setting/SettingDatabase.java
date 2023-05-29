package io.github.changebooks.database.setting;

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
public class SettingDatabase {

    private SettingMapper settingMapper;

    public SettingDatabase(SettingMapper settingMapper) {
        AssertUtils.nonNull(settingMapper, "settingMapper");

        this.settingMapper = settingMapper;
    }

    /**
     * 获取全部配置
     *
     * @return [ 配置id, 配置值 ]
     */
    public Map<Integer, String> getPairs() {
        List<SettingModel> list = settingMapper.findAll();
        if (Check.isNull(list)) {
            return null;
        }

        Map<Integer, String> result = new HashMap<>(list.size());

        for (SettingModel record : list) {
            if (Check.nonNull(record)) {
                Integer settingId = record.getSettingId();
                String settingValue = record.getSettingValue();
                if (Check.isPositive(settingId) && Check.nonNull(settingValue)) {
                    result.put(settingId, settingValue);
                }
            }
        }

        return result;
    }

    /**
     * 通过“配置id”，获取“配置值”
     *
     * @param settingId 配置id
     * @return 配置值
     */
    public String getValue(Integer settingId) {
        AssertUtils.isPositive(settingId, "settingId");

        SettingModel record = settingMapper.find(settingId);
        if (Check.isNull(record)) {
            return null;
        } else {
            return record.getSettingValue();
        }
    }

    /**
     * 通过“配置id”，新增或修改“配置值”
     *
     * @param settingId    配置id
     * @param settingValue 配置值
     * @return 成功？
     */
    public boolean setValue(Integer settingId, @Nullable String settingValue) {
        AssertUtils.isPositive(settingId, "settingId");

        String nowValue = getValue(settingId);
        if (Check.isNull(nowValue)) {
            if (Check.isNull(settingValue)) {
                return true;
            } else {
                return insert(settingId, settingValue);
            }
        } else {
            if (nowValue.equals(settingValue)) {
                return true;
            } else {
                return update(settingId, settingValue);
            }
        }
    }

    /**
     * 新增一条记录
     *
     * @param settingId    配置id
     * @param settingValue 配置值
     * @return 成功？
     */
    public boolean insert(Integer settingId, String settingValue) {
        AssertUtils.isPositive(settingId, "settingId");
        AssertUtils.nonNull(settingValue, "settingValue");

        int affectedRows = settingMapper.insert(settingId, settingValue);
        return affectedRows > 0;
    }

    /**
     * 通过“配置id”，修改一条记录
     *
     * @param settingId    配置id
     * @param settingValue 配置值
     * @return 成功？
     */
    public boolean update(Integer settingId, @Nullable String settingValue) {
        AssertUtils.isPositive(settingId, "settingId");

        int affectedRows = settingMapper.update(settingValue, settingId);
        if (affectedRows > 0) {
            return true;
        } else {
            String nowValue = getValue(settingId);
            return Check.isNull(nowValue) ? Check.isNull(settingValue) : nowValue.equals(settingValue);
        }
    }

}
