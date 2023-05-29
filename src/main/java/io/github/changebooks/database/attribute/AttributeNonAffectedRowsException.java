package io.github.changebooks.database.attribute;

import io.github.changebooks.code.base.Check;

/**
 * Write Database Exception
 * Affected's Rows is Zero
 *
 * @author changebooks@qq.com
 */
public class AttributeNonAffectedRowsException extends RuntimeException {
    /**
     * Default Message
     */
    private static final String NON_NULL = "record can't be null";

    public AttributeNonAffectedRowsException(AttributeModel record) {
        super(Check.isNull(record) ? NON_NULL : record.toString());
    }

}
