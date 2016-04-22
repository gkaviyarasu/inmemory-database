package com.kavi.database.engine.indexer;

import java.util.List;

/**
 * Created by kaviyarasug on 21/04/16.
 */
public interface Tree<T extends Comparable> {
    void insert(T key, int rowIndex);

    // A utility function to print in-order traversal of the tree.
    // The function also prints height of every node
    List<Node> inOrder();

    Node getRoot();

    List<Node> inOrder(Node right);

    public interface Node<T extends Comparable> {
        T getValue();

        int getRowIndex();

        Node getLeft();

        Node getRight();
    }
}
