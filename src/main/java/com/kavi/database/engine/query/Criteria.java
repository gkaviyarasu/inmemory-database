package com.kavi.database.engine.query;

import com.kavi.database.engine.metadata.Column;
import com.kavi.database.engine.metadata.RowNullSafeComparator;
import com.kavi.database.engine.metadata.Table;
import com.kavi.database.util.Util;

import java.util.*;

/**
 * Created by kaviyarasug on 21/04/16.
 */
public class Criteria {
    private Expression expression;
    private List<Column> selectColumns;
    private Column orderBy;
    private OrderType orderType;

    public static Criteria parseSql(String sql, Table table) throws Exception {
        Criteria criteria = new Criteria();
        int whereIndex = sql.indexOf("Where");
        int orderIndex = sql.indexOf("Order by");
        String selectColumns = sql.substring(7, whereIndex < 0 ? sql.length() : whereIndex);
        List<Column> selectedColumns = new ArrayList<>();
        if (selectColumns.contains("*")) {
            selectedColumns.addAll(table.getColumns().values());
        } else {
            String[] columnNames = selectColumns.split(",");
            for (String columnName : columnNames) {
                selectedColumns.add(table.getColumns().get(columnName.trim()));
            }
        }
        criteria.setSelectColumns(selectedColumns);
        if (whereIndex > 0) {
            String whereExpression = sql.substring(whereIndex + 6, orderIndex < 0 ? sql.length() : orderIndex);
            StringTokenizer tokenizer = new StringTokenizer(whereExpression);
            Column column = table.getColumns().get(tokenizer.nextToken());
            String operator = tokenizer.nextToken();
            Comparable value = Util.convertToType(column.getType(), tokenizer.nextToken());

            criteria.setExpression(ExpressionBuilder.generateExpression(column, operator, value));
        }

        if (orderIndex > 0) {
            String orderByField = sql.substring(orderIndex + 9);
            StringTokenizer tokenizer = new StringTokenizer(orderByField);
            Column column = table.getColumns().get(tokenizer.nextToken());
            OrderType order = tokenizer.hasMoreTokens() ? OrderType.valueOf(tokenizer.nextToken()) : OrderType.ASC;
            criteria.setOrderBy(column);
            criteria.setOrderType(order);
        }

        return criteria;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public List<Column> getSelectColumns() {
        return selectColumns;
    }

    public void setSelectColumns(List<Column> selectColumns) {
        this.selectColumns = selectColumns;
    }

    public Column getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Column orderBy) {
        this.orderBy = orderBy;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public List<Map<String, Comparable>> eval() throws Exception {
        List<Map<String, Comparable>> result = new ArrayList<>();
        Iterator<Map<String, Comparable>> rowIterator = selectColumns.get(0).getTable().getDataStore().getRowIterator();
        if (expression.isIndexBasedExpression()) {
            result = expression.matchWithIndex();
        } else {
            while (rowIterator.hasNext()) {
                Map<String, Comparable> resultObject = new HashMap<>();
                Map<String, Comparable> row = rowIterator.next();
                if (expression == null || expression.match(row)) {
                    for (Column column : selectColumns) {
                        resultObject.put(column.getName(), row.get(column.getName()));
                    }
                    result.add(resultObject);
                }
            }
        }
        if (this.orderBy != null) {
            Collections.sort(result, new RowNullSafeComparator(orderBy, orderType));
        }
        return result;
    }

    public static enum OrderType {
        DESC, ASC;
    }
}
