package io.github.changebooks.database.attribute;

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
public interface AttributeMapper {
    /**
     * 通过“主表id”，获取全部记录
     *
     * @param tableName 表名
     * @param baseId    主表id
     * @return list
     */
    @Select("SELECT id, base_id, attribute_id, attribute_value, version, created_at, updated_at " +
            "FROM ${tableName} " +
            "WHERE base_id = #{baseId,jdbcType=BIGINT}")
    @Results({
            @Result(id = true, column = "id", jdbcType = JdbcType.BIGINT, property = "id"),
            @Result(column = "base_id", jdbcType = JdbcType.BIGINT, property = "baseId"),
            @Result(column = "attribute_id", jdbcType = JdbcType.INTEGER, property = "attributeId"),
            @Result(column = "attribute_value", jdbcType = JdbcType.VARCHAR, property = "attributeValue"),
            @Result(column = "version", jdbcType = JdbcType.INTEGER, property = "version"),
            @Result(column = "created_at", jdbcType = JdbcType.TIMESTAMP, property = "createdAt"),
            @Result(column = "updated_at", jdbcType = JdbcType.TIMESTAMP, property = "updatedAt")
    })
    List<AttributeModel> findAll(@Param("tableName") String tableName,
                                 @Param("baseId") long baseId);

    /**
     * 通过“主表id”和“属性id”，获取一条记录
     *
     * @param tableName   表名
     * @param baseId      主表id
     * @param attributeId 属性id
     * @return 记录
     */
    @Select("SELECT id, base_id, attribute_id, attribute_value, version, created_at, updated_at " +
            "FROM ${tableName} " +
            "WHERE base_id = #{baseId,jdbcType=BIGINT} AND attribute_id = #{attributeId,jdbcType=INTEGER}")
    @Results({
            @Result(id = true, column = "id", jdbcType = JdbcType.BIGINT, property = "id"),
            @Result(column = "base_id", jdbcType = JdbcType.BIGINT, property = "baseId"),
            @Result(column = "attribute_id", jdbcType = JdbcType.INTEGER, property = "attributeId"),
            @Result(column = "attribute_value", jdbcType = JdbcType.VARCHAR, property = "attributeValue"),
            @Result(column = "version", jdbcType = JdbcType.INTEGER, property = "version"),
            @Result(column = "created_at", jdbcType = JdbcType.TIMESTAMP, property = "createdAt"),
            @Result(column = "updated_at", jdbcType = JdbcType.TIMESTAMP, property = "updatedAt")
    })
    AttributeModel find(@Param("tableName") String tableName,
                        @Param("baseId") long baseId, @Param("attributeId") int attributeId);

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
    @Select("SELECT id, base_id, attribute_id, attribute_value, version, created_at, updated_at " +
            "FROM ${tableName} " +
            "WHERE attribute_id = #{attributeId,jdbcType=INTEGER} AND attribute_value = #{attributeValue,jdbcType=VARCHAR} " +
            "LIMIT ${startRow}, ${pageSize}")
    @Results({
            @Result(id = true, column = "id", jdbcType = JdbcType.BIGINT, property = "id"),
            @Result(column = "base_id", jdbcType = JdbcType.BIGINT, property = "baseId"),
            @Result(column = "attribute_id", jdbcType = JdbcType.INTEGER, property = "attributeId"),
            @Result(column = "attribute_value", jdbcType = JdbcType.VARCHAR, property = "attributeValue"),
            @Result(column = "version", jdbcType = JdbcType.INTEGER, property = "version"),
            @Result(column = "created_at", jdbcType = JdbcType.TIMESTAMP, property = "createdAt"),
            @Result(column = "updated_at", jdbcType = JdbcType.TIMESTAMP, property = "updatedAt")
    })
    List<AttributeModel> findList(@Param("tableName") String tableName,
                                  @Param("attributeId") int attributeId, @Param("attributeValue") String attributeValue,
                                  @Param("startRow") long startRow, @Param("pageSize") int pageSize);

    /**
     * 新增一条记录
     *
     * @param tableName      表名
     * @param baseId         主表id
     * @param attributeId    属性id
     * @param attributeValue 属性值
     * @return 影响行数
     */
    @Insert("INSERT INTO ${tableName} (base_id, attribute_id, attribute_value, version) " +
            "VALUES (#{baseId,jdbcType=BIGINT}, #{attributeId,jdbcType=INTEGER}, #{attributeValue,jdbcType=VARCHAR}, 1)")
    int insert(@Param("tableName") String tableName,
               @Param("baseId") long baseId, @Param("attributeId") int attributeId, @Param("attributeValue") String attributeValue);

    /**
     * 通过“自增id”和“版本”，修改一条记录
     *
     * @param tableName      表名
     * @param attributeValue 属性值
     * @param id             自增id
     * @param version        版本
     * @return 影响行数
     */
    @Update("UPDATE ${tableName} SET attribute_value = #{attributeValue,jdbcType=VARCHAR}, version = version + 1 " +
            "WHERE id = #{id,jdbcType=BIGINT} AND version = #{version,jdbcType=INTEGER}")
    int update(@Param("tableName") String tableName,
               @Param("attributeValue") String attributeValue, @Param("id") long id, @Param("version") int version);

}
