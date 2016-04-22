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
public class EqExpression implements Expression {
    private final Column column;
    private final Comparable value;

    public EqExpression(final Column column, Comparable value) {
        this.column = column;
        this.value = value;
    }

    public Column getColumn() {
        return column;
    }

    public Comparable getValue() {
        return value;
    }

    @Override
    public boolean match(Map<String, Comparable> row) {
        return Util.nullSafeCompare(row.get(column.getName()), value) == 0;
    }

    @Override
    public List<Map<String, Comparable>> matchWithIndex() throws Exception {
        Tree tree = column.getTable().getIndexes().get(column.getName());
        if (tree == null) {
            throw new Exception("Unknown indexed column");
        }
        List<Map<String, Comparable>> result = new ArrayList<>();
        Tree.Node root = tree.getRoot();

        while (root != null) {
            switch (Util.nullSafeCompare(root.getValue(), value)) {
                case 0:
                    result.add(column.getTable().getDataStore().readRow(root.getRowIndex()));
                    return result;
                case 1:
                    root = root.getLeft();
                    break;
                case -1:
                    root = root.getRight();
                    break;
            }
        }
        return result;
    }
}
