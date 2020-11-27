package com.hashgesh;

public class TreePrinter {
    public void printTree(Node ptr) {
        StringBuilder sb = new StringBuilder();
        visitAndCollect(sb, "", "", ptr);
        System.out.println(sb.toString());
    }

    public  void visitAndCollect(StringBuilder sb, String padding, String pointer, Node node) {
        if (node != null) {
            sb.append(padding);
            sb.append(pointer);

            //add node info
            sb.append(node.getCount());
            if (node.getWord() != null)
                sb.append("(").append(node.getWord()).append(")");
            sb.append("\n");


            //prepare connection representations
            StringBuilder levelSeparator = new StringBuilder(padding);
            levelSeparator.append("|  ");
            String rightBranch = "└──";
            String leftBranch = (node.getRight() != null) ? "├──" : "└──";


            visitAndCollect(sb, levelSeparator.toString(), leftBranch, node.getLeft());
            visitAndCollect(sb, levelSeparator.toString(), rightBranch, node.getRight());
        }
    }
}
