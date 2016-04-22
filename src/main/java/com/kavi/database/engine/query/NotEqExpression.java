package com.kavi.database.engine.query;

import com.kavi.database.engine.indexer.Tree;
import com.kavi.database.engine.metadata.Column;
import com.kavi.database.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by kaviyarasug on 21/04/16.
 */
public class NotEqExpression implements Expression {
    private final Column column;
    private final Comparable value;

    public NotEqExpression(final Column column, Comparable value) {
        this.column = column;
        this.value = value;
    }

    @Override
    public boolean match(Map<String, Comparable> row) {
        return Util.nullSafeCompare(row.get(column.getName()), value) != 0;
    }

    public Comparable getValue() {
        return value;
    }

    @Override
    public Column getColumn() {
        return column;
    }

    @Override
    public List<Map<String, Comparable>> matchWithIndex() throws Exception {
        Tree tree = column.getTable().getIndexes().get(column.getName());
        if (tree == null) {
            throw new Exception("Unknown indexed column");
        }
        List<Map<String, Comparable>> result = new ArrayList<>();
        Tree.Node root = tree.getRoot();

        List<Tree.Node> nodes = tree.inOrder();
        for (Tree.Node node : nodes) {
            if (Util.nullSafeCompare(node.getValue(), value) != 0) {
                column.getTable().getDataStore().readRow(node.getRowIndex());
            }
        }
        return result;
    }

    @Override
    public boolean isIndexBasedExpression() {
        return false;
    }
}
