package com.kavi.database.engine.query;

import com.kavi.database.engine.metadata.Column;

/**
 * Created by kaviyarasug on 21/04/16.
 */
public class ExpressionBuilder {

    public static Expression generateExpression(Column column, String operator, Comparable value) {
        Expression expression = null;
        switch (operator) {
            case "=":
                expression = new EqExpression(column, value);
                break;
            case "!=":
                expression = new NotEqExpression(column, value);
                break;
            case ">":
                expression = new GeExpression(column, value);
                break;
            case "<":
                expression = new LeExpression(column, value);
                break;
            case ">=":
                expression = new GeEqExpression(column, value);
                break;
            case "<=":
                expression = new LeEqExpression(column, value);
                break;
        }
        return expression;
    }
}