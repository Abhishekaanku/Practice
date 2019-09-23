package com.mine;

public class Tree {
    private Node root;

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }


    static class Node {
        private char data;
        private boolean isEnd;
        private Node []child;

        Node(char data,boolean isEnd) {
            this.data=data;
            this.isEnd=isEnd;
            child=new Node[27];
        }

        public char getData() {
            return data;
        }

        public Node[] getChild() {
            return child;
        }

        public boolean isEnd() {
            return isEnd;
        }

        public void setIsEnd(boolean end) {
            isEnd = end;
        }
    }
}
