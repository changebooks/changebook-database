package io.github.changebooks.database.debug;

import io.github.changebooks.code.util.AssertUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 拦截器
 *
 * @author changebooks@qq.com
 */
@Intercepts({
        @Signature(
                type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
        ),
        @Signature(
                type = Executor.class,
                method = "update",
                args = {MappedStatement.class, Object.class}
        )
})
public class DebugInterceptor implements Interceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(DebugInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        AssertUtils.nonNull(invocation, "invocation");

        long beginAt = System.currentTimeMillis();
        Object proceedResult = invocation.proceed();
        long endAt = System.currentTimeMillis();

        try {
            writeLog(invocation, beginAt, endAt, proceedResult);
        } catch (Throwable tr) {
            LOGGER.error("DebugInterceptor intercept, writeLog failed, beginAt: {}, endAt: {}, proceedResult: {}, throwable: ",
                    beginAt, endAt, proceedResult, tr);
        }

        return proceedResult;
    }

    /**
     * 写日志
     *
     * @param invocation    Invocation
     * @param beginAt       开始执行时间，单位：毫秒
     * @param endAt         结束执行时间，单位：毫秒
     * @param proceedResult Object
     */
    public void writeLog(Invocation invocation, long beginAt, long endAt, Object proceedResult) {
        DebugSchema schema = DebugUtils.newSchema(invocation, beginAt, endAt, proceedResult);
        LOGGER.info(schema.toString());
    }

}
