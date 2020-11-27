package com.hashgesh;

public class Node {

    public String  word;
    public int     count;
    public boolean isLeaf;

    public Node parent;
    public Node left;
    public Node right;

    public Node() {
    }

    public Node(String word, Integer count, boolean isLeaf, Node parent, Node left, Node right) {
        this.word = word;
        this.count = count;
        this.isLeaf = isLeaf;
        this.parent = parent;
        this.left = left;
        this.right = right;
    }

    public String getWord() {
        return word;
    }

    public void setWord(final String word) {
        this.word = word;
    }

    public int getCount() {
        return count;
    }

    public void setCount(final int count) {
        this.count = count;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(final boolean leaf) {
        isLeaf = leaf;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(final Node parent) {
        this.parent = parent;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(final Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(final Node right) {
        this.right = right;
    }

    public static Integer getLeafValue(Node ptr) {
        Node current = ptr;
        if (current.isLeaf()) {
            return current.count;
        }
        return getLeafValue(current.left);
    }


    public boolean isFilled() {
        return this.left != null && this.right != null && !this.isLeaf;
    }
}
