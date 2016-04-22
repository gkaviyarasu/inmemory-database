package com.kavi.database.engine.metadata;

import com.kavi.database.engine.query.Criteria;
import com.kavi.database.util.Util;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by kaviyarasug on 21/04/16.
 */
public class RowNullSafeComparator implements Comparator<Map<String, Comparable>> {

    private final Column column;
    private final Criteria.OrderType orderType;

    public RowNullSafeComparator(final Column column, Criteria.OrderType orderType) {
        this.column = column;
        this.orderType = orderType;
    }

    @Override
    public int compare(final Map<String, Comparable> obj1, final Map<String, Comparable> obj2) {
        int returnValue = 0;
        if (obj1 == null ^ obj2 == null) {
            returnValue = obj1 == null ? -1 : 1;
        } else if (obj1 == null && obj2 == null) {
            returnValue = 0;
        } else {
            Comparable value1 = obj1.get(column.getName());
            Comparable value2 = obj2.get(column.getName());
            returnValue = Util.nullSafeCompare(value1, value2);
        }
        return Criteria.OrderType.ASC == orderType ? returnValue : returnValue * -1;
    }
}
