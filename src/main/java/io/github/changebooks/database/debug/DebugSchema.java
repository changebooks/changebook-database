package io.github.changebooks.database.debug;

import io.github.changebooks.code.base.JsonParser;

import java.io.Serializable;
import java.util.List;

/**
 * SQL概要
 *
 * @author changebooks@qq.com
 */
public final class DebugSchema implements Serializable {
    /**
     * 完整SQL
     * 如，INSERT INTO table (id, name) VALUES (1, 'abc');
     */
    private String command;

    /**
     * 预处理SQL
     * 如，INSERT INTO table (id, name) VALUES (?, ?);
     */
    private String prepareSql;

    /**
     * 参数列表
     * 如，[1, "abc"]
     */
    private List<Object> bindings;

    /**
     * 执行结果
     */
    private Object proceedResult;

    /**
     * 耗时，单位：毫秒
     */
    private long elapsed;

    /**
     * 开始时间，单位：毫秒
     */
    private long beginAt;

    /**
     * 结束时间，单位：毫秒
     */
    private long endAt;

    /**
     * 执行方法
     * 如，***.mapper.TestMapper.insert
     */
    private String statementId;

    /**
     * 配置id
     */
    private String databaseId;

    @Override
    public String toString() {
        return JsonParser.toJson(this);
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getPrepareSql() {
        return prepareSql;
    }

    public void setPrepareSql(String prepareSql) {
        this.prepareSql = prepareSql;
    }

    public List<Object> getBindings() {
        return bindings;
    }

    public void setBindings(List<Object> bindings) {
        this.bindings = bindings;
    }

    public Object getProceedResult() {
        return proceedResult;
    }

    public void setProceedResult(Object proceedResult) {
        this.proceedResult = proceedResult;
    }

    public long getElapsed() {
        return elapsed;
    }

    public void setElapsed(long elapsed) {
        this.elapsed = elapsed;
    }

    public long getBeginAt() {
        return beginAt;
    }

    public void setBeginAt(long beginAt) {
        this.beginAt = beginAt;
    }

    public long getEndAt() {
        return endAt;
    }

    public void setEndAt(long endAt) {
        this.endAt = endAt;
    }

    public String getStatementId() {
        return statementId;
    }

    public void setStatementId(String statementId) {
        this.statementId = statementId;
    }

    public String getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(String databaseId) {
        this.databaseId = databaseId;
    }

}
