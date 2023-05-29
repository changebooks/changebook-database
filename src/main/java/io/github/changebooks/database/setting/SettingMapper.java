package io.github.changebooks.database.setting;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MyBatis Mapper
 *
 * @author changebooks@qq.com
 */
@Repository
public interface SettingMapper {
    /**
     * 获取全部记录
     *
     * @return list
     */
    @Select("SELECT setting_id, setting_value, created_at, updated_at FROM setting")
    @Results({
            @Result(id = true, column = "setting_id", jdbcType = JdbcType.INTEGER, property = "settingId"),
            @Result(column = "setting_value", jdbcType = JdbcType.VARCHAR, property = "settingValue"),
            @Result(column = "created_at", jdbcType = JdbcType.TIMESTAMP, property = "createdAt"),
            @Result(column = "updated_at", jdbcType = JdbcType.TIMESTAMP, property = "updatedAt")
    })
    List<SettingModel> findAll();

    /**
     * 通过“配置id”，获取一条记录
     *
     * @param settingId 配置id
     * @return 记录
     */
    @Select("SELECT setting_id, setting_value, created_at, updated_at FROM setting " +
            "WHERE setting_id = #{settingId,jdbcType=INTEGER}")
    @Results({
            @Result(id = true, column = "setting_id", jdbcType = JdbcType.INTEGER, property = "settingId"),
            @Result(column = "setting_value", jdbcType = JdbcType.VARCHAR, property = "settingValue"),
            @Result(column = "created_at", jdbcType = JdbcType.TIMESTAMP, property = "createdAt"),
            @Result(column = "updated_at", jdbcType = JdbcType.TIMESTAMP, property = "updatedAt")
    })
    SettingModel find(@Param("settingId") int settingId);

    /**
     * 新增一条记录
     *
     * @param settingId    配置id
     * @param settingValue 配置值
     * @return 影响行数
     */
    @Insert("INSERT INTO setting (setting_id, setting_value) " +
            "VALUES (#{settingId,jdbcType=INTEGER}, #{settingValue,jdbcType=VARCHAR})")
    int insert(@Param("settingId") int settingId, @Param("settingValue") String settingValue);

    /**
     * 通过“配置id”，修改一条记录
     *
     * @param settingValue 配置值
     * @param settingId    配置id
     * @return 影响行数
     */
    @Update("UPDATE setting SET setting_value = #{settingValue,jdbcType=VARCHAR} " +
            "WHERE setting_id = #{settingId,jdbcType=INTEGER}")
    int update(@Param("settingValue") String settingValue, @Param("settingId") int settingId);

}
