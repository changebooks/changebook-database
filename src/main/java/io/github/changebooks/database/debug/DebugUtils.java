package io.github.changebooks.database.debug;

import io.github.changebooks.code.base.Check;
import io.github.changebooks.code.util.AssertUtils;
import io.github.changebooks.code.util.DateTime;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;

/**
 * 分析SQL，梳理SQL概要
 *
 * @author changebooks@qq.com
 */
public final class DebugUtils {
    /**
     * SQL前缀，SELECT
     */
    private static final String SQL_PREV_SELECT = "SELECT";

    /**
     * 空白字符，如：空格、制表符、换页符等
     */
    private static final String REGEX_SPACE = "[\\s]+";

    /**
     * 占位符
     */
    private static final String PLACE_HOLDER = "\\?";

    /**
     * NULL占位符
     */
    private static final String PLACE_NULL = "NULL";

    private DebugUtils() {
    }

    /**
     * SQL概要
     *
     * @param invocation    Invocation
     * @param beginAt       开始执行时间，单位：毫秒
     * @param endAt         结束执行时间，单位：毫秒
     * @param proceedResult Object
     * @return {@link DebugSchema} instance
     */
    public static DebugSchema newSchema(Invocation invocation, long beginAt, long endAt, Object proceedResult) {
        AssertUtils.nonNull(invocation, "invocation");

        Object[] args = invocation.getArgs();

        AssertUtils.nonNull(args, "args");
        AssertUtils.isPositive(args.length, "args.length");
        AssertUtils.nonNull(args[0], "args[0]");

        MappedStatement statement = (MappedStatement) args[0];
        AssertUtils.nonNull(statement, "statement");

        Configuration configuration = statement.getConfiguration();
        AssertUtils.nonNull(configuration, "configuration");

        Object parameterObject = args.length > 1 ? args[1] : null;
        BoundSql boundSql = statement.getBoundSql(parameterObject);
        AssertUtils.nonNull(boundSql, "boundSql");

        String prepareSql = boundSql.getSql();
        List<Object> bindings = getBindings(boundSql, configuration);
        String command = replaceHolder(trimPrepareSql(prepareSql), bindings);
        String statementId = statement.getId();
        String databaseId = configuration.getDatabaseId();
        long elapsed = endAt - beginAt;

        DebugSchema schema = new DebugSchema();

        schema.setCommand(command);
        schema.setPrepareSql(prepareSql);
        schema.setBindings(bindings);
        schema.setElapsed(elapsed);
        schema.setBeginAt(beginAt);
        schema.setEndAt(endAt);
        schema.setStatementId(statementId);
        schema.setDatabaseId(databaseId);
        if (!sqlPrevSelect(prepareSql)) {
            schema.setProceedResult(proceedResult);
        }

        return schema;
    }

    /**
     * 参数列表
     *
     * @param boundSql      BoundSql
     * @param configuration Configuration
     * @return Object list, or null
     */
    public static List<Object> getBindings(BoundSql boundSql, Configuration configuration) {
        AssertUtils.nonNull(boundSql, "boundSql");
        AssertUtils.nonNull(configuration, "configuration");

        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappings();
        if (parameterMappingList == null) {
            return null;
        }

        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        Object parameterObject = boundSql.getParameterObject();
        MetaObject metaObject = null;

        List<Object> bindings = new ArrayList<>();

        for (ParameterMapping parameterMapping : parameterMappingList) {
            if (parameterMapping == null) {
                continue;
            }

            if (parameterMapping.getMode() == ParameterMode.OUT) {
                continue;
            }

            String propertyName = parameterMapping.getProperty();

            if (boundSql.hasAdditionalParameter(propertyName)) {
                Object parameter = boundSql.getAdditionalParameter(propertyName);
                bindings.add(parameter);
                continue;
            }

            if (parameterObject == null) {
                bindings.add(null);
                continue;
            }

            if (typeHandlerRegistry != null && typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                bindings.add(parameterObject);
                continue;
            }

            if (metaObject == null) {
                metaObject = configuration.newMetaObject(parameterObject);
            }

            Object parameter = (metaObject != null) ? metaObject.getValue(propertyName) : null;
            bindings.add(parameter);
        }

        return bindings;
    }

    /**
     * 查询SQL？
     *
     * @param sql SQL命令
     * @return SELECT *** ?
     */
    public static boolean sqlPrevSelect(String sql) {
        if (Check.isEmpty(sql)) {
            return false;
        }

        String prev = sql.trim().substring(0, SQL_PREV_SELECT.length()).toUpperCase();
        return SQL_PREV_SELECT.equals(prev);
    }

    /**
     * 预处理SQL，替换占位符
     *
     * @param prepareSql 预处理SQL，如，INSERT INTO table (id, name) VALUES (?, ?);
     * @param bindings   参数列表，如，[1, "abc"]
     * @return INSERT INTO table (id, name) VALUES (1, 'abc');
     */
    public static String replaceHolder(String prepareSql, List<Object> bindings) {
        if (Check.isEmpty(prepareSql) || Check.isEmpty(bindings)) {
            return prepareSql;
        }

        for (Object value : bindings) {
            String replacement = quoteValue(value);
            replacement = Matcher.quoteReplacement(replacement);

            prepareSql = prepareSql.replaceFirst(PLACE_HOLDER, replacement);
        }

        return prepareSql;
    }

    /**
     * 预处理SQL，空白去重，包括：空格、制表符、换页符等
     *
     * @param prepareSql 预处理SQL，如，INSERT INTO  table   (id, name)    VALUES     (?, ?);\t\n
     * @return INSERT INTO table (id, name) VALUES (?, ?);
     */
    public static String trimPrepareSql(String prepareSql) {
        if (prepareSql == null) {
            return null;
        } else {
            return prepareSql.replaceAll(REGEX_SPACE, " ");
        }
    }

    /**
     * 引用参数，填充占位符
     *
     * @param value 参数，如，1, abc, null, 2000-01-01 00:00:00
     * @return 1, 'abc', NULL, '2000-01-01 00:00:00'
     */
    public static String quoteValue(Object value) {
        if (value == null) {
            return PLACE_NULL;
        }

        if (value instanceof Date) {
            value = DateTime.format((Date) value);
        }

        if (value instanceof String) {
            return "'" + value + "'";
        }

        return value.toString();
    }

}
