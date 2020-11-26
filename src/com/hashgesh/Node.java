package com.hashgesh;

public class Node {
    public String word;
    public int count;
    public boolean isLeaf;

    public Node parent;
    public Node left;
    public Node right;

    public Node(){
    }

    public Node(String word, Integer count, boolean isLeaf, Node parent, Node left, Node right){
        this.word =  word;
        this.count = count;
        this.isLeaf = isLeaf;
        this.parent = parent;
        this.left = left;
        this.right = right;
    }

    public void setCount(Integer val){
        this.count =  val;
    }

    public boolean isFilled(){
        return this.left != null && this.right != null && !this.isLeaf;
    }


}
