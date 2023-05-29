package io.github.changebooks.database.sw;

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
public interface SwitchMapper {
    /**
     * 通过“主表id”，获取全部记录
     *
     * @param tableName 表名
     * @param baseId    主表id
     * @return list
     */
    @Select("SELECT id, base_id, switch_id, switch_value, created_at, updated_at " +
            "FROM ${tableName} " +
            "WHERE base_id = #{baseId,jdbcType=BIGINT}")
    @Results({
            @Result(id = true, column = "id", jdbcType = JdbcType.BIGINT, property = "id"),
            @Result(column = "base_id", jdbcType = JdbcType.BIGINT, property = "baseId"),
            @Result(column = "switch_id", jdbcType = JdbcType.INTEGER, property = "switchId"),
            @Result(column = "switch_value", jdbcType = JdbcType.INTEGER, property = "switchValue"),
            @Result(column = "created_at", jdbcType = JdbcType.TIMESTAMP, property = "createdAt"),
            @Result(column = "updated_at", jdbcType = JdbcType.TIMESTAMP, property = "updatedAt")
    })
    List<SwitchModel> findAll(@Param("tableName") String tableName,
                              @Param("baseId") long baseId);

    /**
     * 通过“开关id”和“值”，查询列表
     *
     * @param tableName   表名
     * @param switchId    开关id
     * @param switchValue 值，0-关、1-开
     * @param startRow    起始行数
     * @param pageSize    每页条数
     * @return list
     */
    @Select("SELECT id, base_id, switch_id, switch_value, created_at, updated_at " +
            "FROM ${tableName} " +
            "WHERE switch_id = #{switchId,jdbcType=INTEGER} AND switch_value = #{switchValue,jdbcType=INTEGER} " +
            "LIMIT ${startRow}, ${pageSize}")
    @Results({
            @Result(id = true, column = "id", jdbcType = JdbcType.BIGINT, property = "id"),
            @Result(column = "base_id", jdbcType = JdbcType.BIGINT, property = "baseId"),
            @Result(column = "switch_id", jdbcType = JdbcType.INTEGER, property = "switchId"),
            @Result(column = "switch_value", jdbcType = JdbcType.INTEGER, property = "switchValue"),
            @Result(column = "created_at", jdbcType = JdbcType.TIMESTAMP, property = "createdAt"),
            @Result(column = "updated_at", jdbcType = JdbcType.TIMESTAMP, property = "updatedAt")
    })
    List<SwitchModel> findList(@Param("tableName") String tableName,
                               @Param("switchId") int switchId, @Param("switchValue") int switchValue,
                               @Param("startRow") long startRow, @Param("pageSize") int pageSize);

    /**
     * 通过“主表id”和“开关id”，获取一条记录
     *
     * @param tableName 表名
     * @param baseId    主表id
     * @param switchId  开关id
     * @return 记录
     */
    @Select("SELECT id, base_id, switch_id, switch_value, created_at, updated_at " +
            "FROM ${tableName} " +
            "WHERE base_id = #{baseId,jdbcType=BIGINT} AND switch_id = #{switchId,jdbcType=INTEGER}")
    @Results({
            @Result(id = true, column = "id", jdbcType = JdbcType.BIGINT, property = "id"),
            @Result(column = "base_id", jdbcType = JdbcType.BIGINT, property = "baseId"),
            @Result(column = "switch_id", jdbcType = JdbcType.INTEGER, property = "switchId"),
            @Result(column = "switch_value", jdbcType = JdbcType.INTEGER, property = "switchValue"),
            @Result(column = "created_at", jdbcType = JdbcType.TIMESTAMP, property = "createdAt"),
            @Result(column = "updated_at", jdbcType = JdbcType.TIMESTAMP, property = "updatedAt")
    })
    SwitchModel find(@Param("tableName") String tableName,
                     @Param("baseId") long baseId, @Param("switchId") int switchId);

    /**
     * 新增一条记录
     *
     * @param tableName   表名
     * @param baseId      主表id
     * @param switchId    开关id
     * @param switchValue 值，0-关、1-开
     * @return 影响行数
     */
    @Insert("INSERT INTO ${tableName} (base_id, switch_id, switch_value) " +
            "VALUES (#{baseId,jdbcType=BIGINT}, #{switchId,jdbcType=INTEGER}, #{switchValue,jdbcType=INTEGER})")
    int insert(@Param("tableName") String tableName,
               @Param("baseId") long baseId, @Param("switchId") int switchId, @Param("switchValue") int switchValue);

    /**
     * 通过“主表id”和“开关id”，修改一条记录
     *
     * @param tableName   表名
     * @param switchValue 值，0-关、1-开
     * @param baseId      主表id
     * @param switchId    开关id
     * @return 影响行数
     */
    @Update("UPDATE ${tableName} SET switch_value = #{switchValue,jdbcType=INTEGER} " +
            "WHERE base_id = #{baseId,jdbcType=BIGINT} AND switch_id = #{switchId,jdbcType=INTEGER}")
    int update(@Param("tableName") String tableName,
               @Param("switchValue") int switchValue, @Param("baseId") long baseId, @Param("switchId") int switchId);

}
