package com.kavi.database.engine.query;

import com.kavi.database.engine.indexer.Tree;
import com.kavi.database.engine.metadata.Column;
import com.kavi.database.util.Util;

import java.util.*;

/**
 * Created by kaviyarasug on 21/04/16.
 */
public class LeExpression implements Expression {
    private final Column column;
    private final Comparable value;

    public LeExpression(final Column column, Comparable value) {
        this.column = column;
        this.value = value;
    }

    @Override
    public boolean match(Map<String, Comparable> row) {
        return Util.nullSafeCompare(row.get(column.getName()), value) < 0;
    }

    @Override
    public Column getColumn() {
        return column;
    }

    public Comparable getValue() {
        return value;
    }

    @Override
    public List<Map<String, Comparable>> matchWithIndex() throws Exception {
        Tree tree = column.getTable().getIndexes().get(column.getName());
        if (tree == null) {
            throw new Exception("Unknown indexed column");
        }
        List<Map<String, Comparable>> result = new ArrayList<>();
        Queue<Tree.Node> nodeQueue = new LinkedList<>();
        nodeQueue.add(tree.getRoot());

        while (!nodeQueue.isEmpty()) {
            Tree.Node root = nodeQueue.poll();
            if (root == null) {
                continue;
            }
            switch (Util.nullSafeCompare(root.getValue(), value)) {
                case -1:
                    result.add(column.getTable().getDataStore().readRow(root.getRowIndex()));
                    addToList(tree.inOrder(root.getLeft()), result);
                    nodeQueue.add(root.getRight());
                    break;
                case 1:
                case 0:
                    nodeQueue.add(root.getLeft());
                    break;
            }
        }
        return result;
    }

    private void addToList(List<Tree.Node> nodes, List<Map<String, Comparable>> result) {
        for (Tree.Node node : nodes) {
            result.add(column.getTable().getDataStore().readRow(node.getRowIndex()));
        }
    }
}
