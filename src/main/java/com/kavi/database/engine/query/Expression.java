package com.kavi.database.engine.query;

import com.kavi.database.engine.metadata.Column;

import java.util.List;
import java.util.Map;

/**
 * Created by kaviyarasug on 21/04/16.
 */
public interface Expression {
    boolean match(Map<String, Comparable> row);

    Column getColumn();

    List<Map<String, Comparable>> matchWithIndex() throws Exception;

    default boolean isIndexBasedExpression() {
        return getColumn().getTable().getIndexes().containsKey(getColumn().getName());
    }
}
