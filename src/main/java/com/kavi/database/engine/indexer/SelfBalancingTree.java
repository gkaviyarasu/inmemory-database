package com.kavi.database.engine.indexer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaviyarasug on 21/04/16.
 */
public class SelfBalancingTree<T extends Comparable> implements Tree<T> {
    Node<T> root;

    public SelfBalancingTree() {

    }

    // A utility function to get height of the tree
    private int height(Node N) {
        if (N == null) {
            return 0;
        }
        return N.height;
    }

    // A utility function to get maximum of two integers
    private int max(int a, int b) {
        return (a > b) ? a : b;
    }

    // A utility function to right rotate subtree rooted with y
    // See the diagram given above.
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;

        // Return new root
        return x;
    }

    // A utility function to left rotate subtree rooted with x
    // See the diagram given above.
    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        //  Update heights
        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;

        // Return new root
        return y;
    }

    // Get Balance factor of node N
    private int getBalance(Node N) {
        if (N == null) {
            return 0;
        }
        return height(N.left) - height(N.right);
    }

    private Node insert(Node node, T key, int rowIndex) {
         
        /* 1.  Perform the normal BST rotation */
        if (node == null) {
            return (new Node(key, rowIndex));
        }

        if (key.compareTo(node.key) < 0) {
            node.left = insert(node.left, key, rowIndex);
        } else {
            node.right = insert(node.right, key, rowIndex);
        }
 
        /* 2. Update height of this ancestor node */
        node.height = max(height(node.left), height(node.right)) + 1;
 
        /* 3. Get the balance factor of this ancestor node to check whether
         this node became unbalanced */
        int balance = getBalance(node);

        // If this node becomes unbalanced, then there are 4 cases
        // Left Left Case
        if (balance > 1 && key.compareTo(node.left.key) < 0) {
            return rightRotate(node);
        }

        // Right Right Case
        if (balance < -1 && key.compareTo(node.right.key) > 0) {
            return leftRotate(node);
        }

        // Left Right Case
        if (balance > 1 && key.compareTo(node.left.key) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && key.compareTo(node.right.key) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
 
        /* return the (unchanged) node pointer */
        return node;
    }

    @Override
    public void insert(T key, int rowIndex) {
        root = insert(root, key, rowIndex);
    }

    // A utility function to print preorder traversal of the tree.
    // The function also prints height of every node
    @Override
    public List<Tree.Node> inOrder() {
        return inOrder(root);
    }

    @Override
    public List<Tree.Node> inOrder(Tree.Node node) {
        List<Tree.Node> results = new ArrayList<>();
        inOrder(node, results);
        return results;
    }

    @Override
    public Tree.Node getRoot() {
        return root;
    }

    private void inOrder(Tree.Node node, List<Tree.Node> result) {
        if (node != null) {
            inOrder(node.getLeft(), result);
            //System.out.print(node.key + " ");
            result.add(node);
            inOrder(node.getRight(), result);
        }
    }


    public static class Node<T extends Comparable> implements Comparable<Node>, com.kavi.database.engine.indexer.Tree.Node<T> {
        private T key;
        private int rowIndex, height;
        private Node left, right;

        public Node(T key, int rowIndex) {
            this.key = key;
            this.rowIndex = rowIndex;
        }

        @Override
        public T getValue() {
            return key;
        }

        public void setValue(T value) {
            this.key = value;
        }

        @Override
        public int getRowIndex() {
            return rowIndex;
        }

        public void setRowIndex(int rowIndex) {
            this.rowIndex = rowIndex;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        @Override
        public com.kavi.database.engine.indexer.Tree.Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        @Override
        public com.kavi.database.engine.indexer.Tree.Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        @Override
        public int compareTo(Node o) {
            return this.key.compareTo(o.key);
        }

    }
}
